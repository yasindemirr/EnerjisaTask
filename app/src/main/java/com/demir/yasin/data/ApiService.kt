package com.demir.yasin.data

import com.demir.yasin.model.Results
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("main/userInvoices.json")
    suspend fun getInvoices():Response<Results>

}