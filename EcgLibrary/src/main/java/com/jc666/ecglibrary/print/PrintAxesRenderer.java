package com.jc666.ecglibrary.print;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import androidx.annotation.NonNull;
import com.jc666.ecglibrary.ChartUtils;
import java.util.List;

/**
 * @author JC666
 * @date 2021/03/10
 * PrintAxesRenderer
 *
 */
class PrintAxesRenderer extends BriteMEDPrintRenderer {
    private String TAG = this.getClass().getSimpleName();

    private static final int RED_ROW_COLOR = Color.parseColor("#00C30D23");  //紅色

    private static final int RED_CELL_COLOR = Color.parseColor("#00C30D23");  //紅色

    private static final int RED_POINT_COLOR = Color.parseColor("#00C30D23");  //紅色

    private static final int GREED_ROW_COLOR = Color.parseColor("#0000C100");  //綠色

    private static final int GREED_CELL_COLOR = Color.parseColor("#0000C100");  //綠色

    private static final int GREED_POINT_COLOR = Color.parseColor("#0000C100");  //綠色

    private static final int LABEL_TEXT_COLOR = Color.parseColor("#FF000000"); //深藍色

    private static final int LABEL_LEAD_COLOR = Color.parseColor("#FF000000"); //黑色

    private Paint rowPaint;//行與行之間的畫筆

    private Paint cellPaint;//網格畫筆

    private Paint pointPaint;//網格畫點畫筆

    private Paint labelTextPaint;//專門寫每個Lead名稱畫筆

    private Paint labelLeadPaint;//專門畫每個Lead起始位置的柱狀條畫筆

    private int colorType;//紀錄畫筆顏色Type，0:紅色 1:綠色

    private int gridType = 0; // 區分網格類型0:1x12, 1:2x6, 2:4x3

    PrintAxesRenderer(@NonNull Context context, @NonNull List<ECGReportPrintDataFormat> values,@NonNull int type) {
        super(context, values);
        switch (values.size()) {
            case 30000:
                gridType = 1;
                break;
            case 60000:
                gridType = 0;
                break;
            default:
                gridType = 2; //20000
        }
        this.colorType = type;
    }

    @Override
    public void draw(Canvas canvas) {
        initPaint(canvas, colorType);
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

        if(gridType == 1) {
            vCellCounts+=5;
        } else if(gridType == 0) {
            vCellCounts+=5;
        }

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
            } else {
                drawHorizontalPoint(canvas,startY+i*cellPixel,startX,endX);
            }

