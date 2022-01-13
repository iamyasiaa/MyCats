package com.example.appwithcats

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class CustomTextWatcherApiKey (private val editText: TextInputEditText, button: Button): TextWatcher {

    private var button: View = button

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        if (editText.text.toString().isNotEmpty()) {
            button.isEnabled = true
        }
    }
}