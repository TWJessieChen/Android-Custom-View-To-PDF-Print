package com.jc666.ecglibrary.view

import com.jc666.ecglibrary.Transformer

/**
 * @author JC666
 * @date 2021/12/14
 * @describe ECG 數據轉成圖片參數設置
 */

interface StaticSoftStrategy {

    fun pictureWidth(): Int //生成图片的宽,像素


    fun pictureHeight(): Int //生成图片的高,像素


    fun pointsPerRow(): Int //一行总共显示几个点


    fun secondsPerRow(): Int //一行显示几秒


    fun pointsPerSecond(): Int //一秒多少个点


    fun gridCountPerRow(): Int //每行显示几个大格子


    fun cellCountPerGrid(): Int //每个大格子由几个小格组成


    fun pixelPerPoint(): Float //每个点占几个像素


    fun pixelPerCell(): Float //每个小格几个像素


    fun totalRows(): Int //总共几行


    fun horizontalPadding(): Int //水平方向边距


    fun verticalPadding(): Int //竖直方向边距


    fun maxDataValueForMv(): Float //一行所表示的最大毫伏数


    fun cellCountsPerMv(): Int //一毫伏占几个小格子


    fun getTransformer(): Transformer? //获取坐标转换管理


}