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
class ECGReportSoftRenderer private constructor(context: Context, values: Array<ECGPointValue>, type: Int, softStrategy: SoftStrategy?, dataRenderer: RealRenderer?, axesArenderer: RealRenderer?)  {
    private val TAG = ECGReportSoftRenderer::class.java.simpleName

    private val mSoftStrategy: SoftStrategy

    private val mDataRenerer: RealRenderer

    private val mAxesRenderer: RealRenderer

    private var softwareBitmap: Bitmap? = null

    private var softwareCanvas: Canvas? = null

    companion object {
        @JvmOverloads
        fun instantiate(
            context: Context,
            values: Array<ECGPointValue>,
            type:Int,
            softStrategy: SoftStrategy? = null,
            dataRenderer: RealRenderer? = null,
            axesArenderer: RealRenderer? = null
        ): ECGReportSoftRenderer {
            return ECGReportSoftRenderer(context, values, type, softStrategy, dataRenderer, axesArenderer)
        }
    }

    init {
        mSoftStrategy = softStrategy ?: LuckySoftStrategy(values.size)
        mDataRenerer = dataRenderer ?: SoftDataRenderer(context, values)
        mAxesRenderer = axesArenderer ?: SoftAxesRenderer(context, values, type)
        mDataRenerer.setSoftStrategy(mSoftStrategy)
        mAxesRenderer.setSoftStrategy(mSoftStrategy)
    }

    private fun initSoft() {
        softwareCanvas = Canvas()

        //因為要讓在不同尺寸的Lead上呈現一樣的大小，所以底圖高度設定一樣大小
        softwareBitmap = Bitmap.createBitmap(
            mSoftStrategy.pictureWidth(),
            3600, Bitmap.Config.ARGB_8888
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
        softwareCanvas!!.drawColor(Color.WHITE)
        mAxesRenderer.draw(softwareCanvas)
        mDataRenerer.draw(softwareCanvas);
        return softwareBitmap
    }

}