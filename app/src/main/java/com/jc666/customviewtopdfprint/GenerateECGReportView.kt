package com.jc666.customviewtopdfprint

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Typeface
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

class GenerateECGReportView(context: Context) {
    private val TAG = GenerateECGReportView::class.java.simpleName

    private var context: Context

    private var patientHRTitleValue = "HR "
    private var patientHRUnitValue = " bpm"
    private var llPatientNameNumberAgePadding = 0
    private var llPatientNameNumberAgePaddingHeight = 0
    private var ivECGHeight = 0
    private var ivECGWidth = 0
    private var tvTimeWidth = 0
    private var llPatientECGInfoContentWidth = 0
    private var tvNameContentSize = 0F
    private var tvLastNamePadding = 0
    private var tvECGReportNotes = 0F
    private var tvECGReportNotesPadding = 0
    private var tvPatientTitle = 0F
    private var tvPatientNumber = 0F
    private var tvPatientNumberPadding = 0
    private var ivGender = 0
    private var tvPatientAge = 0F
    private var tvPatientAgeValuePadding = 0
    private var tvPatientBirthdayValuePadding = 0
    private var ivHRIconWidthWidth = 0
    private var tvPatientECGInfoContent = 0F
    private var tvPatientECGInfoContentPadding = 0
    private var tvPatientHRValuePadding = 0
    private var llPatientECGInfoContentPaddingWidth = 0
    private var llPatientECGInfoContentPaddingHeight = 0
    private var llReportMagnificationLayoutHeight = 0
    private var tvReportMagnificationChannelAC = 0F

//    private var gender = 2
//    private var firstNameValue = "JC"
//    private var lastNameValue = "666"
//    private var patientNumberTitleValue = "病歷號碼"
//    private var patientNumberValue = "GHGFVJ654D563FG7"
//    private var patientAgeTitleValue = "年齡: "
//    private var patientAgeValue = "35"
//    private var patientBirthdayTitleValue = "生日 "
//    private var patientBirthdayValue = "1986.04.22"
//    private var ecgReportNotesValue = "Rhythm: sinusrhythm with tachycardia. QRST Evaluation: indeterminate ECG. Summary: abnormal ECG."
//    private var patientHRValue = "120"
//    private var reportMagnificationValue = "增益:x1.0"
//    private var lowChannelValue = "低通:150Hz"
//    private var highChannelValue = "高通:0.05Hz"
//    private var acChannelValue = "交流電:60Hz"
//    private var hrDetectValue = "心律調節器偵測:ON"
//    private var pDurValue = 102
//    private var pRIntValue = 148
//    private var qRSDurValue = 82
//    private var qTIntValue = 298
//    private var qTcIntValue = 421
//    private var pAxisValue = 46
//    private var qRSAxisValue = 47
//    private var tAxisValue = 47
//    private var rV5SV1Value = 1.253

    init {
        this.context = context
    }

