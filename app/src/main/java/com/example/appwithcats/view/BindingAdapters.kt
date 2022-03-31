package com.example.appwithcats.view

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("error")
fun TextInputEditText.error(error: String?) {
    error.let { this.error = error} ?: run {this.error = null}
}