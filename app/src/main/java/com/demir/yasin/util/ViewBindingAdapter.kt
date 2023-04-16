package com.demir.yasin.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
//Burada  string içinde TL logosunu ekledik
object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("setTotalPrice")
    fun setTotalPrice(view: TextView, price: String) {
        val locale = Locale("tr", "TR")

        val symbols = DecimalFormatSymbols()
        symbols.groupingSeparator = '.'
        symbols.decimalSeparator = ','
        val pattern = "#,##0.0#"
        val decimalFormat = DecimalFormat(pattern, symbols)
        decimalFormat.isParseBigDecimal = true

        val amount: BigDecimal = decimalFormat.parse(price) as BigDecimal


        val f: NumberFormat = NumberFormat.getCurrencyInstance(locale)
        val output: String = f.format(amount)
        view.text = output
    }

}