package com.example.appwithcats.repository

import android.content.Context
import com.example.appwithcats.inresfaces.ISharPref


class SharedPreferenceRepository(context: Context) : ISharPref {

    companion object {
        private const val EMAIL = "e-mail"
        private const val DESCRIPTION = "description"
        private const val APIKEY = "api-key"
    }

    private var preference = context.getSharedPreferences("sp_dagger", Context.MODE_PRIVATE)
    private var editor = preference.edit()

    private fun setString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    private fun getString(key: String, defaultValue: String = ""): String {
        return preference.getString(key, defaultValue) ?: defaultValue
    }

    override var description: String
        get() = getString(DESCRIPTION)
        set(value) {
            setString(DESCRIPTION, value)
        }
    override var email: String
        get() = getString(EMAIL)
        set(value) {
            setString(EMAIL, value)
        }
    override var apikey: String
        get() = getString(APIKEY)
        set(value) {
            setString(APIKEY, value)
        }
}