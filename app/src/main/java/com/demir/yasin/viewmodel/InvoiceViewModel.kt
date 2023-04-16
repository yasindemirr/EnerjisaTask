package com.demir.yasin.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demir.yasin.model.Invoice
import com.demir.yasin.model.Results
import com.demir.yasin.repository.ApiRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(val apiRepository: ApiRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>(false)
    val errorMessage = MutableLiveData<String>(null)


    private val mutableInvoice = MutableLiveData<Results>()
    val invoiceData: LiveData<Results> = mutableInvoice

    init {
        getInvoice() // Uygulama açıldığında o ekran için sadece tek bir kez istek atmamız için init fonksiyonu içinde isteğimizi attık.
    }

    @SuppressLint("NullSafeMutableLiveData")
    private fun getInvoice() = viewModelScope.launch {
        isLoading.postValue(true)
        val response = apiRepository.getInvoices()
        if (response.isSuccessful) {
            response.body()?.let {
                mutableInvoice.value = it
                isLoading.postValue(false)
            }
        } else {
            errorMessage.postValue(response.message())
            isLoading.postValue(false)
        }
    }

}