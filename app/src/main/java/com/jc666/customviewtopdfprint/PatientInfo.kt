package com.jc666.customviewtopdfprint

data class PatientInfo(val firstName: String,
                       val lastName: String,
                       val patientNumberTitleValue: String,
                       val patientNumberValue: String,
                       val gender: Int, //1:male, 2:female, 3:other
                       val patientAgeTitleValue: String,
                       val patientAgeValue: String,
                       val patientBirthdayTitleValue: String,
                       val patientBirthdayValue: String,
                       val ecgReportNotesValue: String,
                       val patientHRValue: String,
                       val reportMagnificationValue: String,
                       val lowChannelValue: String,
                       val highChannelValue: String,
                       val acChannelValue: String,
                       val hrDetectValue: String,
                       val pDurValue: Int,
                       val pRIntValue: Int,
                       val qRSDurValue: Int,
                       val qTIntValue: Int,
                       val qTcIntValue: Int,
                       val pAxisValue: Int,
                       val qRSAxisValue: Int,
                       val tAxisValue: Int,
                       val rV5SV1Value: Double)
