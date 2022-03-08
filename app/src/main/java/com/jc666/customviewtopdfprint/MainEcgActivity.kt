package com.jc666.customviewtopdfprint

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.HorizontalScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.jc666.ecglibrary.constants.ConstContent
import com.jc666.ecglibrary.view.StaticECGBackgroundView
import com.jc666.ecglibrary.view.StaticECGDataView

class MainEcgActivity : AppCompatActivity() {
    private val TAG = MainEcgActivity::class.java.simpleName

//    private var cc_layout: ConstraintLayout? = null
    private var ecg_data_ecgView_i: StaticECGBackgroundView? = null
    private var ecg_data_ecgView_ii: StaticECGBackgroundView? = null

    private var static_ecg_data_ecgView_i: StaticECGDataView? = null
    private var static_ecg_data_ecgView_ii: StaticECGDataView? = null

    private var hs_static_ecg_data_ecgView_i: HorizontalScrollView? = null
    private var hs_static_ecg_data_ecgView_ii: HorizontalScrollView? = null
//    private var ecg_data_ecgView_iii: EcgView? = null
//    private var ecg_data_ecgView_iiii: EcgView? = null
//    private var ecg_data_ecgView_iiiii: EcgView? = null
//    private var ecg_data_ecgView_iiiiii: EcgView? = null
//    private var ecg_data_ecgView_iiiiiii: EcgView? = null
//    private var ecg_data_ecgView_iiiiiiii: EcgView? = null
//    private var ecg_data_ecgView_iiiiiiiii: EcgView? = null
//    private var ecg_data_ecgView_iiiiiiiiii: EcgView? = null
//    private var ecg_data_ecgView_iiiiiiiiiii: EcgView? = null
//    private var ecg_data_ecgView_iiiiiiiiiiii: EcgView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ecg)

        var dataParseResult = ECGDataBriteMEDParse(this@MainEcgActivity)

        ecg_data_ecgView_i = findViewById(R.id.ecg_data_ecgView_i)
        ecg_data_ecgView_ii = findViewById(R.id.ecg_data_ecgView_ii)

        static_ecg_data_ecgView_ii = findViewById(R.id.static_ecg_data_ecgView_ii)
        static_ecg_data_ecgView_i = findViewById(R.id.static_ecg_data_ecgView_i)

        hs_static_ecg_data_ecgView_i = findViewById(R.id.hs_static_ecg_data_ecgView_i)
        hs_static_ecg_data_ecgView_ii = findViewById(R.id.hs_static_ecg_data_ecgView_ii)
//        ecg_data_ecgView_iii = findViewById(R.id.ecg_data_ecgView_iii)
//        ecg_data_ecgView_iiii = findViewById(R.id.ecg_data_ecgView_iiii)
//        ecg_data_ecgView_iiiii = findViewById(R.id.ecg_data_ecgView_iiiii)
//        ecg_data_ecgView_iiiiii = findViewById(R.id.ecg_data_ecgView_iiiiii)
//        ecg_data_ecgView_iiiiiii = findViewById(R.id.ecg_data_ecgView_iiiiiii)
//        ecg_data_ecgView_iiiiiiii = findViewById(R.id.ecg_data_ecgView_iiiiiiii)
//        ecg_data_ecgView_iiiiiiiii = findViewById(R.id.ecg_data_ecgView_iiiiiiiii)
//        ecg_data_ecgView_iiiiiiiiii = findViewById(R.id.ecg_data_ecgView_iiiiiiiiii)
//        ecg_data_ecgView_iiiiiiiiiii = findViewById(R.id.ecg_data_ecgView_iiiiiiiiiii)
//        ecg_data_ecgView_iiiiiiiiiiii = findViewById(R.id.ecg_data_ecgView_iiiiiiiiiiii)


        ecg_data_ecgView_i!!.setBackgroundParams(ConstContent.BACKGROUND_DRAW_MODE_BLACK,ConstContent.GAIN_II_MODE)

        static_ecg_data_ecgView_i!!.setLeadData(ConstContent.BACKGROUND_DRAW_MODE_BLACK,
                                                dataParseResult.valuesOneLeadTest,
                                                ConstContent.GAIN_II_MODE,
                                                0)

        ecg_data_ecgView_ii!!.setBackgroundParams(ConstContent.BACKGROUND_DRAW_MODE_WHITE,ConstContent.GAIN_III_MODE)
        static_ecg_data_ecgView_ii!!.setLeadData(ConstContent.BACKGROUND_DRAW_MODE_WHITE,
            dataParseResult.valuesOneLeadTest,
            ConstContent.GAIN_III_MODE,
            1)


        static_ecg_data_ecgView_i!!.setOnTouchListener(object : OnScaleWithGestureDetectorTouchListener(this@MainEcgActivity) {
            override fun onScaleEnd() {
                Log.d(TAG,"static_ecg_data_ecgView_i onSwipe")

            }
            override fun onClick() {
                Log.d(TAG,"static_ecg_data_ecgView_i onClick")

            }
            override fun onDoubleClick() {
                Log.d(TAG,"static_ecg_data_ecgView_i onDoubleClick")

            }
        })

        hs_static_ecg_data_ecgView_i!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP-> {
                        hs_static_ecg_data_ecgView_ii!!.smoothScrollTo(v.getScrollX(),0)
                    }
                }
                return false
            }
        })
    }

    private fun getFloats(): FloatArray? {
        val array = Constant.STRING_ECG_VALUE.split(",").toTypedArray()
        val floats = FloatArray(array.size)
        for (i in floats.indices) {
            floats[i] = array[i].toFloat()
        }
        return floats
    }
}