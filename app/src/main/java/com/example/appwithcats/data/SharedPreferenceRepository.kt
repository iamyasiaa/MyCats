package com.example.appwithcats.data

import android.content.Context
import com.example.appwithcats.view.interfaces.ISharPref


class SharedPreferenceRepository(context: Context): ISharPref {

    companion object {
        private const val EMAIL = "e-mail"
        private const val DESCRIPTION = "description"
        private const val APIKEY = "api-key"
        private const val IMAGE_ID = "image_id"
    }

    private var preference = context.getSharedPreferences("sp_dagger", Context.MODE_PRIVATE)
    private var editor = preference.edit()

    private fun setString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    private fun getString(key: String, defaultValue: String = ""): String {
        return preference.getString(key, defaultValue) ?: defaultValue
    }

    private fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    private fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return preference.getBoolean(key, defaultValue)
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
    override var imageId: String
        get() = getString(IMAGE_ID)
        set(value) {
            setString(IMAGE_ID, value)
        }

}