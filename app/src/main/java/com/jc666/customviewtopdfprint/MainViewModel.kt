package com.jc666.customviewtopdfprint

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.view.View
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import com.gkemon.XMLtoPDF.model.FailureResponse
import com.gkemon.XMLtoPDF.model.SuccessResponse
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception


/**
 *
 * ViewModel架構
 * 使用LiveData
 * 使用CoroutineScope
 *
 * @author JC666
 */

class MainViewModel()  : ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName

    val generatePdfResult = MutableLiveData<String>()

    val writePdfResult = MutableLiveData<String>()

    val errorMessage = MutableLiveData<String>()

    val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val ioScope = CoroutineScope(Dispatchers.IO + exceptionHandler)

    init {
        viewModelScope.launch {
            //init
            Log.d(TAG, "MainViewModel init")

        }
    }

    fun saveGenerateECGMaskBmp(bmp: Bitmap) {
        ioScope.launch {
            val currentTimeMillis = System.currentTimeMillis()
            saveBitmap(bmp, MainApplication.internalFilePath + File.separator + currentTimeMillis + ".png")
        }
    }

    fun generatePdf(v: View) {
        ioScope.launch {
//            val bitmap = v.getDrawingCache()

            val bitmap = Bitmap.createBitmap(842, 595, Bitmap.Config.ARGB_8888)
            val canvas_bmp = Canvas(bitmap)
            v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            v.draw(canvas_bmp)

//            val bitmap = loadBitmapFromView(v)
            val currentTimeMillis = System.currentTimeMillis()
            saveBitmap(bitmap, MainApplication.internalFilePath + File.separator + currentTimeMillis + ".png")

//            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 595, 842, true)

            // Create a PdfDocument with a page of the same size as the image
            val document = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
            val page = document.startPage(pageInfo)

            // Draw the bitmap onto the page
            val canvas: Canvas = page.canvas
            canvas.drawBitmap(bitmap, 0f, 0f, null)
            document.finishPage(page)

            // Write the PDF file to a file
            document.writeTo(FileOutputStream(MainApplication.internalFilePath + File.separator + currentTimeMillis + ".pdf"))
            document.close()

            viewModelScope.launch {
                generatePdfResult.value =
                    MainApplication.internalFilePath + File.separator + currentTimeMillis + ".pdf"
            }
//            loadBitmapFromView(v)
        }
    }

    fun generatePdf_V2(v: View) {
        ioScope.launch {
            val currentTimeMillis = System.currentTimeMillis()

            v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            var pageHeightInPixel = v.getMeasuredHeight()
            var pageWidthInPixel = v.getMeasuredWidth()



//            if (pageWidthInPixel == PdfGenerator.WRAP_CONTENT_WIDTH && pageHeightInPixel == PdfGenerator.WRAP_CONTENT_HEIGHT) {
//                content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
//                pageHeightInPixel = v.getMeasuredHeight()
//                pageWidthInPixel = v.getMeasuredWidth()
//                PdfGenerator.postScriptThreshold = 1.0
//                PdfGenerator.a4HeightInPostScript = pageHeightInPixel
//            }


            pageHeightInPixel= (pageHeightInPixel * 1.5).toInt()
            pageWidthInPixel = (pageWidthInPixel * 1.5).toInt()

            v.measure(View.MeasureSpec.makeMeasureSpec(pageWidthInPixel, View.MeasureSpec.EXACTLY), View.MeasureSpec.UNSPECIFIED)
            pageHeightInPixel = Math.max(v.getMeasuredHeight(), PdfGenerator.a4HeightInPostScript)


            val document = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidthInPixel, pageHeightInPixel, 1).create()
            val page = document.startPage(pageInfo)
            v.layout(0, 0, pageWidthInPixel, pageHeightInPixel)
            v.draw(page.canvas)
            document.finishPage(page)

            // Write the PDF file to a file
            document.writeTo(FileOutputStream(MainApplication.internalFilePath + File.separator + currentTimeMillis + ".pdf"))
            document.close()

            viewModelScope.launch {
                generatePdfResult.value =
                    MainApplication.internalFilePath + File.separator + currentTimeMillis + ".pdf"
            }
//            loadBitmapFromView(v)
        }
    }


    fun generatePdfFromGkemon(v: View, context: Context) {
        ioScope.launch {
            val currentTimeMillis = System.currentTimeMillis()

            PdfGenerator.getBuilder()
                .setContext(context)
                .fromViewSource()
                .fromView(v)
                .setFileName(currentTimeMillis.toString())
                .setFolderName("ECG_Report")
                .openPDFafterGeneration(false)
                .build(object : PdfGeneratorListener() {
                    override fun onFailure(failureResponse: FailureResponse?) {
                        super.onFailure(failureResponse)
                    }

                    override fun showLog(log: String?) {
                        super.showLog(log)
                    }

                    override fun onStartPDFGeneration() {
                        /*When PDF generation begins to start*/
                    }

                    override fun onFinishPDFGeneration() {
                        /*When PDF generation is finished*/
                    }

                    override fun onSuccess(response: SuccessResponse?) {
                        super.onSuccess(response)
                        viewModelScope.launch {
                            generatePdfResult.value =
                                response!!.path
                            writePdfResult.value =
                                response!!.path
                        }
                    }
                })
        }
    }

    private fun saveBitmap(bitmap: Bitmap?, path: String) {
        if (bitmap != null) {
            try {
                var outputStream: FileOutputStream? = null
                try {
                    outputStream =
                        FileOutputStream(path) //here is set your file path where you want to save or also here you can set file object directly
                    bitmap.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        outputStream
                    ) // bitmap is your Bitmap instance, if you want to compress it you can compress reduce percentage
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    try {
                        outputStream?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
        viewModelScope.cancel()
    }

}
