package com.jc666.ecglibrary.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.jc666.ecglibrary.LuckySoftStrategy
import com.jc666.ecglibrary.R
import com.jc666.ecglibrary.SoftStrategy
import com.jc666.ecglibrary.Renderer.StaticECGBackgroundRenderer
import com.jc666.ecglibrary.Renderer.StaticECGSoftStrategy

class StaticEcgView : View {
    private val TAG = StaticEcgView::class.java.simpleName

    private val NAMESPACE = "http://schemas.android.com/apk/res-auto"

    //紀錄畫筆顏色Type，0:白色(黑色波形) 1:黑色(綠色波形)
    private val BACKGROUND_BLACK_COLOR = Color.parseColor("#ff231815") //黑色
    private val BACKGROUND_WHITE_COLOR = Color.parseColor("#ffffffff") //白色

    private var mSoftStrategy: SoftStrategy? = null

    private var mStaticECGBackgroundRenderer: StaticECGBackgroundRenderer? = null

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
        this.mSoftStrategy = StaticECGSoftStrategy(5000, width, height)
        this.mStaticECGBackgroundRenderer = StaticECGBackgroundRenderer(context,
            mSoftStrategy as StaticECGSoftStrategy,
            0,
            LEAD_NMAE,
            GAIN)
    }

//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        mWidth = w.toFloat()
//        mHeight = h.toFloat()
//        super.onSizeChanged(w, h, oldw, oldh)
//    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.StaticEcgView)
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
            (mSoftStrategy as StaticECGSoftStrategy).pictureWidth(),
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
        mStaticECGBackgroundRenderer!!.draw(ecgBackgroundCanvas)

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