    fun createECGLayout(bmp: Bitmap, patientInfo: PatientInfo, grid: Int) : View {
        val parent = LinearLayout(context)
        parent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

//        val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT)
//        params.width = 842
//        params.height = 595
//        parent.layoutParams = params
//        parent.layoutParams = LinearLayout.LayoutParams(dip2px(842f),dip2px(595f))
        parent.orientation = LinearLayout.VERTICAL

        val density = Resources.getSystem().displayMetrics.density
        val scaledDensity = Resources.getSystem().displayMetrics.scaledDensity

        Log.d(TAG, "density: " + density)
        Log.d(TAG, "scaledDensity: " + scaledDensity)

        when(grid) {
            0 -> {
                ivECGHeight = dip2px((14.160319845242177 * 36*16.3).toFloat())
                ivECGWidth = dip2px((14.160319845242177 * 50*16.5).toFloat())
                tvTimeWidth = dip2px((14.160319845242177 * 50*16.5).toFloat() + 35F)
                llPatientNameNumberAgePadding = dip2px(100F)
                llPatientECGInfoContentWidth = dip2px(1500F)
                tvNameContentSize = sp2px(82F)
                tvLastNamePadding = dip2px(400F)
                tvECGReportNotes = sp2px(42F)
                tvECGReportNotesPadding = dip2px(1400F)
                tvPatientTitle = sp2px(54F)
                tvPatientNumber = sp2px(72F)
                tvPatientNumberPadding = dip2px(100F)
                ivGender = dip2px(100F)
                tvPatientAge = sp2px(52F)
                tvPatientBirthdayValuePadding = dip2px(100F)
                tvPatientAgeValuePadding = dip2px(100F)
                llPatientNameNumberAgePaddingHeight = dip2px(3F)
                ivHRIconWidthWidth = dip2px(100F)
                tvPatientECGInfoContent = sp2px(58F)
                tvPatientECGInfoContentPadding = dip2px(10F)
                tvPatientHRValuePadding = dip2px(40F)
                llPatientECGInfoContentPaddingWidth = dip2px(10F)
                llPatientECGInfoContentPaddingHeight = dip2px(28F)
                llReportMagnificationLayoutHeight = dip2px(350F)
                tvReportMagnificationChannelAC = sp2px(52F)
            }
            1 -> {
                if(density < 2) {
                    ivECGHeight = dip2px((14.160319845242177 * 36*16).toFloat())
                    ivECGWidth = dip2px((14.160319845242177 * 50*16).toFloat())
                    tvTimeWidth = dip2px((14.160319845242177 * 50*16).toFloat() + 35F)
                } else {
                    ivECGHeight = dip2px((14.160319845242177 * 36*16.3).toFloat())
                    ivECGWidth = dip2px((14.160319845242177 * 50*16.5).toFloat())
                    tvTimeWidth = dip2px((14.160319845242177 * 50*16.5).toFloat() + 35F)
                }
                llPatientNameNumberAgePadding = dip2px(100F)
                llPatientECGInfoContentWidth = dip2px(1500F)
                tvNameContentSize = sp2px(82F)
                tvLastNamePadding = dip2px(400F)
                tvECGReportNotes = sp2px(42F)
                tvECGReportNotesPadding = dip2px(1400F)
                tvPatientTitle = sp2px(54F)
                tvPatientNumber = sp2px(72F)
                tvPatientNumberPadding = dip2px(100F)
                ivGender = dip2px(100F)
                tvPatientAge = sp2px(52F)
                tvPatientBirthdayValuePadding = dip2px(100F)
                tvPatientAgeValuePadding = dip2px(100F)
                llPatientNameNumberAgePaddingHeight = dip2px(3F)
                ivHRIconWidthWidth = dip2px(100F)
                tvPatientECGInfoContent = sp2px(58F)
                tvPatientECGInfoContentPadding = dip2px(10F)
                tvPatientHRValuePadding = dip2px(40F)
                llPatientECGInfoContentPaddingWidth = dip2px(10F)
                llPatientECGInfoContentPaddingHeight = dip2px(28F)
                llReportMagnificationLayoutHeight = dip2px(350F)
                tvReportMagnificationChannelAC = sp2px(52F)
            }
            2 -> {
                ivECGHeight = dip2px((14.160319845242177 * 25*3.6).toFloat())
                ivECGWidth = dip2px((14.160319845242177 * 50*3.52).toFloat())
                tvTimeWidth = dip2px((14.160319845242177 * 50*3.52).toFloat() + 35F)
                llPatientNameNumberAgePadding = dip2px(50F)
                llPatientECGInfoContentWidth = dip2px(360F)
                tvNameContentSize = sp2px(24F)
                tvLastNamePadding = dip2px(200F)
                tvECGReportNotes = sp2px(14F)
                tvECGReportNotesPadding = dip2px(600F)
                tvPatientTitle = sp2px(18F)
                tvPatientNumber = sp2px(24F)
                tvPatientNumberPadding = dip2px(50F)
                ivGender = dip2px(45F)
                tvPatientAge = sp2px(20F)
                tvPatientBirthdayValuePadding = dip2px(50F)
                tvPatientAgeValuePadding = dip2px(50F)
                llPatientNameNumberAgePaddingHeight = dip2px(3F)
                ivHRIconWidthWidth = dip2px(45F)
                tvPatientECGInfoContent = sp2px(16F)
                tvPatientECGInfoContentPadding = dip2px(5F)
                tvPatientHRValuePadding = dip2px(20F)
                llPatientECGInfoContentPaddingWidth = dip2px(5F)
                llPatientECGInfoContentPaddingHeight = dip2px(7F)
                llReportMagnificationLayoutHeight = dip2px(100F)
                tvReportMagnificationChannelAC = sp2px(14F)
            }
        }

        //first & last name & ECG notes
        val nameContent = LinearLayout(context)
        nameContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        nameContent.orientation = LinearLayout.HORIZONTAL
        nameContent.setPadding(llPatientNameNumberAgePadding, llPatientNameNumberAgePaddingHeight, 0, 0)
        val tv_first_name = TextView(context)
        val tv_last_name = TextView(context)
        val tv_ecg_report_notes = TextView(context)
        val tv_first_name_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        val tv_last_name_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_first_name.setLayoutParams(tv_first_name_params)
        tv_first_name.setText(patientInfo.firstName)
        tv_first_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvNameContentSize)
        tv_last_name.setLayoutParams(tv_last_name_params)
        tv_last_name.setText(patientInfo.lastName)
        tv_last_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvNameContentSize)
        tv_last_name.setPadding(tvLastNamePadding, 0, 0, 0)
        tv_ecg_report_notes.setLayoutParams(LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT))
        tv_ecg_report_notes.setText(patientInfo.ecgReportNotesValue)
        tv_ecg_report_notes.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvECGReportNotes)
        tv_ecg_report_notes.setPadding(tvECGReportNotesPadding, 10, 0, 0)
        tv_ecg_report_notes.maxLines = 1
        nameContent.removeAllViews()
        nameContent.addView(tv_first_name)
        nameContent.addView(tv_last_name)
        nameContent.addView(tv_ecg_report_notes)

        //patient title & number
        val patientNumberContent = LinearLayout(context)
        patientNumberContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientNumberContent.orientation = LinearLayout.HORIZONTAL
        patientNumberContent.setPadding(llPatientNameNumberAgePadding, llPatientNameNumberAgePaddingHeight, 0, 0)
        val tv_patient_title = TextView(context)
        val tv_patient_number = TextView(context)
        val tv_patient_title_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        val tv_patient_number_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_patient_title.setLayoutParams(tv_patient_title_params)
        tv_patient_title.setText(patientInfo.patientNumberTitleValue)
        tv_patient_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, tvPatientTitle)
        tv_patient_number.setLayoutParams(tv_patient_number_params)
        tv_patient_number.setText(patientInfo.patientNumberValue)
        tv_patient_number.setTextSize(TypedValue.COMPLEX_UNIT_SP, tvPatientNumber)
        tv_patient_number.setPadding(tvPatientNumberPadding, 0, 0, 0)
        tv_patient_number.setTypeface(null, Typeface.BOLD)
        patientNumberContent.removeAllViews()
        patientNumberContent.addView(tv_patient_title)
        patientNumberContent.addView(tv_patient_number)

        //patient gender & age & birthday
        val patientGenderAgeBirthdayContent = LinearLayout(context)
        patientGenderAgeBirthdayContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientGenderAgeBirthdayContent.orientation = LinearLayout.HORIZONTAL
        patientGenderAgeBirthdayContent.gravity = Gravity.CENTER_VERTICAL
        patientGenderAgeBirthdayContent.setPadding(llPatientNameNumberAgePadding, llPatientNameNumberAgePaddingHeight, 0, 0)
        val iv_gender = ImageView(context)
        val iv_gender_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        iv_gender.setLayoutParams(iv_gender_params)
        when(grid) {
            1 -> {
                iv_gender.setImageResource(R.mipmap.male)
            }
            2 -> {
                iv_gender.setImageResource(R.mipmap.female)
            }
            3 -> {
                iv_gender.setImageResource(R.mipmap.other)
            }
        }
        iv_gender.getLayoutParams().height = ivGender
        iv_gender.getLayoutParams().width = ivGender
        val tv_patient_age_value = TextView(context)
        val tv_patient_birthday_value = TextView(context)
        val tv_patient_age_birthday_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_patient_age_value.setLayoutParams(tv_patient_age_birthday_params)
        tv_patient_age_value.setText(patientInfo.patientAgeTitleValue + patientInfo.patientAgeValue)
        tv_patient_age_value.setTextSize(TypedValue.COMPLEX_UNIT_SP, tvPatientAge)
        tv_patient_age_value.setPadding(tvPatientAgeValuePadding, 0, 0, 0)
        tv_patient_birthday_value.setLayoutParams(tv_patient_age_birthday_params)
        tv_patient_birthday_value.setText(patientInfo.patientBirthdayTitleValue + patientInfo.patientBirthdayValue)
        tv_patient_birthday_value.setTextSize(TypedValue.COMPLEX_UNIT_SP, tvPatientAge)
        tv_patient_birthday_value.setPadding(tvPatientBirthdayValuePadding, 0, 0, 0)
        patientGenderAgeBirthdayContent.removeAllViews()
        patientGenderAgeBirthdayContent.addView(iv_gender)
        patientGenderAgeBirthdayContent.addView(tv_patient_age_value)
        patientGenderAgeBirthdayContent.addView(tv_patient_birthday_value)

        //patient ecg data & info
        val patientECGDataInfoContent = LinearLayout(context)
        patientECGDataInfoContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientECGDataInfoContent.orientation = LinearLayout.HORIZONTAL
        patientECGDataInfoContent.setPadding(3, llPatientNameNumberAgePaddingHeight, 0, 0)

        //patient ecg data
        val iv_ECG_data = ImageView(context)
        val iv_ECG_data_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        iv_ECG_data.setLayoutParams(iv_ECG_data_params)
        iv_ECG_data.setImageBitmap(bmp)
        iv_ECG_data.getLayoutParams().height = ivECGHeight
        iv_ECG_data.getLayoutParams().width = ivECGWidth
        //調整大小，可以決定格子大小，這尺寸專門for ecg 圖表使用
        //Log.d(TAG,"width: " + iv_ECG_data.width + " height: " + iv_ECG_data.height)
