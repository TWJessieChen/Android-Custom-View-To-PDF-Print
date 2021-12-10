package com.jc666.customviewtopdfprint

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintJob
import android.print.PrintManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer.measure
import androidx.constraintlayout.widget.ConstraintLayout
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import com.gkemon.XMLtoPDF.model.FailureResponse
import com.gkemon.XMLtoPDF.model.SuccessResponse


class MainActivity : AppCompatActivity() {

    private var btn_generate_pdf: Button? = null

    private var btn_print_pdf: Button? = null

    private var printManager: PrintManager? = null

    private val viewModel by lazy {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        printManager = getSystemService(PRINT_SERVICE) as PrintManager

        btn_generate_pdf = findViewById(R.id.btn_generate_pdf)
        btn_print_pdf = findViewById(R.id.btn_print_pdf)


        btn_generate_pdf!!.setOnClickListener {
            val inflater = LayoutInflater.from(this@MainActivity)
            val inflatedFrame: View = inflater.inflate(R.layout.ecg_report, null)

            val frameLayout = inflatedFrame.findViewById(R.id.screen) as ConstraintLayout
            val tv_first_name = inflatedFrame.findViewById(R.id.tv_first_name) as TextView
            val tv_last_name = inflatedFrame.findViewById(R.id.tv_last_name) as TextView
            val tv_patient_age = inflatedFrame.findViewById(R.id.tv_patient_age) as TextView
            val tv_patient_number = inflatedFrame.findViewById(R.id.tv_patient_number) as TextView
            val tv_patient_brithday = inflatedFrame.findViewById(R.id.tv_patient_brithday) as TextView
            tv_first_name.setText("JC")
            tv_last_name.setText("666")
            tv_patient_number.setText("病歷號碼    GHGFVJ654D563FG7")
            tv_patient_age.setText("年齡: 17")
            tv_patient_brithday.setText("生日 2004.03.11")

            val displayMetrics = DisplayMetrics()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this@MainActivity.display?.getRealMetrics(displayMetrics)
                displayMetrics.densityDpi
            }
            else{
                this@MainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            }

            inflatedFrame.measure(
                View.MeasureSpec.makeMeasureSpec(
                    displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
                ),
                View.MeasureSpec.makeMeasureSpec(
                    displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
                )
            )

            inflatedFrame.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)

//            inflatedFrame.measure(
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//            )
//            inflatedFrame.layout(0, 0, frameLayout.measuredWidth, frameLayout.measuredHeight)

            inflatedFrame.isDrawingCacheEnabled = true
            inflatedFrame.buildDrawingCache(true)

            viewModel.generatePdf(inflatedFrame)
        }

        btn_print_pdf!!.setOnClickListener {

            val inflatedFrame: View = getLayoutInflater().inflate(R.layout.ecg_report_sdk, null)

            val frameLayout = inflatedFrame.findViewById(R.id.screen) as ConstraintLayout
            val tv_first_name = inflatedFrame.findViewById(R.id.tv_first_name) as TextView
            val tv_last_name = inflatedFrame.findViewById(R.id.tv_last_name) as TextView
            val tv_patient_age = inflatedFrame.findViewById(R.id.tv_patient_age) as TextView
            val tv_patient_number = inflatedFrame.findViewById(R.id.tv_patient_number) as TextView
            val tv_patient_brithday = inflatedFrame.findViewById(R.id.tv_patient_brithday) as TextView
            tv_first_name.setText("JC")
            tv_last_name.setText("666")
            tv_patient_number.setText("病歷號碼    GHGFVJ654D563FG7")
            tv_patient_age.setText("年齡: 17")
            tv_patient_brithday.setText("生日 2004.03.11")

//            val displayMetrics = DisplayMetrics()
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                this@MainActivity.display?.getRealMetrics(displayMetrics)
//                displayMetrics.densityDpi
//            }
//            else{
//                this@MainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
//            }
//
//            inflatedFrame.measure(
//                View.MeasureSpec.makeMeasureSpec(
//                    displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
//                ),
//                View.MeasureSpec.makeMeasureSpec(
//                    displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
//                )
//            )
//
//            inflatedFrame.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)

            inflatedFrame.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            inflatedFrame.layout(0, 0, frameLayout.measuredWidth, frameLayout.measuredHeight)

            inflatedFrame.isDrawingCacheEnabled = true
            inflatedFrame.buildDrawingCache(true)

            viewModel.generatePdfFromGkemon(inflatedFrame, this@MainActivity)
        }

        viewModel.generatePdfResult.observe(this) { it ->
            if(it != null) {
                //針對紙張做設定，紙張格式，紙張橫向直向等等
                val printAttributesBuilder = PrintAttributes.Builder()
                printAttributesBuilder
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE)

                val printJob = print(
                    "Cover PDF",
                    PdfDocumentAdapter(applicationContext, it),
                    printAttributesBuilder.build()
                )
            }
        }

    }


    private fun print(
        name: String, adapter: PrintDocumentAdapter,
        attrs: PrintAttributes
    ): PrintJob? {
        startService(Intent(this, PrintJobMonitorService::class.java))
        return printManager!!.print(name, adapter, attrs)
    }



}