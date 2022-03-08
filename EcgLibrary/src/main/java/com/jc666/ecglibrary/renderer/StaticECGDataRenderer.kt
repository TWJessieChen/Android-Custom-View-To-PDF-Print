package com.jc666.ecglibrary.renderer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.Log
import com.jc666.ecglibrary.*
import com.jc666.ecglibrary.view.ECGTransformer

/**
 * @author JC666
 * @date 2022/03/08
 * @describe TODO
 */
internal class StaticECGDataRenderer(context: Context,
                                     height: Int,
                                     values: List<ECGReportDataFormat>,
                                     leadIndex: Int,
                                     drawType: Int,
                                     gainValue: Float) {
    private val TAG = this.javaClass.simpleName

    private var colorType = 0 //紀錄畫筆顏色Type，0:黑色 1:綠色

    private var mECGTransformer: ECGTransformer? = null

    private var mHeight:Int = 0

    //默认每行所表示的上下最大毫伏数 (maxDataValueForMv,-maxDataValueForMv)
    private var maxDataValueForMv = 1.0f

    protected var mDensity = 0f

    protected var mScaleDensity = 0f

    protected var mDisplayMetrics: DisplayMetrics? = null

    private var dataLeft = 0
    private var dataRight = 0
    private var rowHeight = 0
    private val leadIndex: Int
    private var linePaint: Paint? = null
    private var timePaint: Paint? = null
    private var peakPaint: Paint? = null

    private var mEcgDataList: List<ECGReportDataFormat>? = null

    fun draw(canvas: Canvas) {
        initPaint(canvas, colorType)
        mECGTransformer = ECGTransformer()

        Log.d(TAG, "this.dataLeft: " + dataLeft)
        dataRight = Math.round(mEcgDataList!!.size * 1f)
        Log.d(TAG, "this.dataRight: " + dataRight)
        rowHeight = mHeight
        Log.d(TAG, "this.rowHeight: " + rowHeight)
        mECGTransformer!!.setVisibleCoorport(
            0f,
            maxDataValueForMv,
            mEcgDataList!!.size.toFloat(),
            -maxDataValueForMv
        )
        var i = 0
        val rows = 1
        while (i < rows) {
            mECGTransformer!!.setDataContentRect(
                dataLeft,
                i * rowHeight,
                dataRight,
                (i + 1) * rowHeight
            )

            //標記時間的 debug 使用
            //drawRowTimeDebug(canvas,dataLeft,(i+1)*rowHeight,i*mSoftStrategy.secondsPerRow()+"s");
            val start = i * mEcgDataList!!.size
            val end = Math.min((i + 1) * mEcgDataList!!.size, mEcgDataList!!.size)
            for (j in start until end - 1) {
                val currentX = mECGTransformer!!.computeRawX(j - start)
                val currentY = mECGTransformer!!.computeRawY(mEcgDataList!![j].ecg[leadIndex] / oneMV)
                val nextX = mECGTransformer!!.computeRawX(j + 1 - start)
                val nextY = mECGTransformer!!.computeRawY(mEcgDataList!![j + 1].ecg[leadIndex] / oneMV)
                if (!mECGTransformer!!.needDraw(currentY, nextY)) {
                    continue
                }
                canvas.drawLine(currentX, currentY, nextX, nextY, linePaint!!)
                if (mEcgDataList!![j].isPacemaker == 1) {
                    canvas.drawLine(currentX, currentY, nextX, nextY, peakPaint!!)
                }
            }
            i++
        }
    }

    private fun initPaint(canvas: Canvas, type: Int) {
        /**
         * relation 設定線條也要跟著不同的屏幕分辨率上做調整，這樣轉出來輸出的畫面才會一致!!!
         * 參考文章 :
         * https://codejzy.com/posts-859534.html
         * https://stackoverflow.com/questions/11622773/android-line-width-pixel-size
         */
        var relation = Math.sqrt((canvas.width * canvas.height).toDouble())
        //        Log.d(TAG, "initPaint canvas relation: " + relation);
        relation = relation / 250
        //        Log.d(TAG, "initPaint canvas relation: " + relation);
        linePaint = Paint()
        linePaint!!.isAntiAlias = false
        linePaint!!.style = Paint.Style.STROKE
        linePaint!!.strokeCap = Paint.Cap.ROUND
        linePaint!!.strokeWidth = (0.3 * relation).toFloat()
        if (type == 1) {
            linePaint!!.color = GREEN_LEAD_LINE_COLOR
        } else {
            linePaint!!.color = BLACK_LEAD_LINE_COLOR
        }
        peakPaint = Paint()
        peakPaint!!.isAntiAlias = false
        peakPaint!!.style = Paint.Style.FILL
        peakPaint!!.strokeCap = Paint.Cap.ROUND
        peakPaint!!.textSize = ChartUtils.sp2px(mScaleDensity, 20f).toFloat()
        peakPaint!!.color = Color.parseColor("#ffc30d23")

        //debug用
        timePaint = Paint()
        timePaint!!.isAntiAlias = false
        timePaint!!.style = Paint.Style.FILL
        timePaint!!.strokeCap = Paint.Cap.ROUND
        timePaint!!.textSize = ChartUtils.sp2px(mScaleDensity, 20f).toFloat()
        if (type == 1) {
            timePaint!!.color = BLACK_LEAD_LINE_COLOR
        } else {
            timePaint!!.color = GREEN_LEAD_LINE_COLOR
        }
    }

    private fun drawRowTimeDebug(canvas: Canvas, left: Float, bottom: Float, text: String) {
        val padding = ChartUtils.dp2px(mDensity, 5f)
        val fontMetrics = timePaint!!.fontMetricsInt
        val startX = left + padding
        val rectBottom = bottom - padding
        val rectTop = rectBottom - ChartUtils.getTextHeight(timePaint, text)
        val baseline = (rectBottom + rectTop - fontMetrics.bottom - fontMetrics.top) / 2f
        canvas.drawText(text, startX, baseline, timePaint!!)
    }

    companion object {
        private val BLACK_LEAD_LINE_COLOR = Color.parseColor("#ff000000") //黑色
        private val GREEN_LEAD_LINE_COLOR = Color.parseColor("#ff00fb00") //亮綠色
        private const val oneMV = 13981f
    }

    init {
        colorType = drawType
        maxDataValueForMv = gainValue
        this.leadIndex = leadIndex
        this.mEcgDataList = values
        this.mDisplayMetrics = context.getResources().getDisplayMetrics()
        this.mDensity = mDisplayMetrics!!.density
        this.mScaleDensity = mDisplayMetrics!!.scaledDensity
        this.mHeight = height
    }
}