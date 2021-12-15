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
class EcgSoftRenderer private constructor(context: Context, values: Array<ECGPointValue>, softStrategy: SoftStrategy?, dataRenderer: RealRenderer?, axesArenderer: RealRenderer?)  {
    private val TAG = EcgSoftRenderer::class.java.simpleName

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
            softStrategy: SoftStrategy? = null,
            dataRenderer: RealRenderer? = null,
            axesArenderer: RealRenderer? = null
        ): EcgSoftRenderer {
            return EcgSoftRenderer(context, values, softStrategy, dataRenderer, axesArenderer)
        }
    }

    init {
        mSoftStrategy = softStrategy ?: LuckySoftStrategy(values.size)
        mDataRenerer = dataRenderer ?: SoftDataRenderer(context, values)
        mAxesRenderer = axesArenderer ?: SoftAxesRenderer(context, values)
        mDataRenerer.setSoftStrategy(mSoftStrategy)
        mAxesRenderer.setSoftStrategy(mSoftStrategy)
    }

    private fun initSoft() {
        softwareCanvas = Canvas()
        softwareBitmap = Bitmap.createBitmap(
            mSoftStrategy.pictureWidth(),
            mSoftStrategy.pictureHeight(), Bitmap.Config.ARGB_8888
        )
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
        //        mDataRenerer.draw(softwareCanvas);
        return softwareBitmap
    }

}