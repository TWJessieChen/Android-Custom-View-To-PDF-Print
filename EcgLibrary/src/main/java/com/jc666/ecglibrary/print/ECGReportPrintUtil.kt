package com.jc666.ecglibrary.print

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import com.jc666.ecglibrary.SoftStrategy

/**
 * @author JC666
 * @date 2021/03/10
 * ECGReportPrintUtil
 */
class ECGReportPrintUtil private constructor(context: Context,
                                             values: List<ECGReportPrintDataFormat>,
                                             type: Int,
                                             softStrategy: SoftStrategy?,
                                             dataRenderer: BriteMEDPrintRenderer?,
                                             axesArenderer: BriteMEDPrintRenderer?)  {
    private val TAG = ECGReportPrintUtil::class.java.simpleName

    private val mSoftStrategy: SoftStrategy

    private val mDataRenderer: BriteMEDPrintRenderer

    private val mAxesRenderer: BriteMEDPrintRenderer

    private var softwareBitmap: Bitmap? = null

    private var softwareCanvas: Canvas? = null

    companion object {
        @JvmOverloads
        fun instantiate(
            context: Context,
            values: List<ECGReportPrintDataFormat>,
            type:Int,
            softStrategy: SoftStrategy? = null,
            dataRenderer: BriteMEDPrintRenderer? = null,
            axesArenderer: BriteMEDPrintRenderer? = null
        ): ECGReportPrintUtil {
            return ECGReportPrintUtil(context, values, type, softStrategy, dataRenderer, axesArenderer)
        }
    }

    init {
        mSoftStrategy = softStrategy ?: PrintStrategy(values.size)
        mDataRenderer = dataRenderer ?: PrintDataRenderer(context, values)
        mAxesRenderer = axesArenderer ?: PrintAxesRenderer(context, values, type)
        mDataRenderer.setSoftStrategy(mSoftStrategy)
        mAxesRenderer.setSoftStrategy(mSoftStrategy)
    }

    private fun initSoft() {
        softwareCanvas = Canvas()

        //因為要讓在不同尺寸的Lead上呈現一樣的大小，所以底圖高度設定一樣大小
        softwareBitmap = Bitmap.createBitmap(
            mSoftStrategy.pictureWidth(),
            3600, Bitmap.Config.ARGB_8888
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
        mDataRenderer.draw(softwareCanvas);
        return softwareBitmap
    }

}