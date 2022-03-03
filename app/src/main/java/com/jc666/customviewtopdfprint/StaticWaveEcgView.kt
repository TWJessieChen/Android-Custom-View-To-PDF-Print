package com.jc666.customviewtopdfprint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.jc666.ecglibrary.ChartUtils
import android.R.attr.bitmap




class StaticWaveEcgView : View {
    private val TAG = StaticWaveEcgView::class.java.simpleName

    private val NAMESPACE = "http://schemas.android.com/apk/res-auto"

    /**
     * 绘制模式
     */
    private var drawMode = 0

    /**
     * 宽高
     */
    private var mWidth = 0f
    private var mHeight = 0f

    /**
     * 网格画笔
     */
    private var mLinePaint: Paint? = null

    /**
     * 数据线画笔
     */
    private var mWavePaint: Paint? = null

    /**
     * 线条的路径
     */
    private var mPath: Path? = null

    /**
     * 保存已绘制的数据坐标
     */
    private lateinit var dataArray: FloatArray

    /**
     * 数据最大值，默认-20~20之间
     */
    private var MAX_VALUE = 20f

    /**
     * 线条粗细
     */
    private var WAVE_LINE_STROKE_WIDTH = 1f

    /**
     * 波形颜色
     */
    private var waveLineColor = Color.parseColor("#ff00c100")

    /**
     * 当前的x，y坐标
     */
    private var nowX = 0f
    private var nowY = 0f
    private var startY = 0f

    /**
     * 线条的长度，可用于控制横坐标
     */
    private var WAVE_LINE_WIDTH = 5

    /**
     * 数据点的数量
     */
    private var row = 0
    private var draw_index = 0
    private var isRefresh = false

    /**
     * 网格是否可见(預設開啟顯示網格)
     */
    private var gridVisible = true

    /**
     * 小网格是否可见(預設開啟顯示網格)
     */
    private var gridSmallVisible = false

    /**
     * 网格的横线和竖线的数量
     */
    private var gridHorizontalNum = 0
    private var gridVerticalNum = 0
    private var gridHorizontalSmallNum = 0
    private var gridVerticalSmallNum = 0

    /**
     * 网格线条的粗细
     */
    private val GRID_LINE_WIDTH = 1

    /**
     * 网格颜色
     */
    private var gridLineColor = Color.parseColor("#1b4200")

    /**
     * 小网格的宽高
     */
    private var SMALL_GRID_WIDTH = 10

    /**
     * 网格的宽高
     */
    private var GRID_WIDTH = 30

    /**
     * 網隔名稱
     */
    private var gridLabelName = ""

    /**
     * 網隔名稱文字大小
     */
    private var gridLabelNameSp = 6F

    private var mDensity = 0f

    /**
     * 繪圖波形秒數
     */
    private var waveLineSecond = 10F

    private var baselineValue = 0F
    private var baselineCount = 0

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        mWidth = w.toFloat()
//        mHeight = h.toFloat()
//        super.onSizeChanged(w, h, oldw, oldh)
//    }

