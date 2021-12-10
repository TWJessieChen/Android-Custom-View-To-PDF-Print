package com.jc666.customviewtopdfprint

import android.app.Service
import android.print.PrintManager
import android.content.Intent
import android.print.PrintJobInfo
import android.os.IBinder
import android.os.SystemClock
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class PrintJobMonitorService : Service(), Runnable {
    private var mgr: PrintManager? = null
    private val executor = Executors.newSingleThreadScheduledExecutor()
    private var lastPrintJobTime = SystemClock.elapsedRealtime()
    override fun onCreate() {
        super.onCreate()
        mgr = getSystemService(PRINT_SERVICE) as PrintManager
        executor.scheduleAtFixedRate(
            this, POLL_PERIOD.toLong(), POLL_PERIOD.toLong(),
            TimeUnit.SECONDS
        )
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        executor.shutdown()
        super.onDestroy()
    }

    override fun run() {
        for (job in mgr!!.printJobs) {
            if (job.info.state == PrintJobInfo.STATE_CREATED || job.isQueued || job.isStarted) {
                lastPrintJobTime = SystemClock.elapsedRealtime()
            }
        }
        val delta = SystemClock.elapsedRealtime() - lastPrintJobTime
        if (delta > POLL_PERIOD * 2) {
            stopSelf()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private const val POLL_PERIOD = 3
    }
}