            if(gridType == 2) {
                drawFourXThreeGrid(canvas, i, startX, startY, cellPixel);
            } else if(gridType == 1) {
                drawTwoXSixGrid(canvas, i, startX, startY, cellPixel);
            } else if(gridType == 0) {
                drawOneXTwelveGrid(canvas, i, startX, startY, cellPixel);
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

    private void drawOneXTwelveGrid(Canvas canvas, int index,int startX,int startY,int cellPixel){
        //畫各個Lead起始位置(劃出柱狀圖示)
        if(index == 0) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 15) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 30) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 45) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 60) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 75) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 90) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 105) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 120) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 135) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 150) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 165) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        }

        //畫各個Lead label number
        if(index == 0) {
            drawRowLabel(canvas, startX+(2*cellPixel), startY+((index + 1)*cellPixel), "I");
        } else if(index == 15) {
            drawRowLabel(canvas, startX+(2*cellPixel), startY+((index + 1)*cellPixel), "II");
        } else if(index == 30) {
            drawRowLabel(canvas, startX+(2*cellPixel), startY+((index + 1)*cellPixel), "III");
        } else if(index == 45) {
            drawRowLabel(canvas, startX+(2*cellPixel), startY+((index + 1)*cellPixel), "aVR");
        } else if(index == 60) {
            drawRowLabel(canvas, startX+(2*cellPixel), startY+((index + 1)*cellPixel), "aVL");
        } else if(index == 75) {
            drawRowLabel(canvas, startX+(2*cellPixel), startY+((index + 1)*cellPixel), "aVF");
        } else if(index == 90) {
            drawRowLabel(canvas, startX+(2*cellPixel), startY+((index + 1)*cellPixel), "V1");
        } else if(index == 105) {
            drawRowLabel(canvas, startX+(2*cellPixel), startY+((index + 1)*cellPixel), "V2");
        } else if(index == 120) {
            drawRowLabel(canvas, startX+(2*cellPixel), startY+((index + 1)*cellPixel), "V3");
        } else if(index == 135) {
            drawRowLabel(canvas, startX+(2*cellPixel), startY+((index + 1)*cellPixel), "V4");
        } else if(index == 150) {
            drawRowLabel(canvas, startX+(2*cellPixel), startY+((index + 1)*cellPixel), "V5");
        } else if(index == 165) {
            drawRowLabel(canvas, startX+(2*cellPixel), startY+((index + 1)*cellPixel), "V6");
        }else if(index == 178) {
            drawRowLabel(canvas, startX+(210*cellPixel), startY+((index + 2)*cellPixel), "25mm/1S   10mm/1mv");
        }
    }

    private void drawTwoXSixGrid(Canvas canvas, int index,int startX,int startY,int cellPixel){
        //畫各個Lead起始位置(劃出柱狀圖示)
        if(index == 5) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(125*cellPixel), startY+(index*cellPixel), startX+(126*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 35) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(125*cellPixel), startY+(index*cellPixel), startX+(126*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 65) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(125*cellPixel), startY+(index*cellPixel), startX+(126*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 95) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(125*cellPixel), startY+(index*cellPixel), startX+(126*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 125) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(125*cellPixel), startY+(index*cellPixel), startX+(126*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 160) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(125*cellPixel), startY+(index*cellPixel), startX+(126*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        }

        //畫各個Lead label number
        if(index == 3) {
            drawRowLabel(canvas, startX, startY+((index + 1)*cellPixel), "I");
            drawRowLabel(canvas, startX+(125*cellPixel), startY+((index + 1)*cellPixel), "V1");
        } else if(index == 33) {
            drawRowLabel(canvas, startX, startY+((index + 1)*cellPixel), "II");
            drawRowLabel(canvas, startX+(125*cellPixel), startY+((index + 1)*cellPixel), "V2");
        } else if(index == 63) {
            drawRowLabel(canvas, startX, startY+((index + 1)*cellPixel), "III");
            drawRowLabel(canvas, startX+(125*cellPixel), startY+((index + 1)*cellPixel), "V3");
        } else if(index == 93) {
            drawRowLabel(canvas, startX, startY+((index + 1)*cellPixel), "aVR");
            drawRowLabel(canvas, startX+(125*cellPixel), startY+((index + 1)*cellPixel), "V4");
        } else if(index == 123) {
            drawRowLabel(canvas, startX, startY+((index + 1)*cellPixel), "aVL");
            drawRowLabel(canvas, startX+(125*cellPixel), startY+((index + 1)*cellPixel), "V5");
        } else if(index == 153) {
            drawRowLabel(canvas, startX, startY+((index + 1)*cellPixel), "aVF");
            drawRowLabel(canvas, startX+(125*cellPixel), startY+((index + 1)*cellPixel), "V6");
        } else if(index == 178) {
            drawRowLabel(canvas, startX+(210*cellPixel), startY+((index + 2)*cellPixel), "25mm/1S   10mm/1mv");
        }
    }

    private void drawFourXThreeGrid(Canvas canvas, int index,int startX,int startY,int cellPixel){
        //畫各個Lead起始位置(劃出柱狀圖示)
        if(index == 5) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(62*cellPixel), startY+(index*cellPixel), startX+(63*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(125*cellPixel), startY+(index*cellPixel), startX+(126*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(187*cellPixel), startY+(index*cellPixel), startX+(188*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 35) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(62*cellPixel), startY+(index*cellPixel), startX+(63*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(125*cellPixel), startY+(index*cellPixel), startX+(126*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(187*cellPixel), startY+(index*cellPixel), startX+(188*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 65) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(62*cellPixel), startY+(index*cellPixel), startX+(63*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(125*cellPixel), startY+(index*cellPixel), startX+(126*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
            canvas.drawRect(startX+(187*cellPixel), startY+(index*cellPixel), startX+(188*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        } else if(index == 95) {
            canvas.drawRect(startX, startY+(index*cellPixel), startX+(1*cellPixel), startY+((index + 10)*cellPixel), labelLeadPaint);
        }

        //畫各個Lead label number
        if(index == 3) {
            drawRowLabel(canvas, startX, startY+((index + 1)*cellPixel), "I");
            drawRowLabel(canvas, startX+(62*cellPixel), startY+((index + 1)*cellPixel), "aVR");
            drawRowLabel(canvas, startX+(125*cellPixel), startY+((index + 1)*cellPixel), "V1");
            drawRowLabel(canvas, startX+(187*cellPixel), startY+((index + 1)*cellPixel), "V4");
        } else if(index == 33) {
            drawRowLabel(canvas, startX, startY+((index + 1)*cellPixel), "II");
            drawRowLabel(canvas, startX+(62*cellPixel), startY+((index + 1)*cellPixel), "aVL");
            drawRowLabel(canvas, startX+(125*cellPixel), startY+((index + 1)*cellPixel), "V2");
            drawRowLabel(canvas, startX+(187*cellPixel), startY+((index + 1)*cellPixel), "V5");
        } else if(index == 63) {
            drawRowLabel(canvas, startX, startY+((index + 1)*cellPixel), "III");
            drawRowLabel(canvas, startX+(62*cellPixel), startY+((index + 1)*cellPixel), "aVF");
            drawRowLabel(canvas, startX+(125*cellPixel), startY+((index + 1)*cellPixel), "V3");
            drawRowLabel(canvas, startX+(187*cellPixel), startY+((index + 1)*cellPixel), "V6");
        } else if(index == 93) {
            //未來要開出來，可以選擇要顯示哪個lead label
            drawRowLabel(canvas, startX, startY+((index + 1)*cellPixel), "II");
        } else if(index == 118) {
            drawRowLabel(canvas, startX+(210*cellPixel), startY+((index + 2)*cellPixel), "25mm/1S   10mm/1mv");
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
        if(type == 0) {
            rowPaint.setColor(RED_ROW_COLOR);
        } else {
            rowPaint.setColor(GREED_ROW_COLOR);
        }
        rowPaint.setAlpha(200);
        rowPaint.setStrokeWidth((float) (0.1 * relation));
        rowPaint.setAntiAlias(false);
        rowPaint.setStyle(Paint.Style.STROKE);

        cellPaint = new Paint();
        if(type == 0) {
            cellPaint.setColor(RED_CELL_COLOR);
        } else {
            cellPaint.setColor(GREED_CELL_COLOR);
        }
        cellPaint.setAlpha(200);
        cellPaint.setStrokeWidth((float) (0.1 * relation));
        cellPaint.setAntiAlias(false);
        cellPaint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint();
        if(type == 0) {
            pointPaint.setColor(RED_POINT_COLOR);
        } else {
            pointPaint.setColor(GREED_POINT_COLOR);
        }
        pointPaint.setAlpha(200);
        pointPaint.setStrokeWidth((float) (0.2 * relation));
        pointPaint.setAntiAlias(false);
        pointPaint.setStyle(Paint.Style.STROKE);

        labelLeadPaint = new Paint();
        labelLeadPaint.setAntiAlias(false);
        labelLeadPaint.setColor(LABEL_LEAD_COLOR);
        labelLeadPaint.setStrokeWidth(ChartUtils.dip2px(mDisplayMetrics,2f));

        labelTextPaint = new Paint();
        labelTextPaint.setAntiAlias(false);
        labelTextPaint.setStyle(Paint.Style.FILL);
        labelTextPaint.setStrokeCap(Paint.Cap.ROUND);
        labelTextPaint.setTextSize((float) (4 * relation));
        labelTextPaint.setColor(LABEL_TEXT_COLOR);
        labelTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

    }

}
