package br.com.tosin.listgithubusers.ui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private const val DEFAULT_DISPLAY_STRING = "-"

fun String?.defaultDisplayOfStringEmpty(): String {
    return this?.ifEmpty {
        DEFAULT_DISPLAY_STRING
    } ?: DEFAULT_DISPLAY_STRING
}

fun String.toAppCalendar(): Calendar? {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
    val date = sdf.parse(this)
    val calendar = Calendar.getInstance()
    return if (date != null) {
        calendar.time = date
        calendar
    } else {
        null
    }
}

fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
}

fun Calendar.toFormatViewHolder(): String {
    val sdf = SimpleDateFormat("MMM dd, YYYY", Locale.getDefault())
    return sdf.format(this.time)
}


