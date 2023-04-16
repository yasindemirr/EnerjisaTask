package com.demir.yasin.util

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.demir.yasin.R

//Bu sınıfta kullanıcı girdiği T.C kimlik no, cep telefonu ve mail adresi validasyon çerçevesinde incelendi.Uymayanlar için kullanıcıya anında geri dönüş yapıldı.
class NumberValidator(
    private val tcKimlikEditText: EditText,
    private val phoneEditText: EditText,
    private val emailEditText: EditText,
    private val tcErrorText: TextView,
    private val emailErrorText: TextView,
    private val phoneErrorText: TextView
) : TextWatcher, View.OnFocusChangeListener {

    init {
        tcKimlikEditText.onFocusChangeListener = this
        phoneEditText.onFocusChangeListener = this
        emailEditText.onFocusChangeListener = this
    }

    private var hasTcKimlikFocus = false
    private var hasPhoneFocus = false
    private var hasEmailFocus = false

    override fun afterTextChanged(s: Editable?) {
        // Do nothing
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Do nothing
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (hasTcKimlikFocus) {
            if (s != null && s.isNotEmpty() && s.length == 11) {
                if (!isValidTCNumber(s.toString())) {
                    tcKimlikEditText.setBackgroundResource(R.drawable.edit_text_error)
                    tcErrorText.visibility=View.VISIBLE
                } else {
                    tcKimlikEditText.setBackgroundResource(R.drawable.edit_text_valid_color)
                    tcErrorText.visibility=View.INVISIBLE
                }
            } else {
                if (hasTcKimlikFocus) {
                    tcKimlikEditText.setBackgroundResource(R.drawable.edit_text_focus)
                } else {
                    tcKimlikEditText.setBackgroundResource(R.drawable.edit_text_back)
                }
            }
        } else if (hasPhoneFocus) {
            if (s != null && s.isNotEmpty() && s.length == 10 ) {
                if (!isValidPhoneNumber(s.toString())){
                    phoneEditText.setBackgroundResource(R.drawable.edit_text_error)
                    phoneErrorText.visibility=View.VISIBLE
                }else{
                    phoneEditText.setBackgroundResource(R.drawable.edit_text_valid_color)
                    phoneErrorText.visibility=View.INVISIBLE
                }
            } else {
                if (hasPhoneFocus) {
                    phoneEditText.setBackgroundResource(R.drawable.edit_text_focus)
                } else {
                    phoneEditText.setBackgroundResource(R.drawable.edit_text_back)
                }
            }
        } else if (hasEmailFocus) {
            if (s != null && s.isNotEmpty()) {
                if(!isValidEmail(s.toString())){
                    emailEditText.setBackgroundResource(R.drawable.edit_text_error)
                    emailErrorText.visibility=View.VISIBLE
                }else{
                    emailEditText.setBackgroundResource(R.drawable.edit_text_valid_color)
                    emailErrorText.visibility=View.INVISIBLE
                }
            } else {
                if (hasEmailFocus) {
                    emailEditText.setBackgroundResource(R.drawable.edit_text_focus)
                } else {
                    emailEditText.setBackgroundResource(R.drawable.edit_text_back)
                }
            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when (v) {
            tcKimlikEditText -> {
                hasTcKimlikFocus = hasFocus
                if (hasFocus && tcKimlikEditText.text.length != 11) {
                    tcKimlikEditText.setBackgroundResource(R.drawable.edit_text_focus)
                } else {
                    if (tcKimlikEditText.text.length != 11) {
                        tcKimlikEditText.setBackgroundResource(R.drawable.edit_text_error)
                        tcErrorText.visibility=View.VISIBLE

                    } else {
                        if (!isValidTCNumber(tcKimlikEditText.text.toString())) {
                            tcKimlikEditText.setBackgroundResource(R.drawable.edit_text_error)
                            tcErrorText.visibility=View.VISIBLE
                        } else {
                            tcKimlikEditText.setBackgroundResource(R.drawable.edit_text_valid_color)
                            tcErrorText.visibility=View.INVISIBLE
                        }
                    }
                }
            }
            phoneEditText -> {
                hasPhoneFocus = hasFocus
                if (hasFocus && phoneEditText.text.length != 10) {
                    phoneEditText.setBackgroundResource(R.drawable.edit_text_focus)
                } else {
                    if (phoneEditText.text.length != 10 || !phoneEditText.text.startsWith("5")) {
                        phoneEditText.setBackgroundResource(R.drawable.edit_text_error)
                        phoneErrorText.visibility=View.VISIBLE
                    } else {
                        phoneEditText.setBackgroundResource(R.drawable.edit_text_valid_color)
                        phoneErrorText.visibility=View.INVISIBLE
                    }
                }
            }
            emailEditText -> {
                hasEmailFocus = hasFocus
                if (hasFocus && emailEditText.text.isEmpty()) {
                    emailEditText.setBackgroundResource(R.drawable.edit_text_focus)
                } else {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.text).matches()) {
                        emailEditText.setBackgroundResource(R.drawable.edit_text_error)
                        emailErrorText.visibility=View.VISIBLE
                    } else {
                        emailEditText.setBackgroundResource(R.drawable.edit_text_valid_color)
                        emailErrorText.visibility=View.INVISIBLE
                    }
                }
            }
        }
    }


    private fun isValidTCNumber(tckn: String): Boolean {
        if (tckn.length != 11) {
            return false
        }

        if (!tckn.matches(Regex("[0-9]+"))) {
            return false
        }
        if (tckn[0] == '0') {
            return false
        }

        val checkDigit =
            ((tckn[0].toString().toInt() + tckn[2].toString().toInt() + tckn[4].toString().toInt() +
                    tckn[6].toString().toInt() + tckn[8].toString().toInt()) * 7 -
                    (tckn[1].toString().toInt() + tckn[3].toString().toInt() + tckn[5].toString()
                        .toInt() +
                            tckn[7].toString().toInt())) % 10

        val checkDigit_ =
            tckn.substring(0, 10).toCharArray().map { it.toString().toInt() }.sum() % 10
        println(checkDigit_)
        // Son hanenin doğruluğunu kontrol edin.
        return checkDigit == tckn[9].toString().toInt() && checkDigit_ == tckn[10].toString()
            .toInt()
    }
    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val regex = Regex("^5[0-9]{9}$")
        return regex.matches(phoneNumber)
    }
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}


