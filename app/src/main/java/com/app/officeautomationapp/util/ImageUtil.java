package com.app.officeautomationapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Toast;

import com.yalantis.ucrop.entity.LocalMedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/11 0011.
 */

public abstract class ImageUtil {

    /**
     * 添加时间水印
     * @param src
     */
    private static Bitmap addTimeFlag(Bitmap src){
        // 获取原始图片与水印图片的宽与高
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(newBitmap);
        // 往位图中开始画入src原始图片
        mCanvas.drawBitmap(src, 0, 0, null);
        //添加文字
        Paint textPaint = new Paint();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        textPaint.setColor(Color.RED) ;
        textPaint.setTextSize(100);
        String familyName = "宋体";
//        Typeface typeface = Typeface.create(familyName,
//                Typeface.BOLD_ITALIC);
//        textPaint.setTypeface(typeface);
//        textPaint.setTextAlign(Align.CENTER);

//        mCanvas.drawText(time, (float)(w*1)/7, (float)(h*14)/15, textPaint);
        mCanvas.drawText(time, (float)(w*1/2), (float)(h*1/15), textPaint);
        mCanvas.save(Canvas.ALL_SAVE_FLAG);
        mCanvas.restore();
        return newBitmap ;
    }


    private static boolean saveBitmap2file(Bitmap bmp,String src){
        Bitmap.CompressFormat format= Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(src);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bmp.compress(format, quality, stream);
    }

    public static void addTimePic(List<LocalMedia> resultList, Context context)
    {
        if(resultList.size()>0)
        {
            //加水印
            for(LocalMedia localMedia:resultList)
            {
                try {
                    Bitmap bmp = BitmapFactory.decodeFile(localMedia.getPath());
                    Bitmap rbmp = ImageUtil.addTimeFlag(bmp);
                    ImageUtil.saveBitmap2file(rbmp, localMedia.getPath());
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(context,"加载水印失败。",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
