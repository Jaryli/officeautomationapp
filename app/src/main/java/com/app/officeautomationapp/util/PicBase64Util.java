package com.app.officeautomationapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by CS-711701-00027 on 2017/5/23.
 */

public abstract class PicBase64Util {

    //图片压缩为base64 compress为1-100
    public static String encode(String path,int compress) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Log.d(TAG, "bitmap width: " + bitmap.getWidth() + " height: " + bitmap.getHeight());
        //convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, compress, baos);
        byte[] bytes = baos.toByteArray();

        //base64 encode
        byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
        String encodeString = new String(encode);
        return encodeString;
    }

    //图片解压缩
    public static Bitmap decode(String base64) {
        byte[] decode = Base64.decode(base64,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        //save to image on sdcard
        return bitmap;
    }

    //图片解压缩
    public static String decode(String base64,String name,Bitmap.CompressFormat c) {
        byte[] decode = Base64.decode(base64,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        String path="";
        try {
            path = Environment.getExternalStorageDirectory().getPath()
                    +name;
            Log.d("linc","path is "+path);
            OutputStream stream = new FileOutputStream(path);
            bitmap.compress(c, 90, stream);
            stream.close();
            Log.e("linc","jpg okay!");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("linc","failed: "+e.getMessage());
        }
        return path;
    }

}
