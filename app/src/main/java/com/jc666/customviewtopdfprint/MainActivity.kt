package com.jc666.customviewtopdfprint

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintJob
import android.print.PrintManager
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.gkemon.XMLtoPDF.model.SuccessResponse
import com.jc666.ecglibrary.EcgSoftRenderer
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.Constructor
import java.nio.channels.FileChannel


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
//            val inflater = LayoutInflater.from(this@MainActivity)
//            val inflatedFrame: View = inflater.inflate(R.layout.ecg_report, null)
//
//            val frameLayout = inflatedFrame.findViewById(R.id.screen) as ConstraintLayout
//            val tv_first_name = inflatedFrame.findViewById(R.id.tv_first_name) as TextView
//            val tv_last_name = inflatedFrame.findViewById(R.id.tv_last_name) as TextView
//            val tv_patient_age = inflatedFrame.findViewById(R.id.tv_patient_age) as TextView
//            val tv_patient_number = inflatedFrame.findViewById(R.id.tv_patient_number) as TextView
//            val tv_patient_brithday = inflatedFrame.findViewById(R.id.tv_patient_brithday) as TextView
//            tv_first_name.setText("JC")
//            tv_last_name.setText("666")
//            tv_patient_number.setText("病歷號碼    GHGFVJ654D563FG7")
//            tv_patient_age.setText("年齡: 17")
//            tv_patient_brithday.setText("生日 2004.03.11")
//
////            resizeView(inflatedFrame, 595, 842)
//
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

//            inflatedFrame.measure(
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//            )
//            inflatedFrame.layout(0, 0, frameLayout.measuredWidth, frameLayout.measuredHeight)

//            inflatedFrame.isDrawingCacheEnabled = true
//            inflatedFrame.buildDrawingCache(true)


            // Creating a LinearLayout.LayoutParams object for text view
            var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                LinearLayout.LayoutParams.WRAP_CONTENT // This will define text view height
            )

            val root = LinearLayout(this@MainActivity)
            root.layoutParams = params
            root.layout(0, 0, 595, 842)

            val text_view: TextView = TextView(this@MainActivity)


            // Add margin to the text view
            params.setMargins(10,10,10,10)

            // Now, specify the text view width and height (dimension)
            text_view.layoutParams = params

            // Display some text on the newly created text view
            text_view.text = "Hi, i am a TextView. Number"

            // Set the text view font/text size
            text_view.setTextSize(TypedValue.COMPLEX_UNIT_SP,30F)

            // Set the text view text color
            text_view.setTextColor(Color.RED)

            // Make the text viw text bold italic
            text_view.setTypeface(text_view.typeface, Typeface.BOLD_ITALIC)

            // Change the text view font
            text_view.setTypeface(Typeface.MONOSPACE)

            // Change the text view background color
            text_view.setBackgroundColor(Color.YELLOW)

            // Put some padding on text view text
            text_view.setPadding(50,10,10,10)

//            // Set a click listener for newly generated text view
//            text_view.setOnClickListener{
//                Toast.makeText(this,text_view.text,Toast.LENGTH_SHORT).show()
//            }

            // Finally, add the text view to the view group
            root.addView(text_view)

            root.isDrawingCacheEnabled = true
            root.buildDrawingCache(true)

//            CreateView().setContentView(this@MainActivity)
//            viewModel.generatePdf(CreateView().createView(AnkoContext.create(this@MainActivity) as AnkoContext<MainActivity>))

