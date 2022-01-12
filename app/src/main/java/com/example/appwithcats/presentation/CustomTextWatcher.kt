package com.example.appwithcats.presentation

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText


class CustomTextWatcher(private var edList: Array<TextInputEditText>, v: Button) :
    TextWatcher {
    private var v: View = v
    var emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {
        var count = 0

        for (editText in edList) {
            count++

            if (editText.text.toString().trim { it <= ' ' }.isEmpty()) {
                v.isEnabled = false
                break
            } else {
                if(count == 1){
                    if (editText.text.toString().matches(emailPattern)){
                        v.isEnabled = true
                    }
                    else
                    {
                        v.isEnabled = false
                        editText.error = "Invalid email"
                    }
                }
            }

        }
    }

}