package com.demir.yasin.model

data class Results(
    val invoices: List<Invoice>,
    val list: List<ListModel>,
    val totalPrice: String,
    val totalPriceCount: Int
)