package com.jc666.customviewtopdfprint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintJob
import android.print.PrintManager
import android.widget.*
import com.jc666.ecglibrary.ECGReportSoftRenderer
import com.jc666.ecglibrary.ECGReportViewSoftRenderer
import org.jetbrains.anko.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel


class MainActivity : AppCompatActivity() {

    private var btn_one_lead_ecg_report: Button? = null

    private var btn_two_lead_ecg_report: Button? = null

    private var btn_one_twelve_ecg_report: Button? = null

    private var btn_two_six_ecg_report: Button? = null

    private var btn_four_three_ecg_report: Button? = null

    private var printManager: PrintManager? = null

    private var imageView: ImageView? = null

    private val viewModel by lazy {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        printManager = getSystemService(PRINT_SERVICE) as PrintManager
        btn_one_twelve_ecg_report = findViewById(R.id.btn_one_twelve_ecg_report)
        btn_two_six_ecg_report = findViewById(R.id.btn_two_six_ecg_report)
        btn_four_three_ecg_report = findViewById(R.id.btn_four_three_ecg_report)
        btn_one_lead_ecg_report = findViewById(R.id.btn_one_lead_ecg_report)
        btn_two_lead_ecg_report = findViewById(R.id.btn_two_lead_ecg_report)
        imageView = findViewById(R.id.image)

        btn_two_lead_ecg_report!!.setOnClickListener {
            val dataParse = ECGDataParse(this@MainActivity)
            val bitmap = ECGReportViewSoftRenderer.instantiate(this@MainActivity,
                dataParse.valuesOneLeadTest,
                0,
                "III",
                2) //.setMaxDataValue(2f)
                .startRender()
            //viewModel.saveGenerateECGMaskBmp(bitmap!!)

            imageView!!.setImageBitmap(bitmap)

        }

        btn_one_lead_ecg_report!!.setOnClickListener {
            val dataParse = ECGDataParse(this@MainActivity)
            val bitmap = ECGReportViewSoftRenderer.instantiate(this@MainActivity,
                dataParse.valuesOneLeadTest,
                1,
                "aVR",
                3) //.setMaxDataValue(2f)
                .startRender()
            //viewModel.saveGenerateECGMaskBmp(bitmap!!)

            imageView!!.setImageBitmap(bitmap)

        }

        btn_one_twelve_ecg_report!!.setOnClickListener {
            val dataParse = ECGDataParse(this@MainActivity)
            val bitmap = ECGReportSoftRenderer.instantiate(this@MainActivity, dataParse.valuesTwelve, 0) //.setMaxDataValue(2f)
                .startRender()
            viewModel.saveGenerateECGMaskBmp(bitmap!!)

            val patientInfo = PatientInfo(
                firstName = "JC",
                lastName = "666",
                patientNumberTitleValue = "病歷號碼",
                patientNumberValue = "GHGFVJ654D563FG7",
                gender = 1,
                patientAgeTitleValue = "年齡: ",
                patientAgeValue = "35",
                patientBirthdayTitleValue = "生日 ",
                patientBirthdayValue = "1986.04.22",
                ecgReportNotesValue = "Rhythm: sinusrhythm with tachycardia. QRST Evaluation: indeterminate ECG. Summary: abnormal ECG.",
                patientHRValue = "120",
                reportMagnificationValue = "增益:x1.0",
                lowChannelValue = "低通:150Hz",
                highChannelValue = "高通:0.07Hz",
                acChannelValue = "交流電:80Hz",
                hrDetectValue = "心律調節器偵測:ON",
                pDurValue = 102,
                pRIntValue = 148,
                qRSDurValue = 82,
                qTIntValue = 298,
                qTcIntValue = 421,
                pAxisValue = 46,
                qRSAxisValue = 47,
                tAxisValue = 47,
                rV5SV1Value = 1.253
            )

            viewModel.generatePdfFromGkemon(GenerateECGReportView(this).createECGLayout(bitmap, patientInfo), this@MainActivity)
        }

        btn_two_six_ecg_report!!.setOnClickListener {
            val dataParse = ECGDataParse(this@MainActivity)
            val bitmap = ECGReportSoftRenderer.instantiate(this@MainActivity, dataParse.valuesSix, 0) //.setMaxDataValue(2f)
                .startRender()
            viewModel.saveGenerateECGMaskBmp(bitmap!!)

            val patientInfo = PatientInfo(
                firstName = "JC",
                lastName = "666",
                patientNumberTitleValue = "病歷號碼",
                patientNumberValue = "GHGFVJ654D563FG7",
                gender = 2,
                patientAgeTitleValue = "年齡: ",
                patientAgeValue = "35",
                patientBirthdayTitleValue = "生日 ",
                patientBirthdayValue = "1986.04.22",
                ecgReportNotesValue = "Rhythm: sinusrhythm with tachycardia. QRST Evaluation: indeterminate ECG. Summary: abnormal ECG.",
                patientHRValue = "120",
                reportMagnificationValue = "增益:x1.0",
                lowChannelValue = "低通:150Hz",
                highChannelValue = "高通:0.07Hz",
                acChannelValue = "交流電:80Hz",
                hrDetectValue = "心律調節器偵測:ON",
                pDurValue = 102,
                pRIntValue = 148,
                qRSDurValue = 82,
                qTIntValue = 298,
                qTcIntValue = 421,
                pAxisValue = 46,
                qRSAxisValue = 47,
                tAxisValue = 47,
                rV5SV1Value = 1.253
            )

            viewModel.generatePdfFromGkemon(GenerateECGReportView(this).createECGLayout(bitmap, patientInfo), this@MainActivity)
        }

        btn_four_three_ecg_report!!.setOnClickListener {
            val dataParse = ECGDataParse(this@MainActivity)
            val bitmap = ECGReportSoftRenderer.instantiate(this@MainActivity, dataParse.values, 0) //.setMaxDataValue(2f)
                .startRender()
            viewModel.saveGenerateECGMaskBmp(bitmap!!)

            val patientInfo = PatientInfo(
                firstName = "JC",
                lastName = "666",
                patientNumberTitleValue = "病歷號碼",
                patientNumberValue = "GHGFVJ654D563FG7",
                gender = 3,
                patientAgeTitleValue = "年齡: ",
                patientAgeValue = "35",
                patientBirthdayTitleValue = "生日 ",
                patientBirthdayValue = "1986.04.22",
                ecgReportNotesValue = "Rhythm: sinusrhythm with tachycardia. QRST Evaluation: indeterminate ECG. Summary: abnormal ECG.",
                patientHRValue = "120",
                reportMagnificationValue = "增益:x1.0",
                lowChannelValue = "低通:150Hz",
                highChannelValue = "高通:0.07Hz",
                acChannelValue = "交流電:80Hz",
                hrDetectValue = "心律調節器偵測:ON",
                pDurValue = 102,
                pRIntValue = 148,
                qRSDurValue = 82,
                qTIntValue = 298,
                qTcIntValue = 421,
                pAxisValue = 46,
                qRSAxisValue = 47,
                tAxisValue = 47,
                rV5SV1Value = 1.253
            )

            viewModel.generatePdfFromGkemon(GenerateECGReportView(this).createECGLayout(bitmap, patientInfo), this@MainActivity)
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

        viewModel.writePdfResult.observe(this) { it ->
            if(it != null) {
                val currentTimeMillis = System.currentTimeMillis()
                copyFile(File(it), File(MainApplication.internalFilePath + File.separator + currentTimeMillis + ".pdf"))
            }
        }

    }

    @Throws(IOException::class)
    fun copyFile(src: File?, dst: File?) {
        val inFile = FileInputStream(src)
        val out = FileOutputStream(dst)
        var fromChannel: FileChannel? = null
        var toChannel: FileChannel? = null
        try {
            fromChannel = inFile.channel
            toChannel = out.channel
            fromChannel.transferTo(0, fromChannel.size(), toChannel)
        } finally {
            fromChannel?.close()
            toChannel?.close()
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