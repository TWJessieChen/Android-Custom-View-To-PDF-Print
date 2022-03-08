package com.jc666.ecglibrary.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.jc666.ecglibrary.R
import com.jc666.ecglibrary.Renderer.StaticECGBackgroundRenderer

class StaticECGBackgroundView : View {
    private val TAG = StaticECGBackgroundView::class.java.simpleName

    //紀錄畫筆顏色Type，0:白色(黑色波形) 1:黑色(綠色波形)
    private val BACKGROUND_BLACK_COLOR = Color.parseColor("#ff231815") //黑色
    private val BACKGROUND_WHITE_COLOR = Color.parseColor("#ffffffff") //白色

    private var mStaticECGBackgroundRenderer: StaticECGBackgroundRenderer? = null

    private var BACKGROUND_TEXT_PADDING_STATUS = 0 //預設不需要字串位移

    private var BACKGROUND_TEXT_SP = 2 //預設8sp字體大小

    private var BACKGROUND_SMALL_GRID_STATUS = 1 //預設開啟小網格

    private var BACKGROUND_DRAW_MODE = 0

    private var GAIN = 0

    private var LEAD_NMAE = ""

    private var ecgBackgroundBitmap: Bitmap? = null

    private var ecgBackgroundCanvas: Canvas? = null

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
        setMeasuredDimension(width, height)
        this.mStaticECGBackgroundRenderer = StaticECGBackgroundRenderer(context,
            width,
            height,
            BACKGROUND_DRAW_MODE,
            LEAD_NMAE,
            GAIN,
            BACKGROUND_SMALL_GRID_STATUS,
            BACKGROUND_TEXT_SP,
            BACKGROUND_TEXT_PADDING_STATUS)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.StaticEcgView)
            if (a.hasValue(R.styleable.StaticEcgView_background_text_padding)) {
                BACKGROUND_TEXT_PADDING_STATUS = a.getInt(R.styleable.StaticEcgView_background_text_padding, 0)
            }
            if (a.hasValue(R.styleable.StaticEcgView_background_text_sp)) {
                BACKGROUND_TEXT_SP = a.getInt(R.styleable.StaticEcgView_background_text_sp, 2)
            }
            if (a.hasValue(R.styleable.StaticEcgView_background_small_grid_enable)) {
                BACKGROUND_SMALL_GRID_STATUS = a.getInt(R.styleable.StaticEcgView_background_small_grid_enable, 0)
            }
            if (a.hasValue(R.styleable.StaticEcgView_background_draw_mode)) {
                BACKGROUND_DRAW_MODE = a.getInt(R.styleable.StaticEcgView_background_draw_mode, 0)
            }
            if (a.hasValue(R.styleable.StaticEcgView_gain)) {
                GAIN = a.getInt(R.styleable.StaticEcgView_gain, 0)
            }
            if (a.hasValue(R.styleable.StaticEcgView_lead_name)) {
                LEAD_NMAE = a.getString(R.styleable.StaticEcgView_lead_name).toString()
            }
        }
        ecgBackgroundCanvas = Canvas()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        ecgBackgroundBitmap = Bitmap.createBitmap(
            measuredWidth,
            measuredHeight, Bitmap.Config.ARGB_8888
        )

        ecgBackgroundBitmap!!.eraseColor(Color.TRANSPARENT)

        ecgBackgroundCanvas!!.setBitmap(ecgBackgroundBitmap)
        ecgBackgroundCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        if(BACKGROUND_DRAW_MODE == 0) {
            ecgBackgroundCanvas!!.drawColor(BACKGROUND_WHITE_COLOR)
        } else {
            ecgBackgroundCanvas!!.drawColor(BACKGROUND_BLACK_COLOR)
        }

        mRenderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mRenderPaint!!.setStyle(Paint.Style.FILL)
        mStaticECGBackgroundRenderer!!.draw(ecgBackgroundCanvas!!)

        canvas.drawBitmap(ecgBackgroundBitmap!!, 0f, 0f, mRenderPaint)
    }

    override fun onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow")
        // releases the bitmap in the renderer to avoid oom error
        if (mStaticECGBackgroundRenderer != null && mStaticECGBackgroundRenderer is StaticECGBackgroundRenderer) {
            if (ecgBackgroundCanvas != null) {
                ecgBackgroundCanvas!!.setBitmap(null)
                ecgBackgroundCanvas = null
            }
            if (ecgBackgroundBitmap != null) {
                if (ecgBackgroundBitmap != null) {
                    ecgBackgroundBitmap!!.recycle()
                }
            }
        }
        super.onDetachedFromWindow()
    }
}