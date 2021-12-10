package com.jc666.customviewtopdfprint

import android.content.Context
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.os.CancellationSignal
import android.print.*
import android.util.Log
import java.io.*
import java.lang.Exception

internal class PdfDocumentAdapter(ctxt: Context?, private val pdfPath: String) :
    ThreadedPrintDocumentAdapter(ctxt) {

    public override fun buildLayoutJob(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?, extras: Bundle?
    ): LayoutJob {
        return PdfLayoutJob(
            pdfPath, oldAttributes, newAttributes,
            cancellationSignal, callback, extras
        )
    }

    public override fun buildWriteJob(
        pages: Array<PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?, ctxt: Context?
    ): WriteJob {
        return PdfWriteJob(
            pdfPath, pages, destination, cancellationSignal,
            callback, ctxt
        )
    }

    private class PdfLayoutJob internal constructor(
        private val pdfPath: String,
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?, extras: Bundle?
    ) : LayoutJob(
        oldAttributes, newAttributes, cancellationSignal, callback,
        extras
    ) {
        override fun run() {
            if (cancellationSignal!!.isCanceled) {
                callback!!.onLayoutCancelled()
                Log.e(javaClass.simpleName, "callback.onLayoutCancelled()")
            } else {
                val builder = PrintDocumentInfo.Builder(pdfPath)
                builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                    .build()
                callback!!.onLayoutFinished(
                    builder.build(),
                    newAttributes != oldAttributes
                )
                Log.e(javaClass.simpleName, "callback.onLayoutFinished")
            }
        }
    }

    private class PdfWriteJob internal constructor(
        private val pdfPath: String, pages: Array<PageRange>?, destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?, ctxt: Context?
    ) : WriteJob(pages, destination, cancellationSignal, callback, ctxt) {
        override fun run() {
            var fileInputStream: FileInputStream? = null
            var fileOutputStream: OutputStream? = null

            try {

//        File pdfFile = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + "/pdf/pallet.pdf");
                val pdfFile = File(pdfPath)
                fileInputStream = FileInputStream(pdfFile)
                fileOutputStream = FileOutputStream(destination!!.fileDescriptor)
                val buf = ByteArray(16384)
                var size: Int

                while (fileInputStream.read(buf).also { size = it } >= 0
                    && !cancellationSignal!!.isCanceled) {
                    fileOutputStream.write(buf, 0, size)
                }

                if (cancellationSignal!!.isCanceled) {
                    callback!!.onWriteCancelled()
                    Log.e(javaClass.simpleName, "callback.onWriteCancelled();")
                } else {
                    callback!!.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
                    Log.e(javaClass.simpleName, "callback.onWriteFinished")
                }
            } catch (e: Exception) {
                callback!!.onWriteFailed(e.message)
                Log.e(javaClass.simpleName, "Exception printing PDF", e)
            } finally {
                try {
                    fileInputStream!!.close()
                    fileOutputStream!!.close()
                } catch (e: IOException) {
                    Log.e(javaClass.simpleName, "Exception cleaning up from printing PDF", e)
                }
            }
        }
    }
}