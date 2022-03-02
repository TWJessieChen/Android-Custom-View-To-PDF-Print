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
 * @describe 主要是繪製ECG圖api class func，分成背景圖與資料圖，兩個不同的圖層
 *
 * List<ECGReportDataFormat> : 傳入的object，包含ECG data list(12 lead 原始資料), isPacemaker 值, leadOff(12 lead 原始資料)
 * colorType : 電子報告顏色模式(Type，0:背景白色(黑色波形) 1:背景黑色(綠色波形))
 * leadName : 需要繪製哪個Lead的名稱(ex: I,II,III,aVR,aVL,aVF,V1,V2,V3,V4,V5,V6)
 * leadIndex : 需要繪製哪個Lead的Index(ex: 0 ~ 11(ECGReportDataFormat(ecg: List<Int>資料)) 對應著 -> I,II,III,aVR,aVL,aVF,V1,V2,V3,V4,V5,V6)
 * gain : 需要繪製Lead gain 值, 0.5 = 2mv = 0(2f), 1 = 1mv = 1(1f), 2 = 0.5mv = 2(0.5f), 4 = 0.25mv = 3(0.25f)
 *
 *
 */

class ECGReportViewRenderer private constructor(context: Context,
                                                valuesObject: List<ECGReportDataFormat>,
                                                colorType: Int,
                                                leadName: String,
                                                leadIndex: Int,
                                                gain: Int,
                                                softStrategy: SoftStrategy?,
                                                ecgDataRenderer: BriteMEDRealRenderer?,
                                                ecgBackgroundRenderer: BriteMEDRealRenderer?)  {
    private val TAG = ECGReportViewRenderer::class.java.simpleName

    //紀錄畫筆顏色Type，0:白色(黑色波形) 1:黑色(綠色波形)
    private val BACKGROUND_BLACK_COLOR = Color.parseColor("#ff231815") //黑色
    private val BACKGROUND_WHITE_COLOR = Color.parseColor("#ffffffff") //白色

    private val mSoftStrategy: SoftStrategy

    private val mECGDataRenderer: BriteMEDRealRenderer

    private val mECGBackgroundRenderer: BriteMEDRealRenderer

    private var ecgDataBitmap: Bitmap? = null

    private var ecgBackgroundBitmap: Bitmap? = null

    private var ecgDataCanvas: Canvas? = null

    private var ecgBackgroundCanvas: Canvas? = null

    private var backgroundType: Int = 0

    private var leadName: String = ""

    private var leadIndex: Int = 0

    private var gain: Int = 1

    companion object {
        @JvmOverloads
        fun instantiate(
            context: Context,
            valuesObject: List<ECGReportDataFormat>,
            colorType:Int,
            leadName: String,
            leadIndex: Int,
            gain: Int,
            softStrategy: SoftStrategy? = null,
            ecgDataRenderer: BriteMEDRealRenderer? = null,
            ecgBackgroundRenderer: BriteMEDRealRenderer? = null
        ): ECGReportViewRenderer {
            return ECGReportViewRenderer(context, valuesObject, colorType, leadName, leadIndex, gain, softStrategy, ecgDataRenderer, ecgBackgroundRenderer)
        }
    }

    init {
        this.mSoftStrategy = softStrategy ?: LuckySoftStrategy(valuesObject.size)
        this.mECGDataRenderer = ecgDataRenderer ?: ECGDataRenderer(context, valuesObject, leadIndex, colorType, gain)
        this.mECGBackgroundRenderer = ecgBackgroundRenderer  ?: ECGBackgroundRenderer(context, valuesObject, colorType, leadName, gain)
        this.mECGDataRenderer.setSoftStrategy(mSoftStrategy)
        this.mECGBackgroundRenderer.setSoftStrategy(mSoftStrategy)
        this.backgroundType = colorType
        this.leadName = leadName
        this.gain = gain
        this.leadIndex = leadIndex
        if (mSoftStrategy is LuckySoftStrategy) {
                    Log.d(TAG,"mSoftStrategy maxDataValueForMv: " + mSoftStrategy.maxDataValueForMv())
        }
        when (gain) {
            0 -> {
                if (mSoftStrategy is LuckySoftStrategy) {
                    mSoftStrategy.setMaxDataValueForMv(2f)
                }
            }
            1 -> {
                if (mSoftStrategy is LuckySoftStrategy) {
                    mSoftStrategy.setMaxDataValueForMv(1f)
                }
            }
            2 -> {
                if (mSoftStrategy is LuckySoftStrategy) {
                    mSoftStrategy.setMaxDataValueForMv(0.5f)
                }
            }
            3 -> {
                if (mSoftStrategy is LuckySoftStrategy) {
                    mSoftStrategy.setMaxDataValueForMv(0.25f)
                }
            }
        }
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