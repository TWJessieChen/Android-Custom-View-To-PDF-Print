package com.jc666.ecglibrary.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.jc666.ecglibrary.ECGReportDataFormat
import com.jc666.ecglibrary.R
import com.jc666.ecglibrary.renderer.StaticECGDataRenderer

/**
 * @author JC666
 * @date 2022/03/08
 *
 * 在HorizontalScrollView中如果要嵌套CustomView，
 * 要在CustomView的onMeasure方法设置CustomView的宽高，
 * 不然CustomView不能显示出来。
 *
 */
class StaticECGDataView : View {
    private val TAG = StaticECGDataView::class.java.simpleName

    private var mEcgDataList: List<ECGReportDataFormat>? = null

    private var mStaticECGDataRenderer: StaticECGDataRenderer? = null

    private var BACKGROUND_DRAW_MODE = 0

    private var gain = 0f

    private var leadIndex: Int = 0

    private var drawType:Int = 0

    private var ecgDataBitmap: Bitmap? = null

    private var ecgDataCanvas: Canvas? = null

    private var mRenderPaint: Paint? = null

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(5000, height)
        ecgDataBitmap = Bitmap.createBitmap(
            5000,
            measuredHeight, Bitmap.Config.ARGB_8888
        )
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.StaticEcgView)
        }
        ecgDataCanvas = Canvas()
    }

//    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        super.onLayout(changed, left, top, right, bottom)
//
//    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(mEcgDataList!!.size > 0) {
            this.mStaticECGDataRenderer = StaticECGDataRenderer(context,
                measuredHeight,
                mEcgDataList!!,
                leadIndex,
                drawType,
                gain)

            ecgDataBitmap!!.eraseColor(Color.TRANSPARENT)
            ecgDataCanvas!!.setBitmap(ecgDataBitmap)
            ecgDataCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

            mRenderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mRenderPaint!!.setStyle(Paint.Style.FILL)
            mStaticECGDataRenderer!!.draw(ecgDataCanvas!!)

            canvas.drawBitmap(ecgDataBitmap!!, 0f, 0f, mRenderPaint)
        }
    }

    override fun onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow")
        // releases the bitmap in the renderer to avoid oom error
        if (mStaticECGDataRenderer != null && mStaticECGDataRenderer is StaticECGDataRenderer) {
            if (ecgDataCanvas != null) {
                ecgDataCanvas!!.setBitmap(null)
                ecgDataCanvas = null
            }
            if (ecgDataBitmap != null) {
                if (ecgDataBitmap != null) {
                    ecgDataBitmap!!.recycle()
                }
            }
        }
        super.onDetachedFromWindow()
    }

    /**
     * 設定心電圖資料
     *
     * @param leadData 心電圖資料
     */
    fun setLeadData(_drawType:Int, ecgDataList: List<ECGReportDataFormat>, _gain: Float, _leadIndex: Int) {
        when (_gain) {
            0.5F -> {
                this.gain = 2f
            }
            1F -> {
                this.gain = 1f
            }
            2F -> {
                this.gain = 0.5f
            }
            4F -> {
                this.gain = 0.25f
            }
        }
        if(ecgDataList.size > 0) {
            this.mEcgDataList = ecgDataList
        }
        this.leadIndex = _leadIndex
        this.drawType = _drawType

        invalidate()
    }
}