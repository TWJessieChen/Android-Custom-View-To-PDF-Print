package com.jc666.customviewtopdfprint

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class GenerateECGReportView(context: Context) {
    private val TAG = GenerateECGReportView::class.java.simpleName

    var context: Context

    private var firstNameValue = "JC"
    private var lastNameValue = "666"
    private var patientNumberTitleValue = "病歷號碼"
    private var patientNumberValue = "GHGFVJ654D563FG7"
    private var patientAgeTitleValue = "年齡: "
    private var patientAgeValue = "35"
    private var patientBirthdayTitleValue = "生日 "
    private var patientBirthdayValue = "1986.04.22"
    private var ecgReportNotesValue = "Rhythm: sinusrhythm with tachycardia. QRST Evaluation: indeterminate ECG. Summary: abnormal ECG."
    private var patientHRTitleValue = "HR "
    private var patientHRUnitValue = " bpm"


    init {
        this.context = context
    }

    fun createECGLayout() : View {
        val parent = LinearLayout(context)
        parent.layoutParams = LinearLayout.LayoutParams(842, 595)
        parent.orientation = LinearLayout.VERTICAL

        //first & last name & ECG notes
        val nameContent = LinearLayout(context)
        nameContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        nameContent.orientation = LinearLayout.HORIZONTAL
        nameContent.setPadding(15, 10, 0, 0)
        val tv_first_name = TextView(context)
        val tv_last_name = TextView(context)
        val tv_ecg_report_notes = TextView(context)
        val tv_first_name_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        val tv_last_name_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_first_name.setLayoutParams(tv_first_name_params)
        tv_first_name.setText(firstNameValue)
        tv_first_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14F)
        tv_last_name.setLayoutParams(tv_last_name_params)
        tv_last_name.setText(lastNameValue)
        tv_last_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14F)
        tv_last_name.setPadding(25, 0, 0, 0)
        tv_ecg_report_notes.setLayoutParams(LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT))
        tv_ecg_report_notes.setText(ecgReportNotesValue)
        tv_ecg_report_notes.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_ecg_report_notes.setPadding(200, 10, 0, 0)
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
        patientNumberContent.setPadding(15, 5, 0, 0)
        val tv_patient_title = TextView(context)
        val tv_patient_number = TextView(context)
        val tv_patient_title_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        val tv_patient_number_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_patient_title.setLayoutParams(tv_patient_title_params)
        tv_patient_title.setText(patientNumberTitleValue)
        tv_patient_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,14F)
        tv_patient_number.setLayoutParams(tv_patient_number_params)
        tv_patient_number.setText(patientNumberValue)
        tv_patient_number.setTextSize(TypedValue.COMPLEX_UNIT_SP,14F)
        tv_patient_number.setPadding(15, 0, 0, 0)
        tv_patient_number.setTypeface(null, Typeface.BOLD)
        patientNumberContent.removeAllViews()
        patientNumberContent.addView(tv_patient_title)
        patientNumberContent.addView(tv_patient_number)

        //patient gender & age & birthday
        val patientGenderAgeBirthdayContent = LinearLayout(context)
        patientGenderAgeBirthdayContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientGenderAgeBirthdayContent.orientation = LinearLayout.HORIZONTAL
        patientGenderAgeBirthdayContent.setPadding(15, 5, 0, 0)
        val iv_gender = ImageView(context)
        val iv_gender_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        iv_gender.setLayoutParams(iv_gender_params)
        iv_gender.setImageResource(R.mipmap.female)
        iv_gender.getLayoutParams().height = 30
        iv_gender.getLayoutParams().width = 36
        val tv_patient_age_value = TextView(context)
        val tv_patient_birthday_value = TextView(context)
        val tv_patient_age_birthday_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_patient_age_value.setLayoutParams(tv_patient_age_birthday_params)
        tv_patient_age_value.setText(patientAgeTitleValue + patientAgeValue)
        tv_patient_age_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,14F)
        tv_patient_age_value.setPadding(5, 0, 0, 0)
        tv_patient_birthday_value.setLayoutParams(tv_patient_age_birthday_params)
        tv_patient_birthday_value.setText(patientBirthdayTitleValue + patientBirthdayValue)
        tv_patient_birthday_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,14F)
        tv_patient_birthday_value.setPadding(15, 0, 0, 0)
        patientGenderAgeBirthdayContent.removeAllViews()
        patientGenderAgeBirthdayContent.addView(iv_gender)
        patientGenderAgeBirthdayContent.addView(tv_patient_age_value)
        patientGenderAgeBirthdayContent.addView(tv_patient_birthday_value)

        //patient ecg data & info
        val patientECGDataInfoContent = LinearLayout(context)
        patientECGDataInfoContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientECGDataInfoContent.orientation = LinearLayout.HORIZONTAL
        patientECGDataInfoContent.setPadding(15, 5, 0, 0)

        //patient ecg data
        val iv_ECG_data = ImageView(context)
        val iv_ECG_data_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        iv_ECG_data.setLayoutParams(iv_ECG_data_params)
        iv_ECG_data.setImageResource(R.mipmap.ecg_background)
