package xyz.zpayh.library.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.socks.library.KLog;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import junit.framework.Assert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Administrator on 2016/11/21.
 */

public class WXUtils {

    public static SendMessageToWX.Req createTextReq(@NonNull String content,@WXSceneState int state ){
        // 初始化一个WXTextObject对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = content;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = content;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction();
        // transaction字段用于唯一标识一个请求
        req.message = msg;
        setScene(state, req);
        return req;
    }

    public static SendMessageToWX.Req createImgReq(@NonNull Bitmap bitmap,
                                                   @NonNull Bitmap thumbBmp,@WXSceneState int state){

        // 初始化WXImageObject和WXMediaMessage对象
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        // 设置缩略图
        bitmap.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp,true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction();
        //transaction字段用于唯一标识一个请求
        req.message = msg;
        setScene(state,req);

        return req;
    }

    public static SendMessageToWX.Req createMusicReq(@NonNull String musicUrl,
                                                    @NonNull String musicTitle,
                                                     @NonNull String description,
                                                     @NonNull Bitmap thumb,
                                                     @WXSceneState int state) {
        // 初始化一个WXMusicObject对象
        WXMusicObject music = new WXMusicObject();
        music.musicUrl = musicUrl;

        //用一个WXMusicObject对象初始化一个WXMediaMessage对象，填写标题，描述
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = musicTitle;
        msg.description = description;
        msg.thumbData = bmpToByteArray(thumb, true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction();
        req.message = msg;
        setScene(state,req);
        return req;
    }

    public static SendMessageToWX.Req createVideoReq(@NonNull String videoUrl,
                                                     @NonNull String videoTitle,
                                                     @NonNull String description,
                                                     @NonNull Bitmap thumb,
                                                     @WXSceneState int state){
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = videoUrl;

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = videoTitle;
        msg.description = description;
        msg.thumbData = bmpToByteArray(thumb,true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction();
        req.message = msg;
        setScene(state,req);

        return req;
    }

    public static SendMessageToWX.Req createWebReq(@NonNull String url,
                                                   @NonNull String title,
                                                   @NonNull String description,
                                                   @NonNull Bitmap thumb,
                                                   @WXSceneState int state){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        msg.thumbData = bmpToByteArray(thumb,true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction();
        req.message = msg;
        setScene(state,req);
        return req;
    }

    private static void setScene(@WXSceneState int state, SendMessageToWX.Req req) {
        switch (state){
            case WXSceneState.SESSION://发送到聊天界面
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case WXSceneState.TIME_LINE://发送到朋友圈
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case WXSceneState.FAVORITE://添加到微信收藏
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            default://发送到聊天界面
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
    }

    private static String buildTransaction() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] getHtmlByteArray(final String url) {
        URL htmlUrl = null;
        InputStream inStream = null;
        try {
            htmlUrl = new URL(url);
            URLConnection connection = htmlUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;
            int responseCode = httpConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                inStream = httpConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = inputStreamToByte(inStream);

        return data;
    }

    public static byte[] inputStreamToByte(InputStream is) {
        try{
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] readFromFile(String fileName, int offset, int len) {
        if (fileName == null) {
            return null;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            KLog.i("readFromFile: file not found");
            return null;
        }

        if (len == -1) {
            len = (int) file.length();
        }

        KLog.d( "readFromFile : offset = " + offset + " len = " + len + " offset + len = " + (offset + len));

        if(offset <0){
            KLog.e( "readFromFile invalid offset:" + offset);
            return null;
        }
        if(len <=0 ){
            KLog.e("readFromFile invalid len:" + len);
            return null;
        }
        if(offset + len > (int) file.length()){
            KLog.e("readFromFile invalid file len:" + file.length());
            return null;
        }

        byte[] b = null;
        try {
            RandomAccessFile in = new RandomAccessFile(fileName, "r");
            b = new byte[len]; // 创建合适文件大小的数组
            in.seek(offset);
            in.readFully(b);
            in.close();

        } catch (Exception e) {
            KLog.e( "readFromFile : errMsg = " + e.getMessage());
            e.printStackTrace();
        }
        return b;
    }

    private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;
    public static Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) {
        Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);

        BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            options.inJustDecodeBounds = true;
            Bitmap tmp = BitmapFactory.decodeFile(path, options);
            if (tmp != null) {
                tmp.recycle();
                tmp = null;
            }

            KLog.d("extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
            final double beY = options.outHeight * 1.0 / height;
            final double beX = options.outWidth * 1.0 / width;
            KLog.d( "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
            options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
            if (options.inSampleSize <= 1) {
                options.inSampleSize = 1;
            }

            // NOTE: out of memory error
            while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                options.inSampleSize++;
            }

            int newHeight = height;
            int newWidth = width;
            if (crop) {
                if (beY > beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            } else {
                if (beY < beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            }

            options.inJustDecodeBounds = false;

            KLog.i( "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
            Bitmap bm = BitmapFactory.decodeFile(path, options);
            if (bm == null) {
                KLog.e("bitmap decode failed");
                return null;
            }

            KLog.i( "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
            final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
            if (scale != null) {
                bm.recycle();
                bm = scale;
            }

            if (crop) {
                final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
                if (cropped == null) {
                    return bm;
                }

                bm.recycle();
                bm = cropped;
                KLog.i(  "bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
            }
            return bm;

        } catch (final OutOfMemoryError e) {
            KLog.e( "decode bitmap failed: " + e.getMessage());
            options = null;
        }

        return null;
    }
}
