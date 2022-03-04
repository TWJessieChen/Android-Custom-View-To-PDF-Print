package com.jc666.customviewtopdfprint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jc666.customviewtopdfprint.ecgview.EcgView

class MainEcgActivity : AppCompatActivity() {

//    private var ecg_data_ecgView_i: EcgView? = null
//    private var ecg_data_ecgView_ii: EcgView? = null
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
//        ecg_data_ecgView_i = findViewById(R.id.ecg_data_ecgView_i)
//        ecg_data_ecgView_ii = findViewById(R.id.ecg_data_ecgView_ii)
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
//
//        val floats = getFloats()
//        ecg_data_ecgView_i?.setLeadData(floats)
//        ecg_data_ecgView_ii?.setLeadData(floats)
//        ecg_data_ecgView_iii?.setLeadData(floats)
//        ecg_data_ecgView_iiii?.setLeadData(floats)
//        ecg_data_ecgView_iiiii?.setLeadData(floats)
//        ecg_data_ecgView_iiiiii?.setLeadData(floats)
//        ecg_data_ecgView_iiiiiii?.setLeadData(floats)
//        ecg_data_ecgView_iiiiiiii?.setLeadData(floats)
//        ecg_data_ecgView_iiiiiiiii?.setLeadData(floats)
//        ecg_data_ecgView_iiiiiiiiii?.setLeadData(floats)
//        ecg_data_ecgView_iiiiiiiiiii?.setLeadData(floats)
//        ecg_data_ecgView_iiiiiiiiiiii?.setLeadData(floats)

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