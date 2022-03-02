package com.jc666.ecglibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import androidx.annotation.NonNull;
import java.util.List;

/**
 * @author JC666
 * @date 2022/03/01
 * @describe TODO
 */
class ECGBackgroundRenderer extends BriteMEDRealRenderer{
    private String TAG = this.getClass().getSimpleName();

    private static final int GRAY_ROW_COLOR = Color.parseColor("#ff949497");  //灰色

    private static final int GRAY_CELL_COLOR = Color.parseColor("#ff949497");  //灰色

    private static final int GRAY_POINT_COLOR = Color.parseColor("#ff949497");  //灰色

    //lead 文字顏色
    private static final int BLACK_LABEL_LEAD_TEXT_COLOR = Color.parseColor("#ff000000"); //黑色
    private static final int WHITE_LABEL_LEAD_TEXT_COLOR = Color.parseColor("#ffffffff"); //白色

    //lead 起始位置顏色
    private static final int BLACK_LABEL_LEAD_COLOR = Color.parseColor("#ff000000"); //黑色
    private static final int GREEN_LABEL_LEAD_COLOR = Color.parseColor("#ff00c100"); //暗綠色

    //lead 單位文字顏色
    private static final int BLACK_LABEL_LEAD_UNIT_TEXT_COLOR = Color.parseColor("#ff000000"); //黑色
    private static final int GREEN_LABEL_LEAD_UNIT_TEXT_COLOR = Color.parseColor("#ff00c100"); //暗綠色

    private Paint rowPaint;//行與行之間的畫筆

    private Paint cellPaint;//網格畫筆

    private Paint pointPaint;//網格畫點畫筆

    private Paint labelTextPaint;//專門寫每個Lead名稱畫筆

    private Paint labelUnitTextPaint;//專門寫每個Lead單位名稱畫筆

    private Paint labelLeadPaint;//專門畫每個Lead起始位置的柱狀條畫筆

    private int colorType;//紀錄畫筆顏色Type，0:黑色(白色波形,全部都黑色) 1:白色(綠色波形)

    private String leadName = "Error";

    private String gainValue = "Error";

