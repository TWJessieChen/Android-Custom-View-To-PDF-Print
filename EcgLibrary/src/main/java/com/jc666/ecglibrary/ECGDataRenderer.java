package com.jc666.ecglibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import androidx.annotation.NonNull;
import java.util.List;

/**
 * @author JC666
 * @date 2021/12/15
 * @describe TODO
 */
class ECGDataRenderer extends BriteMEDRealRenderer{
    private String TAG = this.getClass().getSimpleName();

    private static final int BLACK_LEAD_LINE_COLOR = Color.parseColor("#ff000000"); //黑色

    private static final int GREEN_LEAD_LINE_COLOR = Color.parseColor("#ff00fb00");  //亮綠色

    private int colorType = 0;//紀錄畫筆顏色Type，0:黑色 1:綠色

    private int gainValue = 0;//紀錄畫筆顏色Type，0:黑色 1:綠色

    private Transformer transformer;

    private int dataLeft;

    private int dataRight;

    private int rowHeight;

    private int leadIndex;

    private Paint linePaint;

    private Paint timePaint;

    private Paint peakPaint;

    private static final float oneMV= 13981f;

    ECGDataRenderer(@NonNull Context context, @NonNull List<ECGReportDataFormat> values, int leadIndex, @NonNull int type, int gain) {
        super(context, values);
        this.colorType = type;
        this.gainValue = gain;
        this.leadIndex = leadIndex;
    }

    @Override
    public void draw(Canvas canvas) {
        initPaint(canvas, colorType);
        this.transformer = mSoftStrategy.getTransformer();
        Log.d(TAG,"this.transformer: " + this.transformer);

        this.dataLeft = mSoftStrategy.horizontalPadding();
        Log.d(TAG,"this.dataLeft: " + this.dataLeft);

        this.dataRight = mSoftStrategy.pictureWidth()-mSoftStrategy.horizontalPadding();
        Log.d(TAG,"this.dataRight: " + this.dataRight);

        this.rowHeight = (mSoftStrategy.pictureHeight()-mSoftStrategy.verticalPadding()*2)/mSoftStrategy.totalRows();
        Log.d(TAG,"this.rowHeight: " + this.rowHeight);

        transformer.setVisibleCoorport(0,mSoftStrategy.maxDataValueForMv(),mSoftStrategy.pointsPerRow(),-mSoftStrategy.maxDataValueForMv());
        for (int i = 0,rows = mSoftStrategy.totalRows();i < rows;i++){
            transformer.setDataContentRect(dataLeft,i*rowHeight,dataRight,(i+1)*rowHeight);

            //標記時間的 debug 使用
            //drawRowTimeDebug(canvas,dataLeft,(i+1)*rowHeight,i*mSoftStrategy.secondsPerRow()+"s");

            int start = i*mSoftStrategy.pointsPerRow();
            int end = Math.min((i+1)*mSoftStrategy.pointsPerRow(),mEcgData.size());

            for (int j = start;j < end-1;j++){
                float currentX = transformer.computeRawX((j-start));
                float currentY = transformer.computeRawY(mEcgData.get(j).getEcg().get(leadIndex)  / oneMV);
                float nextX = transformer.computeRawX((j+1-start));
                float nextY = transformer.computeRawY(mEcgData.get(j+1).getEcg().get(leadIndex)  / oneMV);

                if (!transformer.needDraw(currentY,nextY)){
                    continue;
                }
                canvas.drawLine(currentX,currentY,nextX,nextY,linePaint);


                if(mEcgData.get(j).isPacemaker() == 1) {
                    canvas.drawLine(currentX,currentY,nextX,nextY,peakPaint);
                }
            }
        }
    }

    private void initPaint(Canvas canvas, int type){
        /**
         * relation 設定線條也要跟著不同的屏幕分辨率上做調整，這樣轉出來輸出的畫面才會一致!!!
         * 參考文章 :
         * https://codejzy.com/posts-859534.html
         * https://stackoverflow.com/questions/11622773/android-line-width-pixel-size
         * */

        double relation = Math.sqrt(canvas.getWidth() * canvas.getHeight());
//        Log.d(TAG, "initPaint canvas relation: " + relation);
        relation = relation / 250;
//        Log.d(TAG, "initPaint canvas relation: " + relation);

        linePaint = new Paint();
        linePaint.setAntiAlias(false);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeWidth((float) (0.3 * relation));

        if(type == 1) {
            linePaint.setColor(GREEN_LEAD_LINE_COLOR);
        } else {
            linePaint.setColor(BLACK_LEAD_LINE_COLOR);
        }

        peakPaint = new Paint();
        peakPaint.setAntiAlias(false);
        peakPaint.setStyle(Paint.Style.FILL);
        peakPaint.setStrokeCap(Paint.Cap.ROUND);
        peakPaint.setTextSize(ChartUtils.sp2px(mScaleDensity,20));
        peakPaint.setColor(Color.parseColor("#ffc30d23"));

        //debug用
        timePaint = new Paint();
        timePaint.setAntiAlias(false);
        timePaint.setStyle(Paint.Style.FILL);
        timePaint.setStrokeCap(Paint.Cap.ROUND);
        timePaint.setTextSize(ChartUtils.sp2px(mScaleDensity,20));
        if(type == 1) {
            timePaint.setColor(BLACK_LEAD_LINE_COLOR);
        } else {
            timePaint.setColor(GREEN_LEAD_LINE_COLOR);
        }
    }

    private void drawRowTimeDebug(Canvas canvas, float left, float bottom, String text){
        int padding = ChartUtils.dp2px(mDensity,5);
        Paint.FontMetricsInt fontMetrics = timePaint.getFontMetricsInt();
        float startX = left +padding;
        float rectBottom = bottom-padding;
        float rectTop = rectBottom - ChartUtils.getTextHeight(timePaint,text);
        float baseline = (rectBottom + rectTop - fontMetrics.bottom - fontMetrics.top) / 2f;
        canvas.drawText(text,startX,baseline,timePaint);
    }
}
