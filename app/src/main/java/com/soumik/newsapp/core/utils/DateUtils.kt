package com.soumik.newsapp.core.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val TAG = "DateUtils"

    /**
     * provides the date in the formatted way
     * App format = dd-MM-yyyy // 23-03-2022
     * @param format    the format of the given string
     * @param inputDate given date
     * @return  formatted date
     */
    fun provideFormattedDate(format:String,inputDate:String?) : String {
        return try {
            val inputFormat = SimpleDateFormat(format, Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH)
            val date = inputFormat.parse(inputDate!!)
            Log.d(TAG, "provideFormattedDate: ${outputFormat.format(date!!)}")
            outputFormat.format(date)
        } catch (e: Exception) {
            e.stackTrace
            ""
        }
    }

    /**
     * provides the time in the formatted way
     * App format = HH:mm a // 12:02 PM
     * @param format    the format of the given string
     * @param inputDate given date
     * @return  formatted time
     */
    fun provideFormattedTime(format:String,inputDate:String?) : String {
        return try {
            val inputFormat = SimpleDateFormat(format, Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("HH:mm a",Locale.ENGLISH)
            val time = inputFormat.parse(inputDate!!)
            Log.d(TAG, "provideFormattedTime: ${outputFormat.format(time!!)}")
            outputFormat.format(time)
        } catch (e: Exception) {
            e.stackTrace
            ""
        }
    }
}