//        Log.d(TAG,"width: " + (14.160319845242177 * 25).toInt() + " height: " + (14.160319845242177 * 50).toInt())

        //patient ecg info
        val patientECGInfoContent = LinearLayout(context)
        patientECGInfoContent.layoutParams = LinearLayout.LayoutParams(llPatientECGInfoContentWidth,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientECGInfoContent.orientation = LinearLayout.VERTICAL

        //patient HR XXX bpm
        val patientHRContent = LinearLayout(context)
        patientHRContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientHRContent.orientation = LinearLayout.HORIZONTAL
        patientHRContent.gravity = Gravity.CENTER_VERTICAL
        val iv_hr_icon = ImageView(context)
        val iv_hr_icon_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        iv_hr_icon.setLayoutParams(iv_hr_icon_params)
        iv_hr_icon.setImageResource(R.mipmap.heart)
        iv_hr_icon.getLayoutParams().height = ivHRIconWidthWidth
        iv_hr_icon.getLayoutParams().width = ivHRIconWidthWidth
        val tv_patient_hr_value = TextView(context)
        val tv_patient_hr_value_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_patient_hr_value.setLayoutParams(tv_patient_hr_value_params)
        tv_patient_hr_value.setText(patientHRTitleValue + "  " + patientInfo.patientHRValue + "  " + patientHRUnitValue)
        tv_patient_hr_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_hr_value.setPadding(tvPatientHRValuePadding, 0, 0, 0)
        patientHRContent.addView(iv_hr_icon)
        patientHRContent.addView(tv_patient_hr_value)

        val tv_patient_info_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)

        //patient P dur XXX ms
        val patientPDurContent = LinearLayout(context)
        patientPDurContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientPDurContent.orientation = LinearLayout.HORIZONTAL
        patientPDurContent.gravity = Gravity.LEFT
        val patientPDurValueContent = LinearLayout(context)
        patientPDurValueContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientPDurValueContent.orientation = LinearLayout.HORIZONTAL
        patientPDurValueContent.gravity = Gravity.RIGHT
        val tv_patient_p_dur_title = TextView(context)
        val tv_patient_p_dur_value = TextView(context)
        val tv_patient_p_dur_unit = TextView(context)
        tv_patient_p_dur_title.setLayoutParams(tv_patient_info_params)
        tv_patient_p_dur_title.setText("P dur ")
        tv_patient_p_dur_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_p_dur_value.setLayoutParams(tv_patient_info_params)
        tv_patient_p_dur_value.setText(patientInfo.pDurValue.toString())
        tv_patient_p_dur_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        //tv_patient_p_dur_value.setPadding(15, 0, 0, 0)
        tv_patient_p_dur_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_p_dur_unit.setText("ms")
        tv_patient_p_dur_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_p_dur_unit.setPadding(tvPatientECGInfoContentPadding, 0, 0, 0)
        patientPDurContent.addView(tv_patient_p_dur_title)
        patientPDurValueContent.addView(tv_patient_p_dur_value)
        patientPDurValueContent.addView(tv_patient_p_dur_unit)
        patientPDurContent.addView(patientPDurValueContent)
        patientPDurContent.setBackgroundResource(R.drawable.underline)

        //patient PR int XXX ms
        val patientPRIntContent = LinearLayout(context)
        patientPRIntContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientPRIntContent.orientation = LinearLayout.HORIZONTAL
        patientPRIntContent.gravity = Gravity.LEFT
        val patientPRIntValueContent = LinearLayout(context)
        patientPRIntValueContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientPRIntValueContent.orientation = LinearLayout.HORIZONTAL
        patientPRIntValueContent.gravity = Gravity.RIGHT
        val tv_patient_pr_int_title = TextView(context)
        val tv_patient_pr_int_value = TextView(context)
        val tv_patient_pr_int_unit = TextView(context)
        tv_patient_pr_int_title.setLayoutParams(tv_patient_info_params)
        tv_patient_pr_int_title.setText("PR int ")
        tv_patient_pr_int_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_pr_int_value.setLayoutParams(tv_patient_info_params)
        tv_patient_pr_int_value.setText(patientInfo.pRIntValue.toString())
        tv_patient_pr_int_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        //tv_patient_pr_int_value.setPadding(15, 0, 0, 0)
        tv_patient_pr_int_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_pr_int_unit.setText("ms")
        tv_patient_pr_int_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_pr_int_unit.setPadding(tvPatientECGInfoContentPadding, 0, 0, 0)
        patientPRIntContent.addView(tv_patient_pr_int_title)
        patientPRIntValueContent.addView(tv_patient_pr_int_value)
        patientPRIntValueContent.addView(tv_patient_pr_int_unit)
        patientPRIntContent.addView(patientPRIntValueContent)
        patientPRIntContent.setBackgroundResource(R.drawable.underline)

        //patient QRS dur XXX ms
        val patientQRSDurContent = LinearLayout(context)
        patientQRSDurContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientQRSDurContent.orientation = LinearLayout.HORIZONTAL
        patientQRSDurContent.gravity = Gravity.LEFT
        val patientQRSDurValueContent = LinearLayout(context)
        patientQRSDurValueContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientQRSDurValueContent.orientation = LinearLayout.HORIZONTAL
        patientQRSDurValueContent.gravity = Gravity.RIGHT
        val tv_patient_qrs_dur_title = TextView(context)
        val tv_patient_qrs_dur_value = TextView(context)
        val tv_patient_qrs_dur_unit = TextView(context)
        tv_patient_qrs_dur_title.setLayoutParams(tv_patient_info_params)
        tv_patient_qrs_dur_title.setText("QRS dur ")
        tv_patient_qrs_dur_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_qrs_dur_value.setLayoutParams(tv_patient_info_params)
        tv_patient_qrs_dur_value.setText(patientInfo.qRSDurValue.toString())
        tv_patient_qrs_dur_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        //tv_patient_qrs_dur_value.setPadding(15, 0, 0, 0)
        tv_patient_qrs_dur_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_qrs_dur_unit.setText("ms")
        tv_patient_qrs_dur_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_qrs_dur_unit.setPadding(tvPatientECGInfoContentPadding, 0, 0, 0)
        patientQRSDurContent.addView(tv_patient_qrs_dur_title)
        patientQRSDurValueContent.addView(tv_patient_qrs_dur_value)
        patientQRSDurValueContent.addView(tv_patient_qrs_dur_unit)
        patientQRSDurContent.addView(patientQRSDurValueContent)
        patientQRSDurContent.setBackgroundResource(R.drawable.underline)

        //patient QT int XXX ms
        val patientQTIntContent = LinearLayout(context)
        patientQTIntContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientQTIntContent.orientation = LinearLayout.HORIZONTAL
        patientQTIntContent.gravity = Gravity.LEFT
        val patientQTIntValueContent = LinearLayout(context)
        patientQTIntValueContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientQTIntValueContent.orientation = LinearLayout.HORIZONTAL
        patientQTIntValueContent.gravity = Gravity.RIGHT
        val tv_patient_qt_int_title = TextView(context)
        val tv_patient_qt_int_value = TextView(context)
        val tv_patient_qt_int_unit = TextView(context)
        tv_patient_qt_int_title.setLayoutParams(tv_patient_info_params)
        tv_patient_qt_int_title.setText("QT int ")
        tv_patient_qt_int_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_qt_int_value.setLayoutParams(tv_patient_info_params)
        tv_patient_qt_int_value.setText(patientInfo.qTIntValue.toString())
        tv_patient_qt_int_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        //tv_patient_qt_int_value.setPadding(15, 0, 0, 0)
        tv_patient_qt_int_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_qt_int_unit.setText("ms")
        tv_patient_qt_int_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_qt_int_unit.setPadding(tvPatientECGInfoContentPadding, 0, 0, 0)
        patientQTIntContent.addView(tv_patient_qt_int_title)
        patientQTIntValueContent.addView(tv_patient_qt_int_value)
        patientQTIntValueContent.addView(tv_patient_qt_int_unit)
        patientQTIntContent.addView(patientQTIntValueContent)
        patientQTIntContent.setBackgroundResource(R.drawable.underline)

        //patient QTc int XXX ms
        val patientQTcIntContent = LinearLayout(context)
        patientQTcIntContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientQTcIntContent.orientation = LinearLayout.HORIZONTAL
        patientQTcIntContent.gravity = Gravity.LEFT
        val patientQTcIntValueContent = LinearLayout(context)
        patientQTcIntValueContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientQTcIntValueContent.orientation = LinearLayout.HORIZONTAL
        patientQTcIntValueContent.gravity = Gravity.RIGHT
        val tv_patient_qtc_int_title = TextView(context)
        val tv_patient_qtc_int_value = TextView(context)
        val tv_patient_qtc_int_unit = TextView(context)
        tv_patient_qtc_int_title.setLayoutParams(tv_patient_info_params)
        tv_patient_qtc_int_title.setText("QTc int ")
        tv_patient_qtc_int_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_qtc_int_value.setLayoutParams(tv_patient_info_params)
        tv_patient_qtc_int_value.setText(patientInfo.qTcIntValue.toString())
        tv_patient_qtc_int_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        //tv_patient_qtc_int_value.setPadding(15, 0, 0, 0)
        tv_patient_qtc_int_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_qtc_int_unit.setText("ms")
        tv_patient_qtc_int_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_qtc_int_unit.setPadding(tvPatientECGInfoContentPadding, 0, 0, 0)
        patientQTcIntContent.addView(tv_patient_qtc_int_title)
        patientQTcIntValueContent.addView(tv_patient_qtc_int_value)
        patientQTcIntValueContent.addView(tv_patient_qtc_int_unit)
        patientQTcIntContent.addView(patientQTcIntValueContent)
        patientQTcIntContent.setBackgroundResource(R.drawable.underline)

        //patient P axis XXX deg
        val patientPAxisContent = LinearLayout(context)
        patientPAxisContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientPAxisContent.orientation = LinearLayout.HORIZONTAL
        patientPAxisContent.gravity = Gravity.LEFT
        val patientPAxisValueContent = LinearLayout(context)
        patientPAxisValueContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientPAxisValueContent.orientation = LinearLayout.HORIZONTAL
        patientPAxisValueContent.gravity = Gravity.RIGHT
        val tv_patient_p_axis_title = TextView(context)
        val tv_patient_p_axis_value = TextView(context)
        val tv_patient_p_axis_unit = TextView(context)
        tv_patient_p_axis_title.setLayoutParams(tv_patient_info_params)
        tv_patient_p_axis_title.setText("P axis ")
        tv_patient_p_axis_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_p_axis_value.setLayoutParams(tv_patient_info_params)
        tv_patient_p_axis_value.setText(patientInfo.pAxisValue.toString())
        tv_patient_p_axis_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        //tv_patient_p_axis_value.setPadding(15, 0, 0, 0)
        tv_patient_p_axis_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_p_axis_unit.setText("deg")
        tv_patient_p_axis_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_p_axis_unit.setPadding(tvPatientECGInfoContentPadding, 0, 0, 0)
        patientPAxisContent.addView(tv_patient_p_axis_title)
        patientPAxisValueContent.addView(tv_patient_p_axis_value)
        patientPAxisValueContent.addView(tv_patient_p_axis_unit)
        patientPAxisContent.addView(patientPAxisValueContent)
        patientPAxisContent.setBackgroundResource(R.drawable.underline)

        //patient QRS axis XXX deg
        val patientQRSAxisContent = LinearLayout(context)
        patientQRSAxisContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientQRSAxisContent.orientation = LinearLayout.HORIZONTAL
        patientQRSAxisContent.gravity = Gravity.LEFT
        val patientQRSAxisValueContent = LinearLayout(context)
        patientQRSAxisValueContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientQRSAxisValueContent.orientation = LinearLayout.HORIZONTAL
        patientQRSAxisValueContent.gravity = Gravity.RIGHT
        val tv_patient_qrs_axis_title = TextView(context)
        val tv_patient_qrs_axis_value = TextView(context)
        val tv_patient_qrs_axis_unit = TextView(context)
        tv_patient_qrs_axis_title.setLayoutParams(tv_patient_info_params)
        tv_patient_qrs_axis_title.setText("QRS axis ")
        tv_patient_qrs_axis_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_qrs_axis_value.setLayoutParams(tv_patient_info_params)
        tv_patient_qrs_axis_value.setText(patientInfo.qRSAxisValue.toString())
        tv_patient_qrs_axis_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        //tv_patient_qrs_axis_value.setPadding(15, 0, 0, 0)
        tv_patient_qrs_axis_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_qrs_axis_unit.setText("deg")
        tv_patient_qrs_axis_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_qrs_axis_unit.setPadding(tvPatientECGInfoContentPadding, 0, 0, 0)
        patientQRSAxisContent.addView(tv_patient_qrs_axis_title)
        patientQRSAxisValueContent.addView(tv_patient_qrs_axis_value)
        patientQRSAxisValueContent.addView(tv_patient_qrs_axis_unit)
        patientQRSAxisContent.addView(patientQRSAxisValueContent)
        patientQRSAxisContent.setBackgroundResource(R.drawable.underline)

        //patient T axis XXX deg
        val patientTAxisContent = LinearLayout(context)
        patientTAxisContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientTAxisContent.orientation = LinearLayout.HORIZONTAL
        patientTAxisContent.gravity = Gravity.LEFT
        val patientTAxisValueContent = LinearLayout(context)
        patientTAxisValueContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientTAxisValueContent.orientation = LinearLayout.HORIZONTAL
        patientTAxisValueContent.gravity = Gravity.RIGHT
        val tv_patient_t_axis_title = TextView(context)
        val tv_patient_t_axis_value = TextView(context)
        val tv_patient_t_axis_unit = TextView(context)
        tv_patient_t_axis_title.setLayoutParams(tv_patient_info_params)
        tv_patient_t_axis_title.setText("T axis ")
        tv_patient_t_axis_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_t_axis_value.setLayoutParams(tv_patient_info_params)
        tv_patient_t_axis_value.setText(patientInfo.tAxisValue.toString())
        tv_patient_t_axis_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        //tv_patient_t_axis_value.setPadding(15, 0, 0, 0)
        tv_patient_t_axis_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_t_axis_unit.setText("deg")
        tv_patient_t_axis_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_t_axis_unit.setPadding(tvPatientECGInfoContentPadding, 0, 0, 0)
        patientTAxisContent.addView(tv_patient_t_axis_title)
        patientTAxisValueContent.addView(tv_patient_t_axis_value)
        patientTAxisValueContent.addView(tv_patient_t_axis_unit)
        patientTAxisContent.addView(patientTAxisValueContent)
        patientTAxisContent.setBackgroundResource(R.drawable.underline)

        //patient RV5+SV1 XXX mv
        val patientRv5Sv1Content = LinearLayout(context)
        patientRv5Sv1Content.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientRv5Sv1Content.orientation = LinearLayout.HORIZONTAL
        patientRv5Sv1Content.gravity = Gravity.LEFT
        val patientRv5Sv1ValueContent = LinearLayout(context)
        patientRv5Sv1ValueContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientRv5Sv1ValueContent.orientation = LinearLayout.HORIZONTAL
        patientRv5Sv1ValueContent.gravity = Gravity.RIGHT
        val tv_patient_rv_sv_title = TextView(context)
        val tv_patient_rv_sv_value = TextView(context)
        val tv_patient_rv_sv_unit = TextView(context)
        tv_patient_rv_sv_title.setLayoutParams(tv_patient_info_params)
        tv_patient_rv_sv_title.setText("RV5+SV1 ")
        tv_patient_rv_sv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_rv_sv_value.setLayoutParams(tv_patient_info_params)
        tv_patient_rv_sv_value.setText(patientInfo.rV5SV1Value.toString())
        tv_patient_rv_sv_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        //tv_patient_rv_sv_value.setPadding(15, 0, 0, 0)
        tv_patient_rv_sv_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_rv_sv_unit.setText("mv")
        tv_patient_rv_sv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        tv_patient_rv_sv_unit.setPadding(tvPatientECGInfoContentPadding, 0, 0, 0)
        patientRv5Sv1Content.addView(tv_patient_rv_sv_title)
        patientRv5Sv1ValueContent.addView(tv_patient_rv_sv_value)
        patientRv5Sv1ValueContent.addView(tv_patient_rv_sv_unit)
        patientRv5Sv1Content.addView(patientRv5Sv1ValueContent)
        patientRv5Sv1Content.setBackgroundResource(R.drawable.underline)

        //增益
        val reportMagnificationLayout = RelativeLayout(context)
        reportMagnificationLayout.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT)
        val reportMagnificationContent = LinearLayout(context)
        reportMagnificationContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        reportMagnificationContent.orientation = LinearLayout.HORIZONTAL
        reportMagnificationContent.gravity = Gravity.CENTER_VERTICAL
        val tv_report_magnification = TextView(context)
        tv_report_magnification.setLayoutParams(tv_patient_info_params)
        tv_report_magnification.setText(patientInfo.reportMagnificationValue)
        tv_report_magnification.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvReportMagnificationChannelAC)
        reportMagnificationContent.setBackgroundResource(R.drawable.aroundshap)
        reportMagnificationContent.addView(tv_report_magnification)
        reportMagnificationLayout.addView(reportMagnificationContent)

        //低通 高通 交流電
        val channelACLayout = RelativeLayout(context)
        channelACLayout.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT)
        val channelACContent = LinearLayout(context)
        channelACContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        channelACContent.orientation = LinearLayout.VERTICAL
        channelACContent.gravity = Gravity.CENTER_VERTICAL
        val tv_low_channel = TextView(context)
        val tv_high_channel = TextView(context)
        val tv_ac = TextView(context)
        tv_low_channel.setLayoutParams(tv_patient_info_params)
        tv_low_channel.setText(patientInfo.lowChannelValue)
        tv_low_channel.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvReportMagnificationChannelAC)
        tv_high_channel.setLayoutParams(tv_patient_info_params)
        tv_high_channel.setText(patientInfo.highChannelValue)
        tv_high_channel.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvReportMagnificationChannelAC)
        tv_ac.setLayoutParams(tv_patient_info_params)
        tv_ac.setText(patientInfo.acChannelValue)
        tv_ac.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvReportMagnificationChannelAC)
        channelACContent.setBackgroundResource(R.drawable.aroundshap)
        channelACContent.addView(tv_low_channel)
        channelACContent.addView(tv_high_channel)
        channelACContent.addView(tv_ac)
        channelACLayout.addView(channelACContent)

        //心律調整器偵測
        val hrDetectLayout = RelativeLayout(context)
        hrDetectLayout.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT)
        val hrDetectContent = LinearLayout(context)
        hrDetectContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        hrDetectContent.orientation = LinearLayout.HORIZONTAL
        hrDetectContent.gravity = Gravity.CENTER_VERTICAL
        val tv_hr_detect = TextView(context)
        tv_hr_detect.setLayoutParams(tv_patient_info_params)
        tv_hr_detect.setText(patientInfo.hrDetectValue)
        tv_hr_detect.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvReportMagnificationChannelAC)
        hrDetectContent.setBackgroundResource(R.drawable.aroundshap)
        hrDetectContent.addView(tv_hr_detect)
        hrDetectLayout.addView(hrDetectContent)

        patientECGInfoContent.removeAllViews()
        patientECGInfoContent.addView(patientHRContent)
        patientPDurContent.setPadding(llPatientECGInfoContentPaddingWidth, llPatientECGInfoContentPaddingHeight, 0, 0)
        patientECGInfoContent.addView(patientPDurContent)
        patientPRIntContent.setPadding(llPatientECGInfoContentPaddingWidth, llPatientECGInfoContentPaddingHeight, 0, 0)
        patientECGInfoContent.addView(patientPRIntContent)
        patientQRSDurContent.setPadding(llPatientECGInfoContentPaddingWidth, llPatientECGInfoContentPaddingHeight, 0, 0)
        patientECGInfoContent.addView(patientQRSDurContent)
        patientQTIntContent.setPadding(llPatientECGInfoContentPaddingWidth, llPatientECGInfoContentPaddingHeight, 0, 0)
        patientECGInfoContent.addView(patientQTIntContent)
        patientQTcIntContent.setPadding(llPatientECGInfoContentPaddingWidth, llPatientECGInfoContentPaddingHeight, 0, 0)
        patientECGInfoContent.addView(patientQTcIntContent)
        patientPAxisContent.setPadding(llPatientECGInfoContentPaddingWidth, llPatientECGInfoContentPaddingHeight, 0, 0)
        patientECGInfoContent.addView(patientPAxisContent)
        patientQRSAxisContent.setPadding(llPatientECGInfoContentPaddingWidth, llPatientECGInfoContentPaddingHeight, 0, 0)
        patientECGInfoContent.addView(patientQRSAxisContent)
        patientTAxisContent.setPadding(llPatientECGInfoContentPaddingWidth, llPatientECGInfoContentPaddingHeight, 0, 0)
        patientECGInfoContent.addView(patientTAxisContent)
        patientRv5Sv1Content.setPadding(llPatientECGInfoContentPaddingWidth, llPatientECGInfoContentPaddingHeight, 0, 0)
        patientECGInfoContent.addView(patientRv5Sv1Content)
        reportMagnificationLayout.setPadding(llPatientECGInfoContentPaddingWidth, 100, 0, 0)
        patientECGInfoContent.addView(reportMagnificationLayout)
        channelACLayout.setPadding(llPatientECGInfoContentPaddingWidth, llPatientECGInfoContentPaddingHeight, 0, 0)
        patientECGInfoContent.addView(channelACLayout)
        hrDetectLayout.setPadding(llPatientECGInfoContentPaddingWidth, llPatientECGInfoContentPaddingHeight, 0, 0)
        patientECGInfoContent.addView(hrDetectLayout)

        patientECGDataInfoContent.removeAllViews()
        patientECGDataInfoContent.addView(iv_ECG_data)
        patientECGDataInfoContent.addView(patientECGInfoContent)

        //generate time
        val generateTimeContent = LinearLayout(context)
        generateTimeContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        generateTimeContent.orientation = LinearLayout.HORIZONTAL
        generateTimeContent.setPadding(tvTimeWidth, 550, 10, 10)
        val tv_generateTime = TextView(context)
        val tv_generateTime_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_generateTime.setLayoutParams(tv_generateTime_params)
        tv_generateTime.setText("2021.12.03 18:19:00")
        tv_generateTime.setTextSize(TypedValue.COMPLEX_UNIT_SP,tvPatientECGInfoContent)
        generateTimeContent.removeAllViews()
        generateTimeContent.addView(tv_generateTime)

        parent.removeAllViews()
        parent.addView(nameContent)
        parent.addView(patientNumberContent)
        parent.addView(patientGenderAgeBirthdayContent)
        parent.addView(patientECGDataInfoContent)
        parent.addView(generateTimeContent)

        return parent
    }

    private fun dip2px(dipValue: Float): Int {
        var density = Resources.getSystem().displayMetrics.density
//        if(density == 1.3312501F) {
//            density = 2.125F
//        }
        return (dipValue * density + 0.5f).toInt()
    }

    private fun sp2px(spValue: Float): Float {
        var density = Resources.getSystem().displayMetrics.scaledDensity
//        if(density == 1.3312501F) {
//            density = 2.125F
//        }
        return (spValue * density + 0.5f)
    }

}