    private fun init(attrs: AttributeSet?) {
        MAX_VALUE = attrs!!.getAttributeIntValue(NAMESPACE, "max_value", 20).toFloat()
//        WAVE_LINE_WIDTH = attrs.getAttributeIntValue(NAMESPACE, "wave_line_width", 10)
        WAVE_LINE_STROKE_WIDTH =
            attrs.getAttributeIntValue(NAMESPACE, "wave_line_stroke_width", 1).toFloat()
        gridVisible = attrs.getAttributeBooleanValue(NAMESPACE, "grid_visible", true)
        gridSmallVisible = attrs.getAttributeBooleanValue(NAMESPACE, "grid_small_visible", false)
        drawMode = attrs.getAttributeIntValue(NAMESPACE, "draw_mode", NORMAL_MODE)
        val wave_line_color = attrs.getAttributeValue(NAMESPACE, "wave_line_color")
        if (wave_line_color != null && !wave_line_color.isEmpty()) {
            waveLineColor = Color.parseColor(wave_line_color)
        }
        val grid_line_color = attrs.getAttributeValue(NAMESPACE, "grid_line_color")
        if (grid_line_color != null && !grid_line_color.isEmpty()) {
            gridLineColor = Color.parseColor(grid_line_color)
        }
        val wave_background = attrs.getAttributeValue(NAMESPACE, "wave_background")
        if (wave_background != null && !wave_background.isEmpty()) {
            setBackgroundColor(Color.parseColor(wave_background))
        }
        val grid_label_name = attrs.getAttributeValue(NAMESPACE, "grid_label_name")
        if (grid_label_name != null && !grid_label_name.isEmpty()) {
            gridLabelName = grid_label_name
        }
        gridLabelNameSp = attrs!!.getAttributeIntValue(NAMESPACE, "grid_label_name_sp", 6).toFloat()
        GRID_WIDTH = attrs.getAttributeIntValue(NAMESPACE, "grid_width", 30)
        waveLineSecond = attrs.getAttributeFloatValue(NAMESPACE, "wave_line_second", 10F)

        mLinePaint = Paint()
        mLinePaint!!.style = Paint.Style.STROKE
        mLinePaint!!.strokeWidth = GRID_LINE_WIDTH.toFloat()
        /** 抗锯齿效果 */
        mLinePaint!!.isAntiAlias = true

        mWavePaint = Paint()
        mWavePaint!!.style = Paint.Style.STROKE
        mWavePaint!!.color = waveLineColor
        mWavePaint!!.strokeWidth = WAVE_LINE_STROKE_WIDTH
        /** 抗锯齿效果 */
        mWavePaint!!.isAntiAlias = true

        mPath = Path()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        /** 获取控件的宽高 */
        mWidth = measuredWidth.toFloat()
        mHeight = measuredHeight.toFloat()
        /** 根据网格的单位长宽，获取能绘制网格横线和竖线的数量(大網格) */
        gridHorizontalNum = (mHeight / GRID_WIDTH).toInt()
        gridVerticalNum = (mWidth / GRID_WIDTH).toInt()

        /** 根据网格的单位长宽，获取能绘制网格横线和竖线的数量(小網格) */
        gridHorizontalSmallNum = (mHeight/SMALL_GRID_WIDTH).toInt()
        gridVerticalSmallNum = (mWidth/SMALL_GRID_WIDTH).toInt()
        /** 根据线条长度，最多能绘制多少个数据点 */
        row = (mWidth / WAVE_LINE_WIDTH).toInt()
//        row = (waveLineSecond * 500).toInt()
        Log.d(TAG,"row: " + row)
        dataArray = FloatArray(row*100)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        drawRowLabel(canvas, (GRID_WIDTH).toFloat(), (mHeight/2 + (GRID_WIDTH + GRID_WIDTH/2)), "1 m v")
//        drawRowLabel(canvas, (GRID_WIDTH).toFloat(), (mHeight/2 - (GRID_WIDTH + GRID_WIDTH/4)), gridLabelName)
//        /** 繪製初始條 */
//        drawStartPoint(canvas)
//        /** 繪製網格 */
//        if (gridVisible) {
//            drawGrid(canvas)
//        }
//        /** 繪製小網格 */
//        if(gridSmallVisible) {
//            drawSmallGrid(canvas)
//        }
        when (drawMode) {
            0 -> drawWaveLineNormal(canvas)
            1 -> drawWaveLineLoop(canvas)
        }
        draw_index += 1
        if (draw_index >= row) {
            draw_index = 0
        }
    }

    /**
     * 常规模式绘制折线
     *
     * @param canvas
     */
    private fun drawWaveLineNormal(canvas: Canvas) {
        drawPathFromData(canvas, 0, row - 1)
        for (i in 5 until row - 1) {
            dataArray[i] = dataArray[i + 1]
        }
    }

    /**
     * 循环模式绘制折线
     *
     * @param canvas
     */
    private fun drawWaveLineLoop(canvas: Canvas) {
        drawPathFromData(
            canvas,
            if (row - 1 - draw_index > 8) 10 else 8 - (row - 1 - draw_index),
            draw_index
        )
        drawPathFromData(canvas, Math.min(draw_index + 8, row - 1), row - 1)
    }

