package com.jc666.customviewtopdfprint

import android.content.res.Resources
import android.graphics.*
import kotlin.math.roundToInt
import android.graphics.BitmapFactory

import android.graphics.Bitmap





//class BarChart : View {
class BarChart {

//    constructor(context: Context?) : super(context)
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
//
//    private val textBound = Rect()
//    private val inputText = "JC666"
//    private val textPaint = Paint().apply {
//        color = Color.BLACK
//        textSize = 40.toPx().toFloat()
////        textAlign = Paint.Align.CENTER
//    }
//    private val lineTextPaint = Paint().apply {
//        color = Color.BLACK
//        textSize = 12.toPx().toFloat()
//        textAlign = Paint.Align.RIGHT
//    }
//    private val boundPaint = Paint().apply {
//        color = Color.YELLOW
//        style = Paint.Style.FILL
//    }
//    private val centerPaint = Paint().apply {
//        color = Color.RED
//    }
//    private val linePaint = Paint().apply {
//        color = Color.MAGENTA
//    }
//    private val fontMetrics = textPaint.fontMetrics
//
//    override fun onDraw(canvas: Canvas) {
//        val centerX = width / 2
//        val centerY = height / 2
//        textPaint.getTextBounds(inputText, 0, inputText.length, textBound)
//
//        canvas.save()
//        canvas.translate(centerX.toFloat(), centerY.toFloat())
//        canvas.drawRect(textBound, boundPaint)
//        drawLine(canvas, "bottom", 0f, fontMetrics.bottom, Color.DKGRAY)
//        drawLine(canvas, "ascent", -(30.toPx().toFloat()), fontMetrics.ascent, Color.DKGRAY)
//        drawLine(canvas, "top", -(60.toPx().toFloat()), fontMetrics.top, Color.MAGENTA)
//        drawLine(canvas, "descent", -(60.toPx().toFloat()), fontMetrics.descent, Color.MAGENTA)
//        drawLine(canvas, "leading", -(120.toPx().toFloat()), fontMetrics.bottom - fontMetrics.leading, Color.LTGRAY)
//        canvas.drawText(inputText, 0f, 0f, textPaint)
//        canvas.drawCircle(0f, 0f, 3.toPx().toFloat(), centerPaint)
//        canvas.restore()
//    }
//
//    private fun drawLine(canvas: Canvas, text: String, x: Float, y: Float, color: Int) {
//        linePaint.color = color
//        lineTextPaint.color = color
//        val lineLeft = -(200.toPx().toFloat())
//        val lineRight = (200.toPx().toFloat())
//        canvas.drawText(text, x, y, lineTextPaint)
//        canvas.drawLine(lineLeft, y, lineRight, y, linePaint)
//    }

//    constructor(context: Context?) : super(context)
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


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

    private val barPaint = Paint().apply {
        color = Color.BLUE
    }

    private val linePaint = Paint().apply {
        strokeWidth = 8.toPx().toFloat()
        color = Color.GRAY
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 14.toPx().toFloat()
        color = Color.GRAY
        textAlign = Paint.Align.LEFT
    }

    private val iconPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isFilterBitmap = true
        isDither = true
    }

    private val valueTextPaint = Paint().apply {
        color = Color.BLACK
        textSize = 8.toPx().toFloat()
        textAlign = Paint.Align.RIGHT
    }

    private val startDistanceWidth = 10.toPx()
    private val startDistanceHeight = 15.toPx()
    private val barWidth = 30.toPx()
    private val barDistance = 30.toPx()
    private val maxValue = 200

    private var firstNameValue = "JC"
    private var lastNameValue = "666"
    private var patientNumberTitleValue = "病歷號碼"
    private var patientNumberValue = "GHGFVJ654D563FG7"
    private var patientAgeTitleValue = "年齡:"
    private var patientAgeValue = "35"
    private var patientBirthdayTitleValue = "生日"
    private var patientBirthdayValue = "1986.04.22"

    fun onDraw(canvas: Canvas) : Bitmap{
        canvas.setBitmap(softwareBitmap!!)

        drawPatientFirstAndLastNameValue(canvas)
        drawPatientIDNumberValue(canvas)
        drawPatientGenderAndAgeValue(canvas)
//        drawBar(canvas)
//        drawAxis(canvas)
//        drawValue(canvas)
//        drawLabels(canvas)
        return softwareBitmap!!
    }

    private fun drawPatientFirstAndLastNameValue(canvas: Canvas) {
        val x = getTextValueWidth() + startDistanceWidth
        val fontMetrics = textPaint.fontMetrics
        // 要先移動 (top) 這段距離才不會遮住字
        val y = - fontMetrics.top + startDistanceHeight
        // 只畫 firstNameValue
        canvas.drawText(firstNameValue, x, y, textPaint)
        // 只畫 lastNameValue
        canvas.drawText(lastNameValue, x + (barDistance*2), y, textPaint)
    }

    private fun drawPatientIDNumberValue(canvas: Canvas) {
        val x = getTextValueWidth() + startDistanceWidth
        val fontMetrics = textPaint.fontMetrics
        // 要先移動 (top) 這段距離才不會遮住字
        val y = - fontMetrics.top + (startDistanceHeight*2)
        // 只畫 patientNumberTitleValue
        canvas.drawText(patientNumberTitleValue, x, y, textPaint)
        // 只畫 patientNumberValue
        canvas.drawText(patientNumberValue, x + (barDistance*2), y, textPaint)
    }

    private fun drawPatientGenderAndAgeValue(canvas: Canvas) {
        val x = getTextValueWidth() + startDistanceWidth
        val fontMetrics = textPaint.fontMetrics
        // 要先移動 (top) 這段距離才不會遮住字
        val y = - fontMetrics.top + (startDistanceHeight*3)
        // 只畫 patientGenderValue
        val bitmap = BitmapFactory.decodeResource(MainApplication.appContext!!.resources, R.mipmap.female)
        canvas.drawBitmap(bitmap, x, y, iconPaint)
        // 只畫 patientAgeTitleValue
        canvas.drawText(patientAgeTitleValue, x + 10.toPx(), y + 6.toPx(), textPaint)
        // 只畫 patientAgeValue
        canvas.drawText(patientAgeValue, x + 30.toPx(), y + 6.toPx(), textPaint)
        // 只畫 patientBirthdayTitleValue
        canvas.drawText(patientBirthdayTitleValue, x + (barDistance*2), y + 6.toPx(), textPaint)
        // 只畫 patientBirthdayValue
        canvas.drawText(patientBirthdayValue, x + (barDistance*2) + 20.toPx(), y + 6.toPx(), textPaint)
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
            canvas.drawRect(left, top, right, bottom, barPaint)
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
}