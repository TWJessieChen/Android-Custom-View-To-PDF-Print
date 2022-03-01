package com.jc666.ecglibrary

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff

/**
 * @author JC666
 * @date 2021/12/15
 * @describe TODO
 */
class ECGReportViewSoftRenderer private constructor(context: Context,
                                                    values: Array<ECGPointValue>,
                                                    colorType: Int,
                                                    leadName: String,
                                                    gain: Int,
                                                    softStrategy: SoftStrategy?,
                                                    dataRenderer: RealRenderer?,
                                                    axesArenderer: RealRenderer?)  {
    private val TAG = ECGReportViewSoftRenderer::class.java.simpleName

    //紀錄畫筆顏色Type，0:白色(黑色波形) 1:黑色(綠色波形)
    private val BACKGROUND_BLACK_COLOR = Color.parseColor("#ff231815") //黑色
    private val BACKGROUND_WHITE_COLOR = Color.parseColor("#ffffffff") //白色

    private val mSoftStrategy: SoftStrategy

    private val mDataRenerer: RealRenderer

    private val mAxesRenderer: RealRenderer

    private var softwareBitmap: Bitmap? = null

    private var softwareCanvas: Canvas? = null

    private var backgroundType: Int = 0

    companion object {
        @JvmOverloads
        fun instantiate(
            context: Context,
            values: Array<ECGPointValue>,
            colorType:Int,
            leadName: String,
            gain: Int,
            softStrategy: SoftStrategy? = null,
            dataRenderer: RealRenderer? = null,
            axesRenderer: RealRenderer? = null
        ): ECGReportViewSoftRenderer {
            return ECGReportViewSoftRenderer(context, values, colorType, leadName, gain, softStrategy, dataRenderer, axesRenderer)
        }
    }

    init {
        mSoftStrategy = softStrategy ?: LuckySoftStrategy(values.size)
        mDataRenerer = dataRenderer ?: SoftViewDataRenderer(context, values, colorType, gain)
        mAxesRenderer = axesArenderer ?: SoftViewAxesRenderer(context, values, colorType, leadName, gain)
        mDataRenerer.setSoftStrategy(mSoftStrategy)
        mAxesRenderer.setSoftStrategy(mSoftStrategy)
        backgroundType = colorType
    }

    private fun initSoft() {
        softwareCanvas = Canvas()

        //因為要讓在不同尺寸的Lead上呈現一樣的大小，所以底圖高度設定一樣大小
        softwareBitmap = Bitmap.createBitmap(
            mSoftStrategy.pictureWidth(),
            mSoftStrategy.pictureHeight(), Bitmap.Config.ARGB_8888
        )
        //原先是動態設定width & height 大小
//        softwareBitmap = Bitmap.createBitmap(
//            mSoftStrategy.pictureWidth(),
//            mSoftStrategy.pictureHeight(), Bitmap.Config.ARGB_8888
//        )
        softwareCanvas!!.setBitmap(softwareBitmap)
        softwareCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
    }

    /**
     * 開始繪圖
     */
    fun startRender(): Bitmap? {
        initSoft()
        if(backgroundType == 0) {
            softwareCanvas!!.drawColor(BACKGROUND_WHITE_COLOR)
        } else {
            softwareCanvas!!.drawColor(BACKGROUND_BLACK_COLOR)
        }
        mAxesRenderer.draw(softwareCanvas)
        mDataRenerer.draw(softwareCanvas);
        return softwareBitmap
    }

}