    /**
     * 取数组中的指定一段数据来绘制折线
     *
     * @param start 起始数据位
     * @param end   结束数据位
     */
    private fun drawPathFromData(canvas: Canvas, start: Int, end: Int) {
        if(draw_index != 0) {
            mPath!!.reset()
            //(WAVE_LINE_WIDTH*3) 初始位置必須從起始位置右一格開始畫起
            var baseLine = mHeight / 2
            startY = (baseLine - dataArray[start] * (mHeight / (MAX_VALUE * 2))) + (WAVE_LINE_WIDTH*3)
            mPath!!.moveTo((start * WAVE_LINE_WIDTH).toFloat(), startY)
            for (i in start + 1 until end + 1) {
                if (isRefresh) {
                    isRefresh = false
                    return
                }
                nowX = (i * WAVE_LINE_WIDTH).toFloat()
                var dataValue = dataArray[i]
                /** 判断数据为正数还是负数  超过最大值的数据按最大值来绘制 */
                if (dataValue > 0) {
                    if (dataValue > MAX_VALUE) {
                        dataValue = MAX_VALUE
                    }
                } else {
                    if (dataValue < -MAX_VALUE) {
                        dataValue = -MAX_VALUE
                    }
                }
                nowY = mHeight / 2 - dataValue * (mHeight / (MAX_VALUE * 2))
                mPath!!.lineTo(nowX, nowY)
            }
            canvas.drawPath(mPath!!, mWavePaint!!)
        }

    }

    /**
     * 繪製小網格
     *
     * @param canvas
     */
    private fun drawSmallGrid(canvas: Canvas) {
        /** 设置颜色 */
        mLinePaint!!.color = gridLineColor
        //畫橫線
        for (i in 0 until gridHorizontalSmallNum + 1) {
            canvas.drawLine(
                0f, (i * SMALL_GRID_WIDTH).toFloat(),
                mWidth, (i * SMALL_GRID_WIDTH).toFloat(), mLinePaint!!
            )

        }
        //畫豎線
        for (i in 0 until gridVerticalSmallNum + 1) {
            canvas.drawLine(
                (i * SMALL_GRID_WIDTH).toFloat(), 0f,
                (i * SMALL_GRID_WIDTH).toFloat(), mHeight, mLinePaint!!
            )
        }
    }

    /**
     * 繪製大網格
     *
     * @param canvas
     */
    private fun drawGrid(canvas: Canvas) {
        /** 设置颜色 */
        mLinePaint!!.color = gridLineColor
        /** 绘制横线 */
        for (i in 0 until gridHorizontalNum + 1) {
            canvas.drawLine(
                0f, (i * GRID_WIDTH).toFloat(),
                mWidth, (i * GRID_WIDTH).toFloat(), mLinePaint!!
            )
        }
        /** 绘制竖线 */
        for (i in 0 until gridVerticalNum + 1) {
            canvas.drawLine(
                (i * GRID_WIDTH).toFloat(), 0f, (
                        i * GRID_WIDTH).toFloat(), mHeight, mLinePaint!!
            )
        }
    }