    ECGBackgroundRenderer(@NonNull Context context, @NonNull List<ECGReportDataFormat> values,@NonNull int type, String leadName, int gain) {
        super(context, values);
        this.colorType = type;
        this.leadName = leadName;
        switch (gain) {
            case 0 :
                this.gainValue = "2mv";
                break;
            case 1 :
                this.gainValue = "1mv";
                break;
            case 2 :
                this.gainValue = "0.5mv";
                break;
            case 3 :
                this.gainValue = "0,25mv";
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        initPaint(canvas, colorType);
        int startX = mSoftStrategy.horizontalPadding();
        int endX = mSoftStrategy.pictureWidth() - mSoftStrategy.horizontalPadding();
        int startY = mSoftStrategy.verticalPadding();
        int endY = mSoftStrategy.pictureHeight() - mSoftStrategy.verticalPadding();

        Log.d(TAG, "ECGBackgroundRenderer draw startX: " + startX);
        Log.d(TAG, "ECGBackgroundRenderer draw endX: " + endX);
        Log.d(TAG, "ECGBackgroundRenderer draw startY: " + startY);
        Log.d(TAG, "ECGBackgroundRenderer draw endY: " + endY);

        drawHorizontalLine(canvas,startX,endX,startY,endY);
        drawVerticalLine(canvas,startX,endX,startY,endY);
    }

    private void drawHorizontalLine(Canvas canvas,int startX,int endX,int startY,int endY){

        int cellPixel = mSoftStrategy.pixelPerCell();
        int vCellCounts = (endY-startY)/cellPixel;

        //vCellCounts+=5;

        Log.d(TAG, "drawHorizontalLine cellPixel: " + cellPixel);
        Log.d(TAG, "drawHorizontalLine vCellCounts: " + vCellCounts);
        Log.d(TAG, "drawHorizontalLine mSoftStrategy.cellCountPerGrid(): " + mSoftStrategy.cellCountPerGrid());
        Log.d(TAG, "drawHorizontalLine mSoftStrategy.gridCountPerRow(): " + mSoftStrategy.gridCountPerRow());


        for (int i = 0;i <= vCellCounts; i++){
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
            } else {
                canvas.drawLine(startX,startY+(i*cellPixel), endX,startY+(i*cellPixel), pointPaint);
                //drawHorizontalPoint(canvas,startY+i*cellPixel,startX,endX);
            }

            //畫lead 柱
            if(i == 10) {
                canvas.drawRect(startX+(1*cellPixel), startY+(i*cellPixel), startX+(2*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
                canvas.drawRect(startX+(3*cellPixel), startY+(i*cellPixel), startX+(4*cellPixel), startY+((i + 10)*cellPixel), labelLeadPaint);
                canvas.drawRect(startX+(1*cellPixel), startY+(i*cellPixel), startX+(4*cellPixel), startY+((i + 1)*cellPixel), labelLeadPaint);
            }
            //畫lead 柱
            if(i == 19) {
                canvas.drawRect(startX+(0*cellPixel), startY+(i*cellPixel), startX+(1*cellPixel), startY+((i + 1)*cellPixel), labelLeadPaint);
                canvas.drawRect(startX+(4*cellPixel), startY+(i*cellPixel), startX+(5*cellPixel), startY+((i + 1)*cellPixel), labelLeadPaint);
            }

            //畫lead 文字
            if(i == 5) {
                drawRowLabel(canvas, startX+(0*cellPixel), startY+((i + 1)*cellPixel), leadName);
            }

            //畫lead 單位
            if(i == 27) {
                drawRowLabelUnit(canvas, startX+(0*cellPixel), startY+((i + 1)*cellPixel), gainValue);
            }

        }

    }

    private void drawRowLabel(Canvas canvas,float left,float bottom,String text){
        int padding = ChartUtils.dp2px(mDensity,5);
//        int padding = dpToPx(5);
        Paint.FontMetricsInt fontMetrics = labelTextPaint.getFontMetricsInt();
        float startX = left + padding;
        float rectBottom = bottom - padding;
        float rectTop = rectBottom - ChartUtils.getTextHeight(labelTextPaint,text);
        float baseline = (rectBottom + rectTop - fontMetrics.bottom - fontMetrics.top) / 2f;
        canvas.drawText(text,startX,baseline,labelTextPaint);
    }

    private void drawRowLabelUnit(Canvas canvas,float left,float bottom,String text){
        int padding = ChartUtils.dp2px(mDensity,5);
        //        int padding = dpToPx(5);
        Paint.FontMetricsInt fontMetrics = labelUnitTextPaint.getFontMetricsInt();
        float startX = left + padding;
        float rectBottom = bottom - padding;
        float rectTop = rectBottom - ChartUtils.getTextHeight(labelUnitTextPaint,text);
        float baseline = (rectBottom + rectTop - fontMetrics.bottom - fontMetrics.top) / 2f;
        canvas.drawText(text,startX,baseline,labelUnitTextPaint);
    }

    private void drawVerticalLine(Canvas canvas,int startX,int endX,int startY,int endY){
        int cellPixel = mSoftStrategy.pixelPerCell();
        int hCellCounts = (endX-startX)/cellPixel;
        Log.d(TAG, "drawVerticalLine cellPixel: " + cellPixel);
        Log.d(TAG, "drawVerticalLine hCellCounts: " + hCellCounts);
        Log.d(TAG, "drawVerticalLine mSoftStrategy.cellCountPerGrid(): " + mSoftStrategy.cellCountPerGrid());

        for (int i = 0; i <= hCellCounts; i++){
            if (i == 0){
                canvas.drawLine(startX,startY,startX,endY,rowPaint);
            }else if (i == hCellCounts){
                canvas.drawLine(endX,startY,endX,endY,rowPaint);
            }else if (i % (mSoftStrategy.cellCountPerGrid()) == 0){
                canvas.drawLine(startX+i*cellPixel, startY,startX+i*cellPixel, endY, cellPaint);
            } else {
                canvas.drawLine(startX+(i*cellPixel), startY, startX+i*cellPixel, endY, pointPaint);
            }
        }
    }

    private void drawHorizontalPointLine(Canvas canvas,float y,int startX,int endX){
        int cellPixel = mSoftStrategy.pixelPerCell();
        int hCellCounts = (endX-startX)/cellPixel;
        for (int i = 0;i<=hCellCounts;i++){
            if (i % (mSoftStrategy.cellCountPerGrid()) == 0 || i == hCellCounts){
                continue;
            }
            canvas.drawPoint(startX+i*cellPixel,y,pointPaint);
        }
    }

    private void drawHorizontalPoint(Canvas canvas,float y,int startX,int endX){
        int cellPixel = mSoftStrategy.pixelPerCell();
        int hCellCounts = (endX-startX)/cellPixel;
        for (int i = 0;i<=hCellCounts;i++){
            if (i % (mSoftStrategy.cellCountPerGrid()) == 0 || i == hCellCounts){
                continue;
            }
            canvas.drawPoint(startX+i*cellPixel,y,pointPaint);
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

        rowPaint = new Paint();
        rowPaint.setColor(GRAY_ROW_COLOR);
        rowPaint.setAlpha(200);
        rowPaint.setStrokeWidth((float) (0.8 * relation));
        rowPaint.setAntiAlias(false);
        rowPaint.setStyle(Paint.Style.STROKE);

        cellPaint = new Paint();
        cellPaint.setColor(GRAY_CELL_COLOR);
        cellPaint.setAlpha(200);
        cellPaint.setStrokeWidth((float) (0.8 * relation));
        cellPaint.setAntiAlias(false);
        cellPaint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint();
        pointPaint.setColor(GRAY_POINT_COLOR);
        pointPaint.setAlpha(200);
        pointPaint.setStrokeWidth((float) (0.2 * relation));
        pointPaint.setAntiAlias(false);
        pointPaint.setStyle(Paint.Style.STROKE);

        labelLeadPaint = new Paint();
        labelLeadPaint.setAntiAlias(false);
        if(colorType == 0) {
            labelLeadPaint.setColor(BLACK_LABEL_LEAD_TEXT_COLOR);
        } else {
            labelLeadPaint.setColor(WHITE_LABEL_LEAD_TEXT_COLOR);
        }
        labelLeadPaint.setStrokeWidth(ChartUtils.dip2px(mDisplayMetrics,2f));

        labelTextPaint = new Paint();
        labelTextPaint.setAntiAlias(false);
        labelTextPaint.setStyle(Paint.Style.FILL);
        labelTextPaint.setStrokeCap(Paint.Cap.ROUND);
        labelTextPaint.setTextSize((float) (4 * relation));
        if(colorType == 0) {
            labelTextPaint.setColor(BLACK_LABEL_LEAD_COLOR);
        } else {
            labelTextPaint.setColor(GREEN_LABEL_LEAD_COLOR);
        }
        labelTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        labelUnitTextPaint = new Paint();
        labelUnitTextPaint.setAntiAlias(false);
        labelUnitTextPaint.setStyle(Paint.Style.FILL);
        labelUnitTextPaint.setStrokeCap(Paint.Cap.ROUND);
        labelUnitTextPaint.setTextSize((float) (4 * relation));
        if(colorType == 0) {
            labelUnitTextPaint.setColor(BLACK_LABEL_LEAD_UNIT_TEXT_COLOR);
        } else {
            labelUnitTextPaint.setColor(GREEN_LABEL_LEAD_UNIT_TEXT_COLOR);
        }
        labelUnitTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

    }

}
