package com.test.noteappkb.presentation

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar

fun String.toCurrencyFormat(): String? {
    val formatter = DecimalFormat("###,###")
    return formatter.format(this.toDouble())
}

fun String.toCapitalized() = replaceFirstChar { it.uppercase() }

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy")
    return dateFormat.format(Calendar.getInstance().time)
}
