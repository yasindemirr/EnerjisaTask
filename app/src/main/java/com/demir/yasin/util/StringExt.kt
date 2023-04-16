package com.demir.yasin.util

import android.widget.TextView
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

fun String.setTotalPrice() : String{
    val locale = Locale("tr", "TR")
    val symbols = DecimalFormatSymbols()
    symbols.groupingSeparator = '.'
    symbols.decimalSeparator = ','
    val pattern = "#,##0.0#"
    val decimalFormat = DecimalFormat(pattern, symbols)
    decimalFormat.isParseBigDecimal = true
    val amount: BigDecimal = decimalFormat.parse(this) as BigDecimal
    val f: NumberFormat = NumberFormat.getCurrencyInstance(locale)
    val output: String = f.format(amount)
    return output
}