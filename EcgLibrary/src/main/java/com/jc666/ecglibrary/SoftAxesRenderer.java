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
class SoftAxesRenderer extends RealRenderer{
    private String TAG = this.getClass().getSimpleName();

    private static final int ROW_COLOR = Color.parseColor("#57C2FB");  //藍色

    private static final int CELL_COLOR = Color.parseColor("#ffe70000");  //紅色

    private static final int LABEL_TEXT_COLOR = Color.parseColor("#021F52"); //深藍色

    private static final int LABEL_LEAD_COLOR = Color.parseColor("#ff202123"); //黑色

    private Paint rowPaint;//行與行之間的畫筆

    private Paint cellPaint;//網格畫筆

    private Paint labelTextPaint;//專門寫每個Lead名稱畫筆

    private Paint labelLeadPaint;//專門畫每個Lead起始位置的柱狀條畫筆

    SoftAxesRenderer(@NonNull Context context, @NonNull ECGPointValue[] values) {
        super(context, values);
        initPaint();
    }

    @Override
    public void draw(Canvas canvas) {
        int startX = mSoftStrategy.horizontalPadding();
        int endX = mSoftStrategy.pictureWidth() - mSoftStrategy.horizontalPadding();
        int startY = mSoftStrategy.verticalPadding();
        int endY = mSoftStrategy.pictureHeight() - mSoftStrategy.verticalPadding();

        Log.d(TAG, "SoftAxesRenderer draw startX: " + startX);
        Log.d(TAG, "SoftAxesRenderer draw endX: " + endX);
        Log.d(TAG, "SoftAxesRenderer draw startY: " + startY);
        Log.d(TAG, "SoftAxesRenderer draw endY: " + endY);

        drawHorizontalLine(canvas,startX,endX,startY,endY);
        drawVerticalLine(canvas,startX,endX,startY,endY);
    }

