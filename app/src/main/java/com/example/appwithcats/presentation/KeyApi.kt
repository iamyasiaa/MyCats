package com.example.appwithcats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.appwithcats.ApiKeyViewModel
import com.example.appwithcats.AuthorizationViewModel
import com.example.appwithcats.CustomTextWatcherApiKey
import com.example.appwithcats.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class KeyApi : Fragment() {
    private lateinit var loginButton1: Button
    private lateinit var keyApi: TextInputEditText

    private val apiKeyViewModel: ApiKeyViewModel by viewModels()
    private fun showErrorWindow(message: String) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Ошибка")
                .setMessage(message)
                .setPositiveButton("ОК") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton1 = view!!.findViewById(R.id.loginButton1)
        loginButton1.isEnabled = false
        keyApi = view!!.findViewById(R.id.keyApi)
        val textWatcher1 = CustomTextWatcherApiKey(keyApi, loginButton1)
        keyApi.addTextChangedListener(textWatcher1)


        loginButton1.setOnClickListener {
            val action = KeyApiDirections.actionKeyApiToCatsFragment()
            Navigation.findNavController(view).navigate(action)
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_key_api, container, false)


        return view
    }
}