//        iv_ECG_data.getLayoutParams().height = 30
//        iv_ECG_data.getLayoutParams().width = 36

        //patient ecg info
        val patientECGInfoContent = LinearLayout(context)
        patientECGInfoContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientECGInfoContent.orientation = LinearLayout.VERTICAL

        //patient HR XXX bpm
        val patientHRContent = LinearLayout(context)
        patientHRContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientHRContent.orientation = LinearLayout.HORIZONTAL
        val iv_hr_icon = ImageView(context)
        val iv_hr_icon_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        iv_hr_icon.setLayoutParams(iv_hr_icon_params)
        iv_hr_icon.setImageResource(R.mipmap.heart)
        iv_hr_icon.getLayoutParams().height = 25
        iv_hr_icon.getLayoutParams().width = 25
        val tv_patient_hr_value = TextView(context)
        val tv_patient_hr_value_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_patient_hr_value.setLayoutParams(tv_patient_hr_value_params)
        tv_patient_hr_value.setText(patientHRTitleValue + "   120   " + patientHRUnitValue)
        tv_patient_hr_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,10F)
        tv_patient_hr_value.setPadding(5, 0, 0, 0)
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
        val tv_patient_p_dur_title = TextView(context)
        val tv_patient_p_dur_value = TextView(context)
        val tv_patient_p_dur_unit = TextView(context)
        tv_patient_p_dur_title.setLayoutParams(tv_patient_info_params)
        tv_patient_p_dur_title.setText("P dur  ")
        tv_patient_p_dur_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_p_dur_value.setLayoutParams(tv_patient_info_params)
        tv_patient_p_dur_value.setText("102")
        tv_patient_p_dur_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_p_dur_value.setPadding(15, 0, 0, 0)
        tv_patient_p_dur_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_p_dur_unit.setText("ms")
        tv_patient_p_dur_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_p_dur_value.setPadding(5, 0, 0, 0)
        patientPDurContent.addView(tv_patient_p_dur_title)
        patientPDurContent.addView(tv_patient_p_dur_value)
        patientPDurContent.addView(tv_patient_p_dur_unit)
        patientPDurContent.setBackgroundResource(R.drawable.underline)

        //patient PR int XXX ms
        val patientPRIntContent = LinearLayout(context)
        patientPRIntContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientPRIntContent.orientation = LinearLayout.HORIZONTAL
        patientPRIntContent.gravity = Gravity.LEFT
        val tv_patient_pr_int_title = TextView(context)
        val tv_patient_pr_int_value = TextView(context)
        val tv_patient_pr_int_unit = TextView(context)
        tv_patient_pr_int_title.setLayoutParams(tv_patient_info_params)
        tv_patient_pr_int_title.setText("PR int ")
        tv_patient_pr_int_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_pr_int_value.setLayoutParams(tv_patient_info_params)
        tv_patient_pr_int_value.setText("102")
        tv_patient_pr_int_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_pr_int_value.setPadding(15, 0, 0, 0)
        tv_patient_pr_int_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_pr_int_unit.setText("ms")
        tv_patient_pr_int_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_pr_int_unit.setPadding(5, 0, 0, 0)
        patientPRIntContent.addView(tv_patient_pr_int_title)
        patientPRIntContent.addView(tv_patient_pr_int_value)
        patientPRIntContent.addView(tv_patient_pr_int_unit)
        patientPRIntContent.setBackgroundResource(R.drawable.underline)

        //patient QRS dur XXX ms
        val patientQRSDurContent = LinearLayout(context)
        patientQRSDurContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientQRSDurContent.orientation = LinearLayout.HORIZONTAL
        patientQRSDurContent.gravity = Gravity.LEFT
        val tv_patient_qrs_dur_title = TextView(context)
        val tv_patient_qrs_dur_value = TextView(context)
        val tv_patient_qrs_dur_unit = TextView(context)
        tv_patient_qrs_dur_title.setLayoutParams(tv_patient_info_params)
        tv_patient_qrs_dur_title.setText("QRS dur ")
        tv_patient_qrs_dur_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_qrs_dur_value.setLayoutParams(tv_patient_info_params)
        tv_patient_qrs_dur_value.setText("82")
        tv_patient_qrs_dur_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_qrs_dur_value.setPadding(15, 0, 0, 0)
        tv_patient_qrs_dur_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_qrs_dur_unit.setText("ms")
        tv_patient_qrs_dur_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_qrs_dur_unit.setPadding(5, 0, 0, 0)
        patientQRSDurContent.addView(tv_patient_qrs_dur_title)
        patientQRSDurContent.addView(tv_patient_qrs_dur_value)
        patientQRSDurContent.addView(tv_patient_qrs_dur_unit)
        patientQRSDurContent.setBackgroundResource(R.drawable.underline)

        //patient QT int XXX ms
        val patientQTIntContent = LinearLayout(context)
        patientQTIntContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientQTIntContent.orientation = LinearLayout.HORIZONTAL
        patientQTIntContent.gravity = Gravity.LEFT
        val tv_patient_qt_int_title = TextView(context)
        val tv_patient_qt_int_value = TextView(context)
        val tv_patient_qt_int_unit = TextView(context)
        tv_patient_qt_int_title.setLayoutParams(tv_patient_info_params)
        tv_patient_qt_int_title.setText("QT int ")
        tv_patient_qt_int_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_qt_int_value.setLayoutParams(tv_patient_info_params)
        tv_patient_qt_int_value.setText("482")
        tv_patient_qt_int_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_qt_int_value.setPadding(15, 0, 0, 0)
        tv_patient_qt_int_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_qt_int_unit.setText("ms")
        tv_patient_qt_int_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_qt_int_unit.setPadding(5, 0, 0, 0)
        patientQTIntContent.addView(tv_patient_qt_int_title)
        patientQTIntContent.addView(tv_patient_qt_int_value)
        patientQTIntContent.addView(tv_patient_qt_int_unit)
        patientQTIntContent.setBackgroundResource(R.drawable.underline)

        //patient QTc int XXX ms
        val patientQTcIntContent = LinearLayout(context)
        patientQTcIntContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientQTcIntContent.orientation = LinearLayout.HORIZONTAL
        patientQTcIntContent.gravity = Gravity.LEFT
        val tv_patient_qtc_int_title = TextView(context)
        val tv_patient_qtc_int_value = TextView(context)
        val tv_patient_qtc_int_unit = TextView(context)
        tv_patient_qtc_int_title.setLayoutParams(tv_patient_info_params)
        tv_patient_qtc_int_title.setText("QTc int ")
        tv_patient_qtc_int_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_qtc_int_value.setLayoutParams(tv_patient_info_params)
        tv_patient_qtc_int_value.setText("482")
        tv_patient_qtc_int_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_qtc_int_value.setPadding(15, 0, 0, 0)
        tv_patient_qtc_int_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_qtc_int_unit.setText("ms")
        tv_patient_qtc_int_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_qtc_int_unit.setPadding(5, 0, 0, 0)
        patientQTcIntContent.addView(tv_patient_qtc_int_title)
        patientQTcIntContent.addView(tv_patient_qtc_int_value)
        patientQTcIntContent.addView(tv_patient_qtc_int_unit)
        patientQTcIntContent.setBackgroundResource(R.drawable.underline)

        //patient P axis XXX deg
        val patientPAxisContent = LinearLayout(context)
        patientPAxisContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientPAxisContent.orientation = LinearLayout.HORIZONTAL
        patientPAxisContent.gravity = Gravity.LEFT
        val tv_patient_p_axis_title = TextView(context)
        val tv_patient_p_axis_value = TextView(context)
        val tv_patient_p_axis_unit = TextView(context)
        tv_patient_p_axis_title.setLayoutParams(tv_patient_info_params)
        tv_patient_p_axis_title.setText("P axis ")
        tv_patient_p_axis_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_p_axis_value.setLayoutParams(tv_patient_info_params)
        tv_patient_p_axis_value.setText("82")
        tv_patient_p_axis_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_p_axis_value.setPadding(15, 0, 0, 0)
        tv_patient_p_axis_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_p_axis_unit.setText("deg")
        tv_patient_p_axis_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_p_axis_unit.setPadding(5, 0, 0, 0)
        patientPAxisContent.addView(tv_patient_p_axis_title)
        patientPAxisContent.addView(tv_patient_p_axis_value)
        patientPAxisContent.addView(tv_patient_p_axis_unit)
        patientPAxisContent.setBackgroundResource(R.drawable.underline)

        //patient QRS axis XXX deg
        val patientQRSAxisContent = LinearLayout(context)
        patientQRSAxisContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientQRSAxisContent.orientation = LinearLayout.HORIZONTAL
        patientQRSAxisContent.gravity = Gravity.LEFT
        val tv_patient_qrs_axis_title = TextView(context)
        val tv_patient_qrs_axis_value = TextView(context)
        val tv_patient_qrs_axis_unit = TextView(context)
        tv_patient_qrs_axis_title.setLayoutParams(tv_patient_info_params)
        tv_patient_qrs_axis_title.setText("QRS axis ")
        tv_patient_qrs_axis_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_qrs_axis_value.setLayoutParams(tv_patient_info_params)
        tv_patient_qrs_axis_value.setText("82")
        tv_patient_qrs_axis_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_qrs_axis_value.setPadding(15, 0, 0, 0)
        tv_patient_qrs_axis_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_qrs_axis_unit.setText("deg")
        tv_patient_qrs_axis_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_qrs_axis_unit.setPadding(5, 0, 0, 0)
        patientQRSAxisContent.addView(tv_patient_qrs_axis_title)
        patientQRSAxisContent.addView(tv_patient_qrs_axis_value)
        patientQRSAxisContent.addView(tv_patient_qrs_axis_unit)
        patientQRSAxisContent.setBackgroundResource(R.drawable.underline)

        //patient T axis XXX deg
        val patientTAxisContent = LinearLayout(context)
        patientTAxisContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientTAxisContent.orientation = LinearLayout.HORIZONTAL
        patientTAxisContent.gravity = Gravity.LEFT
        val tv_patient_t_axis_title = TextView(context)
        val tv_patient_t_axis_value = TextView(context)
        val tv_patient_t_axis_unit = TextView(context)
        tv_patient_t_axis_title.setLayoutParams(tv_patient_info_params)
        tv_patient_t_axis_title.setText("T axis ")
        tv_patient_t_axis_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_t_axis_value.setLayoutParams(tv_patient_info_params)
        tv_patient_t_axis_value.setText("82")
        tv_patient_t_axis_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_t_axis_value.setPadding(15, 0, 0, 0)
        tv_patient_t_axis_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_t_axis_unit.setText("deg")
        tv_patient_t_axis_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_t_axis_unit.setPadding(5, 0, 0, 0)
        patientTAxisContent.addView(tv_patient_t_axis_title)
        patientTAxisContent.addView(tv_patient_t_axis_value)
        patientTAxisContent.addView(tv_patient_t_axis_unit)
        patientTAxisContent.setBackgroundResource(R.drawable.underline)

        //patient RV5+SV1 XXX mv
        val patientRv5Sv1Content = LinearLayout(context)
        patientRv5Sv1Content.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        patientRv5Sv1Content.orientation = LinearLayout.HORIZONTAL
        patientRv5Sv1Content.gravity = Gravity.LEFT
        val tv_patient_rv_sv_title = TextView(context)
        val tv_patient_rv_sv_value = TextView(context)
        val tv_patient_rv_sv_unit = TextView(context)
        tv_patient_rv_sv_title.setLayoutParams(tv_patient_info_params)
        tv_patient_rv_sv_title.setText("RV5+SV1 ")
        tv_patient_rv_sv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_rv_sv_value.setLayoutParams(tv_patient_info_params)
        tv_patient_rv_sv_value.setText("1.235")
        tv_patient_rv_sv_value.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_rv_sv_value.setPadding(15, 0, 0, 0)
        tv_patient_rv_sv_unit.setLayoutParams(tv_patient_info_params)
        tv_patient_rv_sv_unit.setText("mv")
        tv_patient_rv_sv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
        tv_patient_rv_sv_unit.setPadding(5, 0, 0, 0)
        patientRv5Sv1Content.addView(tv_patient_rv_sv_title)
        patientRv5Sv1Content.addView(tv_patient_rv_sv_value)
        patientRv5Sv1Content.addView(tv_patient_rv_sv_unit)
        patientRv5Sv1Content.setBackgroundResource(R.drawable.underline)



        patientECGInfoContent.removeAllViews()
        patientECGInfoContent.addView(patientHRContent)
        patientPDurContent.setPadding(5, 3, 0, 0)
        patientECGInfoContent.addView(patientPDurContent)
        patientPRIntContent.setPadding(5, 3, 0, 0)
        patientECGInfoContent.addView(patientPRIntContent)
        patientQRSDurContent.setPadding(5, 3, 0, 0)
        patientECGInfoContent.addView(patientQRSDurContent)
        patientQTIntContent.setPadding(5, 3, 0, 0)
        patientECGInfoContent.addView(patientQTIntContent)
        patientQTcIntContent.setPadding(5, 3, 0, 0)
        patientECGInfoContent.addView(patientQTcIntContent)
        patientPAxisContent.setPadding(5, 3, 0, 0)
        patientECGInfoContent.addView(patientPAxisContent)
        patientQRSAxisContent.setPadding(5, 3, 0, 0)
        patientECGInfoContent.addView(patientQRSAxisContent)
        patientTAxisContent.setPadding(5, 3, 0, 0)
        patientECGInfoContent.addView(patientTAxisContent)
        patientRv5Sv1Content.setPadding(5, 3, 0, 0)
        patientECGInfoContent.addView(patientRv5Sv1Content)


        patientECGDataInfoContent.removeAllViews()
        patientECGDataInfoContent.addView(iv_ECG_data)
        patientECGDataInfoContent.addView(patientECGInfoContent)



        //generate time
        val generateTimeContent = LinearLayout(context)
        generateTimeContent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        generateTimeContent.orientation = LinearLayout.HORIZONTAL
        generateTimeContent.setPadding(825, 550, 10, 10)
        val tv_generateTime = TextView(context)
        val tv_generateTime_params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        tv_generateTime.setLayoutParams(tv_generateTime_params)
        tv_generateTime.setText("2021.12.03 18:19:00")
        tv_generateTime.setTextSize(TypedValue.COMPLEX_UNIT_SP,8F)
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

    fun createXmlElement(title:String,description:String) : View{
        val parent = LinearLayout(context)
        parent.layoutParams = LinearLayout.LayoutParams(842, 595)

        parent.orientation = LinearLayout.VERTICAL



        //children of parent linearlayout
        val iv = ImageView(context)
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)

        lp.setMargins(0, 11, 7, 0)
        iv.setLayoutParams(lp)
        iv.setImageResource(R.mipmap.female)
        iv.getLayoutParams().height = 40
        iv.getLayoutParams().width = 46


        parent.addView(iv); // lo agregamos al layout

        val relativeP = RelativeLayout(context)
        relativeP.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT)

        val linearCH = LinearLayout(context)
        linearCH.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        // TextView1
        val tv1 = TextView(context)
        val lptv1 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)

        lptv1.setMargins(0, 7, 0, 0)

        tv1.setLayoutParams(lptv1)
        tv1.setText(title) // title
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP,25F)
        tv1.setTypeface(null, Typeface.BOLD)

        // TextView2
        val tv2 = TextView(context)
        val lptv2 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)

        lptv2.setMargins(0, 11, 7, 0)

        tv2.setLayoutParams(lptv1)
        tv2.setText(description) // description
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25F)
        tv2.setTypeface(null, Typeface.BOLD)

        linearCH.removeAllViews()
        linearCH.addView(tv1)
        linearCH.addView(tv2)

        relativeP.removeAllViews()
        relativeP.addView(linearCH)

        // last ImageView
        val iv2 = ImageView(context)
        val lpiv2 = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)

        lpiv2.setMargins(0, 11, 7, 0)
        lpiv2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        iv2.setLayoutParams(lpiv2)
        iv2.setImageResource(R.mipmap.female)
        iv2.getLayoutParams().height = 40
        iv2.getLayoutParams().width = 46


        parent.removeAllViews()
        parent.addView(iv)
        parent.addView(relativeP)
        parent.addView(iv2)

        return parent
    }









//    private fun dp2px(dp: Float): Int {
//        val density = resources.displayMetrics.density
//        return (dp * density + 0.5).toInt()
//    }
//
//    private fun sp2px(sp: Float): Int {
//        val density = resources.displayMetrics.scaledDensity
//        return (sp * density + 0.5).toInt()
//    }

}