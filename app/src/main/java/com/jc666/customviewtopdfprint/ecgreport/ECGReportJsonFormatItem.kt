package com.britemed.btecganaar.ecgreport

data class ECGReportJsonFormatItem(
    val ecg: List<Int>,
    val isPacemaker: Int,
    val leadOff: List<Int>,
    val mvData: List<Any>
)