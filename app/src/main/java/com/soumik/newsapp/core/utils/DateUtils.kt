package com.soumik.newsapp.core.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    /**
     * provides the date in the formatted way
     * App format = dd-MM-yyyy
     * @param format    the format of the given string
     * @param inputDate given date
     * @return  formatted date
     */
    fun provideFormattedDate(format:String,inputDate:String?) : String {
        return try {
            val inputFormat = SimpleDateFormat(format, Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH)
            val date = inputFormat.parse(inputDate!!)
            Log.d("TAG", "provideFormattedDate: ${outputFormat.format(date!!)}")
            outputFormat.format(date!!)
        } catch (e: Exception) {
            e.stackTrace
            ""
        }
    }
}