//            viewModel.generatePdf(BarChart(this@MainActivity))
            viewModel.generatePdfCanvas(this@MainActivity)
        }

        btn_print_pdf!!.setOnClickListener {

//            val inflatedFrame: View = getLayoutInflater().inflate(R.layout.ecg_report_sdk, null)
//
//            val frameLayout = inflatedFrame.findViewById(R.id.screen) as ConstraintLayout
//            val tv_first_name = inflatedFrame.findViewById(R.id.tv_first_name) as TextView
//            val tv_last_name = inflatedFrame.findViewById(R.id.tv_last_name) as TextView
//            val tv_patient_age = inflatedFrame.findViewById(R.id.tv_patient_age) as TextView
//            val tv_patient_number = inflatedFrame.findViewById(R.id.tv_patient_number) as TextView
//            val tv_patient_brithday = inflatedFrame.findViewById(R.id.tv_patient_brithday) as TextView
//            tv_first_name.setText("JC")
//            tv_last_name.setText("666")
//            tv_patient_number.setText("病歷號碼    GHGFVJ654D563FG7")
//            tv_patient_age.setText("年齡: 17")
//            tv_patient_brithday.setText("生日 2004.03.11")

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

//            inflatedFrame.measure(
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//            )
//            inflatedFrame.layout(0, 0, frameLayout.measuredWidth, frameLayout.measuredHeight)
//
//            inflatedFrame.isDrawingCacheEnabled = true
//            inflatedFrame.buildDrawingCache(true)

//            viewModel.generatePdfFromGkemon(CreateView().createView(AnkoContext.create(this@MainActivity) as AnkoContext<MainActivity>), this@MainActivity)
//            viewModel.generatePdf(CreateView().createView(AnkoContext.create(this@MainActivity) as AnkoContext<MainActivity>))

//            viewModel.generatePdf_V2(GenerateECGReportView(this).createECGLayout())
            viewModel.generatePdfCanvas(this@MainActivity)
            val dataParse = ECGDataParse(this@MainActivity)
            val bitmap = EcgSoftRenderer.instantiate(this@MainActivity, dataParse.values) //.setMaxDataValue(2f)
                .startRender()
            viewModel.generatePdfFromGkemon(GenerateECGReportView(this).createECGLayout(bitmap!!), this@MainActivity)
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
//                val document = PdfDocument()
//                document.writeTo(FileOutputStream(it))
//                document.close()
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

//    class CreateView() : AnkoComponent<MainActivity> {
//
//        val customStyle = { v: Any ->
//            when (v) {
//                is Button -> v.textSize = 26f
//                is EditText -> v.textSize = 24f
//            }
//        }
//
//        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
//            var height = px2dip(595)
//            var width = px2dip(842)
//
//            verticalLayout {
//                lparams(width = width.toInt(), height = height.toInt())
//
//                frameLayout {
//                    lparams(width = matchParent, height = wrapContent)
//
//                    imageView(R.mipmap.ecg_background).lparams {
//                        margin = dip(16)
//                        gravity = Gravity.CENTER
//                    }
//
//                    val name = textView {
//                        text =  "JC"
//                    }
//                    val password = textView {
//                        text =  "666"
//                    }
//                }
//
////                relativeLayout {
////                    lparams(width = matchParent, height = wrapContent) {
////                        leftPadding = dip(10)
////                        rightPadding = dip(10)
////                    }
////
////                    imageView(R.mipmap.ecg_background).lparams {
////                        margin = dip(16)
////                        gravity = Gravity.CENTER
////                    }
////
////                    val name = textView {
////                        text =  "JC"
////                    }
////                    val password = textView {
////                        text =  "666"
////                    }
////                }
//            }.applyRecursively(customStyle)
//        }
//
//
//    }
//
//    class MainActivityUi : AnkoComponent<MainActivity> {
//        private val customStyle = { v: Any ->
//            when (v) {
//                is Button -> v.textSize = 26f
//                is EditText -> v.textSize = 24f
//            }
//        }
//
//        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
//            verticalLayout {
//                padding = dip(32)
//
//                imageView(R.mipmap.ecg_background).lparams {
//                    margin = dip(16)
//                    gravity = Gravity.CENTER
//                }
//
//                val name = textView {
//                    text =  "JC"
//                }
//                val password = textView {
//                    text =  "666"
//                }
//
//            }.applyRecursively(customStyle)
//        }
//    }

    private fun resizeView(view: View, newWidth: Int, newHeight: Int) {
        try {
            val ctor: Constructor<out ViewGroup.LayoutParams?> =
                view.layoutParams.javaClass.getDeclaredConstructor(
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
            view.layoutParams = ctor.newInstance(newWidth, newHeight)
        } catch (e: Exception) {
            e.printStackTrace()
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