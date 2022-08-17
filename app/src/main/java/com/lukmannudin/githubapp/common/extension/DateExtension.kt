package com.lukmannudin.githubapp.common.extension

import android.annotation.SuppressLint
import android.os.Build
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String.toDate(pattern: String): Date? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
        val localDate = LocalDate.parse(this, formatter)
        Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    } else {
        val simpleDateFormat = SimpleDateFormat(pattern)
        val timeZone = TimeZone.getTimeZone("UTC")
        simpleDateFormat.timeZone = timeZone
        simpleDateFormat.parse(this)
    }
}

fun Long.toDate(): Date {
    return Date(this)
}

fun Date.getLastUpdatedTimeText(): String {
    val diff = Calendar.getInstance().time.time - this.time

    val oneSec = 1000L
    val oneMin: Long = 60 * oneSec
    val oneHour: Long = 60 * oneMin
    val oneDay: Long = 24 * oneHour
    val oneMonth: Long = 30 * oneDay
    val oneYear: Long = 365 * oneDay

    val diffMin: Long = diff / oneMin
    val diffHours: Long = diff / oneHour
    val diffDays: Long = diff / oneDay
    val diffMonths: Long = diff / oneMonth
    val diffYears: Long = diff / oneYear

    return when {
        diffYears > 0 -> {
            "$diffYears years ago"
        }
        diffMonths > 0 && diffYears < 1 -> {
            "${(diffMonths - diffYears / 12)} months ago "
        }
        diffDays > 0 && diffMonths < 1 -> {
            "${(diffDays - diffMonths / 30)} days ago "
        }
        diffHours > 0 && diffDays < 1 -> {
            "${(diffHours - diffDays * 24)} hours ago "
        }
        diffMin > 0 && diffHours < 1 -> {
            "${(diffMin - diffHours * 60)} min ago "
        }
        diffMin < 1 -> {
            "just now"
        }
        else -> ""
    }
}