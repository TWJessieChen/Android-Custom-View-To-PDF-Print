package com.jc666.ecglibrary;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.NonNull;

/**
 * @author JC666
 * @date 2021/12/15
 * @describe TODO
 */
public final class ChartUtils {

    public static final float CONTAIN_OFFSET = 5;//范围坐标判断偏移量

    /**
     * Used to measure label width. If label has mas 5 characters only 5 first characters of this array are used to
     * measure text width.
     */
    public static final char[] VALUEWIDTHCHARS = new char[]{
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};

    public static float dip2px(DisplayMetrics displayMetrics, float dp) {
       return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    public static int dp2px(float density, float dp) {
        if (dp == 0) {
            return 0;
        }
        return (int) (dp * density + 0.5f);
    }

    public static int sp2px(float scaledDensity, float sp) {
        if (sp == 0) {
            return 0;
        }
        return (int) (sp * scaledDensity + 0.5f);
    }

    public static boolean copyof(@NonNull char[] src, @NonNull char[] dst){
        System.arraycopy(src,0,dst,0,Math.min(src.length,dst.length));
        return true;
    }

    public static float measureText(@NonNull char[] src, Paint paint){
        return paint.measureText(src,0,src.length);
    }

    public static int getTextHeight(Paint paint, String text){
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length()-1, rect);
        return rect.height();
    }

    /**
     * 绘制文字图片
     * @param chars
     * @param index
     * @param count
     * @param degree
     * @param px
     * @param py
     * @param paint
     * @return
     */
    public static Bitmap drawBitmapText(@NonNull char[] chars, int index, int count,float degree, float px, float py,@NonNull Paint paint){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        RectF target = new RectF(0,0,paint.measureText(chars,index,count),
                (float) Math.floor(fontMetrics.descent - fontMetrics.ascent));
        int baseLine =  (int)((target.bottom + target.top - fontMetrics.bottom - fontMetrics.top) / 2.0f);
        Bitmap textBmp = Bitmap.createBitmap((int) target.width(), (int) target.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(textBmp);
        canvas.save();
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG));
        canvas.rotate(degree,px,py);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawText(chars,index,count,0,baseLine,paint);
        canvas.restore();
        return textBmp;
    }

    public static Bitmap drawBitmapText(@NonNull char[] chars, int index, int count,@NonNull Paint paint){
        return drawBitmapText(chars, index, count,0,0,0,paint);
    }


    public static float applyDimension(int unit, float value){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(unit,value,metrics);
    }

}
