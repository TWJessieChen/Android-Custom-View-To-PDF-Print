package com.jc666.ecglibrary

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log

/**
 * @author JC666
 * @date 2021/12/15
 * @describe 主要是繪製ECG圖，分成背景圖與資料圖，兩個不同的圖層
 */
class ECGReportViewRenderer private constructor(context: Context,
                                                values: Array<ECGPointValue>,
                                                colorType: Int,
                                                leadName: String,
                                                gain: Int,
                                                softStrategy: SoftStrategy?,
                                                ecgDataRenderer: RealRenderer?,
                                                ecgBackgroundRenderer: RealRenderer?)  {
    private val TAG = ECGReportViewRenderer::class.java.simpleName

    //紀錄畫筆顏色Type，0:白色(黑色波形) 1:黑色(綠色波形)
    private val BACKGROUND_BLACK_COLOR = Color.parseColor("#ff231815") //黑色
    private val BACKGROUND_WHITE_COLOR = Color.parseColor("#ffffffff") //白色

    private val mSoftStrategy: SoftStrategy

    private val mECGDataRenderer: RealRenderer

    private val mECGBackgroundRenderer: RealRenderer

    private var ecgDataBitmap: Bitmap? = null

    private var ecgBackgroundBitmap: Bitmap? = null

    private var ecgDataCanvas: Canvas? = null

    private var ecgBackgroundCanvas: Canvas? = null

    private var backgroundType: Int = 0

    private var leadName: String = ""

    companion object {
        @JvmOverloads
        fun instantiate(
            context: Context,
            values: Array<ECGPointValue>,
            colorType:Int,
            leadName: String,
            gain: Int,
            softStrategy: SoftStrategy? = null,
            ecgDataRenderer: RealRenderer? = null,
            ecgBackgroundRenderer: RealRenderer? = null
        ): ECGReportViewRenderer {
            return ECGReportViewRenderer(context, values, colorType, leadName, gain, softStrategy, ecgDataRenderer, ecgBackgroundRenderer)
        }
    }

    init {
        this.mSoftStrategy = softStrategy ?: LuckySoftStrategy(values.size)
        this.mECGDataRenderer = ecgDataRenderer ?: ECGDataRenderer(context, values, colorType, gain)
        this.mECGBackgroundRenderer = ecgBackgroundRenderer  ?: ECGBackgroundRenderer(context, values, colorType, leadName, gain)
        this.mECGDataRenderer.setSoftStrategy(mSoftStrategy)
        this.mECGBackgroundRenderer.setSoftStrategy(mSoftStrategy)
        this.backgroundType = colorType
        this.leadName = leadName
    }

    private fun initSoft() {
        ecgDataCanvas = Canvas()
        ecgBackgroundCanvas = Canvas()

        Log.d(TAG, "Width: " + mSoftStrategy.pictureWidth())

        //因為要讓在不同尺寸的Lead上呈現一樣的大小，所以底圖高度設定一樣大小
        //原先是動態設定width & height 大小
        ecgDataBitmap = Bitmap.createBitmap(
            mSoftStrategy.pictureWidth(),
            mSoftStrategy.pictureHeight(), Bitmap.Config.ARGB_8888
        )
        ecgBackgroundBitmap = Bitmap.createBitmap(
            mSoftStrategy.pictureWidth(),
            mSoftStrategy.pictureHeight(), Bitmap.Config.ARGB_8888
        )

        ecgDataCanvas!!.setBitmap(ecgDataBitmap)
        ecgDataCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY)

        ecgBackgroundCanvas!!.setBitmap(ecgBackgroundBitmap)
        ecgBackgroundCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
    }

    /**
     * 開始繪圖
     */
    fun startRender(): Triple<String, Bitmap, Bitmap> {
        initSoft()

        if(backgroundType == 0) {
            ecgBackgroundCanvas!!.drawColor(BACKGROUND_WHITE_COLOR)
        } else {
            ecgBackgroundCanvas!!.drawColor(BACKGROUND_BLACK_COLOR)
        }
        mECGBackgroundRenderer.draw(ecgBackgroundCanvas)

        mECGDataRenderer.draw(ecgDataCanvas)

        return Triple(leadName, ecgBackgroundBitmap!!, ecgDataBitmap!!)
    }

}