    /**
     * 繪製初始條
     *
     * @param canvas
     */
    private fun drawStartPoint(canvas: Canvas) {
        /** 设置颜色 */
        mLinePaint!!.color = waveLineColor
        /** 绘制竖线 */
        for (i in GRID_WIDTH until (GRID_WIDTH + GRID_WIDTH/2)) {
            canvas.drawLine(
                i.toFloat(), (mHeight/2 + ((GRID_WIDTH/4) *3)), i.toFloat(), mHeight/2 + GRID_WIDTH, mLinePaint!!
            )
        }
        for (i in (GRID_WIDTH + GRID_WIDTH/2) until (GRID_WIDTH + (GRID_WIDTH/4 * 3))) {
            canvas.drawLine(
                i.toFloat(), mHeight/2 - GRID_WIDTH, i.toFloat(), mHeight/2 + GRID_WIDTH, mLinePaint!!
            )
        }
        for (i in (GRID_WIDTH + (GRID_WIDTH/4 * 3)) until (GRID_WIDTH*2 + GRID_WIDTH/4)) {
            canvas.drawLine(
                i.toFloat(), mHeight/2 - GRID_WIDTH, i.toFloat(), mHeight/2 - (GRID_WIDTH/4)*3, mLinePaint!!
            )
        }
        for (i in (GRID_WIDTH*2 + GRID_WIDTH/4) until (GRID_WIDTH*2 + GRID_WIDTH/2)) {
            canvas.drawLine(
                i.toFloat(), mHeight/2 - GRID_WIDTH, i.toFloat(), mHeight/2 + GRID_WIDTH, mLinePaint!!
            )
        }
        for (i in (GRID_WIDTH*2 + GRID_WIDTH/2) until GRID_WIDTH*3) {
            canvas.drawLine(
                i.toFloat(), (mHeight/2 + ((GRID_WIDTH/4) *3)), i.toFloat(), mHeight/2 + GRID_WIDTH, mLinePaint!!
            )
        }
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private fun drawRowLabel(canvas: Canvas, left: Float, bottom: Float, text: String) {
        mLinePaint!!.color = waveLineColor

        val padding: Int = ChartUtils.dp2px(mDensity, gridLabelNameSp)
        val fontMetrics: Paint.FontMetricsInt = mLinePaint!!.getFontMetricsInt()
        val startX = left + padding
        val rectBottom = bottom - padding
        val rectTop: Float = rectBottom - ChartUtils.getTextHeight(mLinePaint!!, text)
        val baseline = (rectBottom + rectTop - fontMetrics.bottom - fontMetrics.top) / 2f
        canvas.drawText(text, startX, baseline, mLinePaint!!)
    }

    /**
     * 添加新的数据
     */
    fun showLine(line: Float, index: Int) {
        when (drawMode) {
            0 ->
                /** 常规模式数据添加至最后一位 */
                dataArray[row - 1] = line
            1 ->
                /** 循环模式数据添加至当前绘制的位 */
                dataArray[index] = line
        }
        postInvalidate()
    }

    /**
     * 添加新的数据
     */
    fun showLine(line: Float) {
        when (drawMode) {
            0 ->
                /** 常规模式数据添加至最后一位 */
                dataArray[row - 1] = line
            1 ->
                /** 循环模式数据添加至当前绘制的位 */
                dataArray[draw_index] = line
        }
//        baselineValue += line
//        baselineCount++
        postInvalidate()
    }

    fun reset() {
        for (i in dataArray.indices) {
            dataArray[i] = 0F
        }
        draw_index = 0
        postInvalidate()
    }

    fun setMaxValue(max_value: Int): StaticWaveEcgView {
        MAX_VALUE = max_value.toFloat()
        return this
    }

//    fun setWaveLineWidth(width: Int): WaveEcgView {
//        draw_index = 0
//        WAVE_LINE_WIDTH = width
//        row = (mWidth / WAVE_LINE_WIDTH).toInt()
//        isRefresh = true
//        dataArray = FloatArray(row)
//        return this
//    }
//
//    fun setWaveLineStrokeWidth(width: Int): WaveEcgView {
//        WAVE_LINE_WIDTH = width
//        return this
//    }

    fun setWaveLineColor(colorString: String?): StaticWaveEcgView {
        waveLineColor = Color.parseColor(colorString)
        return this
    }

    fun setGridVisible(visible: Boolean): StaticWaveEcgView {
        gridVisible = visible
        return this
    }

    fun setGridLineColor(colorString: String?): StaticWaveEcgView {
        gridLineColor = Color.parseColor(colorString)
        return this
    }

    fun setWaveBackground(colorString: String?): StaticWaveEcgView {
        setBackgroundColor(Color.parseColor(colorString))
        return this
    }

    fun setWaveDrawMode(draw_mode: Int): StaticWaveEcgView {
        drawMode = draw_mode
        return this
    }

    companion object {
        /**
         * 常规绘制模式 不断往后推的方式
         */
        var NORMAL_MODE = 0

        /**
         * 循环绘制模式
         */
        var LOOP_MODE = 1
    }
}