package com.jc666.customviewtopdfprint

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View

open class OnScaleWithGestureDetectorTouchListener(ctx: Context) : View.OnTouchListener {
    private val TAG = OnScaleWithGestureDetectorTouchListener::class.java.simpleName

    private val scaleGestureDetector: ScaleGestureDetector

    private val gestureDetector: GestureDetector

    init {
        scaleGestureDetector = ScaleGestureDetector(ctx, ScaleGestureListener())
        gestureDetector = GestureDetector(ctx, GestureListener())
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if(event.pointerCount > 1) {
            return scaleGestureDetector.onTouchEvent(event)
        }
        return gestureDetector.onTouchEvent(event)
    }

    private inner class ScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
            Log.d(TAG,"onScaleEnd")
            onScaleEnd()
        }

    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            Log.d(TAG,"onSingleTapConfirmed")
            onClick()
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            Log.d(TAG,"onDoubleTap")
            onDoubleClick()
            return true
        }
    }

    open fun onScaleEnd() {}
    open fun onClick() {}
    open fun onDoubleClick() {}
}