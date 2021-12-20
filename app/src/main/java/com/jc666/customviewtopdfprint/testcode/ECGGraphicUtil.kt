package com.jc666.customviewtopdfprint.testcode

import android.content.res.Resources
import android.graphics.*
import kotlin.math.roundToInt

import android.graphics.Bitmap


class ECGGraphicUtil {

    init {
        Bitmap.createBitmap(842, 595, Bitmap.Config.ARGB_8888).also { softwareBitmap = it }
    }

    var barDatas: List<BarData> = listOf(
        BarData("2000", 23f),
        BarData("2001", 34f),
        BarData("2002", 10f),
        BarData("2003", 93f),
        BarData("2004", 77f)
    )

    private var softwareBitmap: Bitmap? = null

    private val rowPaint = Paint().apply {
        color = Color.BLUE
    }

    private val cellPaint = Paint().apply {
        color = Color.RED
    }

    private val linePaint = Paint().apply {
        strokeWidth = 8.toPx().toFloat()
        color = Color.GRAY
    }

    private val valueTextPaint = Paint().apply {
        color = Color.BLACK
        textSize = 8.toPx().toFloat()
        textAlign = Paint.Align.RIGHT
    }

    private val startDistanceWidth = 0.toPx()
    private val startDistanceHeight = 0.toPx()
    private val endDistanceWidth = 250.toPx()
    private val endDistanceHeight = 120.toPx()
    private val cellPixel = 10
    private val vCellCounts = 120
    private val cellCountPerGrid = 5
    private val gridCountPerRow = 6

    private val barWidth = 30.toPx()
    private val barDistance = 30.toPx()
    private val maxValue = 200

    fun onDraw(canvas: Canvas) : Bitmap{
        canvas.setBitmap(softwareBitmap!!)

        drawHorizontalLine(canvas)

        return softwareBitmap!!
    }

    private fun drawHorizontalLine(canvas: Canvas) {

        for (i in 0 .. vCellCounts){
            if(i == 0) {
                canvas.drawLine(startDistanceWidth.toFloat(),
                    endDistanceHeight.toFloat(),
                    endDistanceWidth.toFloat(),
                    endDistanceHeight.toFloat(), rowPaint)
            } else if(i == vCellCounts) {
                canvas.drawLine(startDistanceWidth.toFloat(),
                    endDistanceHeight.toFloat(),
                    endDistanceWidth.toFloat(),
                    endDistanceHeight.toFloat(), rowPaint)
            } else if(i%cellCountPerGrid == 0) {
                canvas.drawLine(startDistanceWidth.toFloat(),
                    (startDistanceHeight+i*cellPixel).toFloat(),
                    endDistanceWidth.toFloat(),
                    (startDistanceHeight+i*cellPixel).toFloat(), cellPaint)
            }
        }
    }

    private fun drawVerticalLine(canvas: Canvas) {





    }


    private fun getTextValueWidth(): Float {
        val rect = Rect()
        valueTextPaint.getTextBounds(maxValue.toString(), 0, maxValue.toString().length, rect)
        return rect.width().toFloat()
    }

    private fun drawAxis(canvas: Canvas) {
        //x 軸
        canvas.drawLine(
            getValueWidth(),
            softwareBitmap!!.height.toFloat() - getLabelHeight(),
            getValueWidth() + softwareBitmap!!.width.toFloat(),
            softwareBitmap!!.height.toFloat() - getLabelHeight(),
            linePaint
        )
        //y 軸
        canvas.drawLine(
            getValueWidth(),
            0f,
            getValueWidth(),
            softwareBitmap!!.height.toFloat() - getLabelHeight(),
            linePaint
        )
    }

    private fun drawBar(canvas: Canvas) {
        barDatas.forEachIndexed { index, barData ->
            val left = index * (barWidth + barDistance) + getValueWidth()
            val right = left + barWidth
            val bottom = softwareBitmap!!.height - getLabelHeight()
            val top = bottom * (1 - barData.value / maxValue)
//            canvas.drawRect(left, top, right, bottom, barPaint)
        }
    }

    private fun drawValue(canvas: Canvas) {
        val x = getValueWidth()
        val fontMetrics = valueTextPaint.fontMetrics
        // 要先移動 (top) 這段距離才不會遮住字
        val y = - fontMetrics.top
        // 只畫 maxValue
        canvas.drawText(maxValue.toString(), x, y, valueTextPaint)
    }

    private val labelTextPaint = Paint().apply {
        color = Color.BLACK
        textSize = 16.toPx().toFloat()
        textAlign = Paint.Align.CENTER
    }

    private fun drawLabels(canvas: Canvas) {
        barDatas.forEachIndexed { index, barData ->
            val x = getValueWidth() + index * (barWidth + barDistance) + barWidth / 2
            val fontMetrics = labelTextPaint.fontMetrics
            val y = softwareBitmap!!.height - fontMetrics.bottom
            canvas.drawText(barData.name, x, y, labelTextPaint)
        }
    }

    private fun getValueWidth(): Float {
        val rect = Rect()
        valueTextPaint.getTextBounds(maxValue.toString(), 0, maxValue.toString().length, rect)
        return rect.width().toFloat()
    }

    private fun getLabelHeight(): Float {
        val metrics = labelTextPaint.fontMetrics
        return metrics.bottom - metrics.top
    }

    //Dp 轉 pixel 的 extension function
    fun Int.toPx(): Int {
        return (Resources.getSystem().displayMetrics.density * this).roundToInt()
    }

    fun dp2px(density: Float, dp: Float): Int {
        return if (dp == 0f) {
            0
        } else (dp * density + 0.5f).toInt()
    }

    fun sp2px(scaledDensity: Float, sp: Float): Int {
        return if (sp == 0f) {
            0
        } else (sp * scaledDensity + 0.5f).toInt()
    }
}