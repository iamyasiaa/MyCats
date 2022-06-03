package com.example.appwithcats.view.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("error")
fun TextInputEditText.error(error: String?) {
    error.let { this.error = error} ?: run {this.error = null}
}
@BindingAdapter("isActive")
fun MaterialButton.error(error: Boolean){
    this.isEnabled = error
}
