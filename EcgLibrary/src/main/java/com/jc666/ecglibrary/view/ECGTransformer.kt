package com.jc666.ecglibrary.view

import android.graphics.Rect
import com.jc666.ecglibrary.Coordinateport

/**
 * @author JC666
 * @date 2021/12/15
 * @describe 坐标系转换
 */
class ECGTransformer {
    /**
     * 实际可视范围,视图虚拟大小
     */
    private val visibleCoorport = Coordinateport()

    /**
     * 数据绘制区域，已经去掉坐标所占的区域
     * 手机屏幕实际物理值大小
     */
    private val dataContentRect = Rect()
    fun setVisibleCoorport(left: Float, top: Float, right: Float, bottom: Float) {
        visibleCoorport[left, top, right] = bottom
    }

    fun setDataContentRect(left: Int, top: Int, right: Int, bottom: Int) {
        dataContentRect[left, top, right] = bottom
    }

    fun computeRawX(index: Int): Float {
        val pixelOffset =
            (index - visibleCoorport.left) * (dataContentRect.width() / visibleCoorport.width())
        return dataContentRect.left + pixelOffset
    }

    fun computeRawY(value: Float): Float {
        val pixelOffset =
            (value - visibleCoorport.bottom) * (dataContentRect.height() / visibleCoorport.height())
        return dataContentRect.bottom - pixelOffset
    }

    fun needDraw(currentY: Float, nextY: Float): Boolean {
        return if (currentY == nextY) {
            currentY != dataContentRect.top.toFloat() && currentY != dataContentRect.bottom.toFloat()
        } else true
    }
}