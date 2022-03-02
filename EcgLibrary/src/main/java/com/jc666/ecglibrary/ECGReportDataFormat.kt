package com.jc666.ecglibrary

data class ECGReportDataFormat(
    val ecg: List<Int>,
    val isPacemaker: Int,
    val leadOff: List<Int>
)