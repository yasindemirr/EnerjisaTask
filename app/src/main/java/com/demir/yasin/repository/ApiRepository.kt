package com.demir.yasin.repository

import com.demir.yasin.data.ApiService
import com.demir.yasin.model.Results
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService:ApiService) {
    suspend fun getInvoices():Response<Results>{
        return apiService.getInvoices()
    }
}