package com.example.newsapp.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


fun formatDate(dateString: String): String {
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val inputFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:SS", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(dateString, inputFormatter)
        outputFormatter.format(date)
    } else {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)
        val date: Date = inputFormat.parse(dateString) as Date
        outputFormat.format(date)
    }
}