package com.jc666.customviewtopdfprint

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.os.Environment
import android.util.Log
import kotlinx.coroutines.*
import java.io.File
import java.util.*

/**
 *
 * Application()初始建置
 *
 * @author JC666
 */

class MainApplication  : Application() {
    private val TAG = MainApplication::class.java.simpleName

    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")

        appContentResolver = contentResolver

        appContext = applicationContext

        internalFilePath = applicationContext.getFilesDir().getAbsolutePath()

        externalFilePath = applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!.getAbsolutePath()

        //這個方法，目前還可以使用，未來可能完全被Deprecated掉，不能使用，這時候就要尋找另一個方式分享檔案了
        externalPublicFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)!!.getAbsolutePath()

    }

    override fun onLowMemory() {
        super.onLowMemory()
        applicationScope.cancel()
    }

    companion object {
        private val TAG = MainApplication::class.java.simpleName

        var appContentResolver: ContentResolver? = null
            private set
        var appContext: Context? = null

        //內部絕對位置，專門進行export/import等等動作
        var internalFilePath: String? = null

        //路徑 :  /storage/emulated/0/Android/data/<app package name>
        var externalFilePath: String? = null

        //路徑 : /storage/emulated/0 (目前不知道何時會被估狗正式給關掉不能使用，當不能使用的時候，匯出檔案就要用另一種方式了，
        // 必須要使用 Fileprovider 方式去分享檔案了!!!)
        var externalPublicFilePath: String? = null

    }

}