package com.jc666.ecglibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import androidx.annotation.NonNull;


/**
 * @author JC666
 * @date 2021/12/15
 * @describe TODO
 */
class SoftDataRenderer extends RealRenderer{
    private String TAG = this.getClass().getSimpleName();

    private Transformer transformer;

    private int dataLeft;

    private int dataRight;

    private int rowHeight;

    private Paint linePaint;

    private Paint timePaint;

    SoftDataRenderer(@NonNull Context context, @NonNull ECGPointValue[] values) {
        super(context, values);
        initPaint();
    }

    @Override
    public void draw(Canvas canvas) {
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

            //標記時間的
            drawRowTime(canvas,dataLeft,(i+1)*rowHeight,i*mSoftStrategy.secondsPerRow()+"s");

            int start = i*mSoftStrategy.pointsPerRow();
            int end = Math.min((i+1)*mSoftStrategy.pointsPerRow(),mEcgData.length);

            for (int j = start;j < end-1;j++){
                float currentX = transformer.computeRawX((j-start));
                float currentY = transformer.computeRawY(mEcgData[j].getCoorY());
                float nextX = transformer.computeRawX((j+1-start));
                float nextY = transformer.computeRawY(mEcgData[j+1].getCoorY());



                if (!transformer.needDraw(currentY,nextY)){
                    continue;
                }
                canvas.drawLine(currentX,currentY,nextX,nextY,linePaint);
            }
        }
    }

    private void initPaint(){
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeWidth(ChartUtils.dp2px(mDensity, 2));
        linePaint.setColor(Color.parseColor("#021F52"));

        timePaint = new Paint();
        timePaint.setAntiAlias(true);
        timePaint.setStyle(Paint.Style.FILL);
        timePaint.setStrokeCap(Paint.Cap.ROUND);
        timePaint.setTextSize(ChartUtils.sp2px(mScaleDensity,20));
        timePaint.setColor(Color.parseColor("#021F52"));
    }

    private void drawRowTime(Canvas canvas,float left,float bottom,String text){
        int padding = ChartUtils.dp2px(mDensity,5);
        Paint.FontMetricsInt fontMetrics = timePaint.getFontMetricsInt();
        float startX = left +padding;
        float rectBottom = bottom-padding;
        float rectTop = rectBottom - ChartUtils.getTextHeight(timePaint,text);
        float baseline = (rectBottom + rectTop - fontMetrics.bottom - fontMetrics.top) / 2f;
        canvas.drawText(text,startX,baseline,timePaint);
    }
}
