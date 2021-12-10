package com.jc666.customviewtopdfprint

import android.content.Context
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.os.CancellationSignal
import android.print.*
import java.util.concurrent.Executors

internal abstract class ThreadedPrintDocumentAdapter(ctxt: Context?) : PrintDocumentAdapter() {
    abstract fun buildLayoutJob(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?
    ): LayoutJob

    abstract fun buildWriteJob(
        pages: Array<PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?,
        ctxt: Context?
    ): WriteJob

    private var ctxt: Context? = null
    private val threadPool = Executors.newFixedThreadPool(1)
    override fun onLayout(
        oldAttributes: PrintAttributes,
        newAttributes: PrintAttributes,
        cancellationSignal: CancellationSignal,
        callback: LayoutResultCallback, extras: Bundle
    ) {
        threadPool.submit(
            buildLayoutJob(
                oldAttributes, newAttributes,
                cancellationSignal, callback,
                extras
            )
        )
    }

    override fun onWrite(
        pages: Array<PageRange>,
        destination: ParcelFileDescriptor,
        cancellationSignal: CancellationSignal,
        callback: WriteResultCallback
    ) {
        threadPool.submit(
            buildWriteJob(
                pages, destination,
                cancellationSignal, callback, ctxt
            )
        )
    }

    override fun onFinish() {
        threadPool.shutdown()
        super.onFinish()
    }

    abstract class LayoutJob internal constructor(
        var oldAttributes: PrintAttributes?,
        var newAttributes: PrintAttributes?,
        var cancellationSignal: CancellationSignal?,
        var callback: LayoutResultCallback?, var extras: Bundle?
    ) : Runnable

    abstract class WriteJob internal constructor(
        var pages: Array<PageRange>?, var destination: ParcelFileDescriptor?,
        var cancellationSignal: CancellationSignal?,
        var callback: WriteResultCallback?, var ctxt: Context?
    ) : Runnable

    init {
        this.ctxt = ctxt
    }
}