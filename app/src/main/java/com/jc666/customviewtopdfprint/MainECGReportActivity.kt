package com.jc666.customviewtopdfprint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainECGReportActivity : AppCompatActivity() {
    private val TAG = MainECGReportActivity::class.java.simpleName

    var one_twelve_Static_wave_view_i: StaticWaveEcgView? = null

    private val viewModel by lazy {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ecg_report)

        one_twelve_Static_wave_view_i = findViewById(R.id.one_twelve_wave_view_i)

        var dataParseResult = ECGDataBriteMEDParse(this@MainECGReportActivity)
//        for(d in 0 until dataParseResult.valuesOneLeadTest.size) {
//            one_twelve_Static_wave_view_i!!.showLine((dataParseResult.valuesOneLeadTest[d].ecg[0].toFloat()))
//        }



    }


}