    private void drawHorizontalLine(Canvas canvas,int startX,int endX,int startY,int endY){

        int cellPixel = mSoftStrategy.pixelPerCell();
        int vCellCounts = (endY-startY)/cellPixel;

        Log.d(TAG, "drawHorizontalLine cellPixel: " + cellPixel);
        Log.d(TAG, "drawHorizontalLine vCellCounts: " + vCellCounts);
        Log.d(TAG, "drawHorizontalLine mSoftStrategy.cellCountPerGrid(): " + mSoftStrategy.cellCountPerGrid());
        Log.d(TAG, "drawHorizontalLine mSoftStrategy.gridCountPerRow(): " + mSoftStrategy.gridCountPerRow());


        for (int i = 0;i<=vCellCounts;i++){
            if (i == 0){
                canvas.drawLine(startX,startY+rowPaint.getStrokeWidth()/2, endX,startY+rowPaint.getStrokeWidth()/2, rowPaint);
            } else if (i == vCellCounts){
                canvas.drawLine(startX,endY-rowPaint.getStrokeWidth()/2, endX,endY-rowPaint.getStrokeWidth()/2, rowPaint);
            } else if (i % mSoftStrategy.cellCountPerGrid() == 0){
                if (i % (mSoftStrategy.cellCountPerGrid()*mSoftStrategy.gridCountPerRow()) == 0){
                    canvas.drawLine(startX,startY+(i*cellPixel), endX,startY+(i*cellPixel), rowPaint);
                } else {
                    canvas.drawLine(startX, startY+(i*cellPixel), endX, startY+(i*cellPixel), cellPaint);
                }
            }

            //畫各個Lead起始位置(劃出柱狀圖示)
            if(i == 5) {
                canvas.drawRect(startX, startY+(i*cellPixel), startX+(1*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
                canvas.drawRect(startX+(62*cellPixel), startY+(i*cellPixel), startX+(63*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
                canvas.drawRect(startX+(120*cellPixel), startY+(i*cellPixel), startX+(121*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
                canvas.drawRect(startX+(182*cellPixel), startY+(i*cellPixel), startX+(183*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
            } else if(i == 35) {
                canvas.drawRect(startX, startY+(i*cellPixel), startX+(1*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
                canvas.drawRect(startX+(62*cellPixel), startY+(i*cellPixel), startX+(63*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
                canvas.drawRect(startX+(120*cellPixel), startY+(i*cellPixel), startX+(121*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
                canvas.drawRect(startX+(182*cellPixel), startY+(i*cellPixel), startX+(183*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
            } else if(i == 65) {
                canvas.drawRect(startX, startY+(i*cellPixel), startX+(1*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
                canvas.drawRect(startX+(62*cellPixel), startY+(i*cellPixel), startX+(63*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
                canvas.drawRect(startX+(120*cellPixel), startY+(i*cellPixel), startX+(121*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
                canvas.drawRect(startX+(182*cellPixel), startY+(i*cellPixel), startX+(183*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
            } else if(i == 95) {
                canvas.drawRect(startX, startY+(i*cellPixel), startX+(1*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
            }

            //畫各個Lead label number
            if(i == 3) {
                drawRowLabel(canvas, startX, startY+((i + 1)*cellPixel), "I");
                drawRowLabel(canvas, startX+(62*cellPixel), startY+((i + 1)*cellPixel), "aVR");
                drawRowLabel(canvas, startX+(120*cellPixel), startY+((i + 1)*cellPixel), "V1");
                drawRowLabel(canvas, startX+(182*cellPixel), startY+((i + 1)*cellPixel), "V4");
            } else if(i == 33) {
                drawRowLabel(canvas, startX, startY+((i + 1)*cellPixel), "II");
                drawRowLabel(canvas, startX+(62*cellPixel), startY+((i + 1)*cellPixel), "aVL");
                drawRowLabel(canvas, startX+(120*cellPixel), startY+((i + 1)*cellPixel), "V2");
                drawRowLabel(canvas, startX+(182*cellPixel), startY+((i + 1)*cellPixel), "V5");
            } else if(i == 63) {
                drawRowLabel(canvas, startX, startY+((i + 1)*cellPixel), "III");
                drawRowLabel(canvas, startX+(62*cellPixel), startY+((i + 1)*cellPixel), "aVF");
                drawRowLabel(canvas, startX+(120*cellPixel), startY+((i + 1)*cellPixel), "V3");
                drawRowLabel(canvas, startX+(182*cellPixel), startY+((i + 1)*cellPixel), "V6");
            } else if(i == 93) {
                drawRowLabel(canvas, startX, startY+((i + 1)*cellPixel), "I");
            }

        }
    }

    private void drawRowLabel(Canvas canvas,float left,float bottom,String text){
        int padding = ChartUtils.dp2px(mDensity,5);
        Paint.FontMetricsInt fontMetrics = labelTextPaint.getFontMetricsInt();
        float startX = left + padding;
        float rectBottom = bottom - padding;
        float rectTop = rectBottom - ChartUtils.getTextHeight(labelTextPaint,text);
        float baseline = (rectBottom + rectTop - fontMetrics.bottom - fontMetrics.top) / 2f;
        canvas.drawText(text,startX,baseline,labelTextPaint);
    }

//    private void drawHorizontalPoint(Canvas canvas,float y,int startX,int endX){
//        int cellPixel = mSoftStrategy.pixelPerCell();
//        int hCellCounts = (endX-startX)/cellPixel;
//        for (int i = 0;i<=hCellCounts;i++){
//            if (i % (mSoftStrategy.cellCountPerGrid()) == 0 || i == hCellCounts){
//                continue;
//            }
//            canvas.drawPoint(startX+i*cellPixel,y,pointPaint);
//        }
//    }

    private void drawVerticalLine(Canvas canvas,int startX,int endX,int startY,int endY){
        int cellPixel = mSoftStrategy.pixelPerCell();
        int hCellCounts = (endX-startX)/cellPixel;
        Log.d(TAG, "drawVerticalLine cellPixel: " + cellPixel);
        Log.d(TAG, "drawVerticalLine hCellCounts: " + hCellCounts);
        Log.d(TAG, "drawHorizontalLine mSoftStrategy.cellCountPerGrid(): " + mSoftStrategy.cellCountPerGrid());

        for (int i = 0;i<=hCellCounts;i++){
            if (i == 0){
                canvas.drawLine(startX,startY,startX,endY,rowPaint);
            }else if (i == hCellCounts){
                canvas.drawLine(endX,startY,endX,endY,rowPaint);
            }else if (i % (mSoftStrategy.cellCountPerGrid()) == 0){
                canvas.drawLine(startX+i*cellPixel,startY,startX+i*cellPixel,endY,cellPaint);
            }
        }
    }

    private void initPaint(){
        rowPaint = new Paint();
        rowPaint.setAntiAlias(true);
        rowPaint.setColor(ROW_COLOR);
        rowPaint.setStrokeWidth(ChartUtils.dp2px(mDensity,3f));

        cellPaint = new Paint();
        cellPaint.setAntiAlias(true);
        cellPaint.setColor(CELL_COLOR);
        cellPaint.setAlpha(200);
        cellPaint.setStrokeWidth(ChartUtils.dp2px(mDensity,0.5f));

        labelLeadPaint = new Paint();
        labelLeadPaint.setAntiAlias(true);
        labelLeadPaint.setColor(LABEL_LEAD_COLOR);
        labelLeadPaint.setStrokeWidth(ChartUtils.dp2px(mDensity,2f));

        labelTextPaint = new Paint();
        labelTextPaint.setAntiAlias(true);
        labelTextPaint.setStyle(Paint.Style.FILL);
        labelTextPaint.setStrokeCap(Paint.Cap.ROUND);
        labelTextPaint.setTextSize(ChartUtils.sp2px(mScaleDensity,20));
        labelTextPaint.setColor(LABEL_TEXT_COLOR);
    }

}
