package com.example.appwithcats.ui.apiKey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.appwithcats.textwatcher.CustomTextWatcherApiKey
import com.example.appwithcats.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class KeyApiFragment : Fragment() {
    private lateinit var loginButton1: Button
    private lateinit var apiKey: TextInputEditText

    private val apiKeyViewModel: ApiKeyViewModel by viewModels()
    private fun showErrorWindow(message: String) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.Error))
                .setMessage(message)
                .setPositiveButton("ОК") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton1 = view.findViewById(R.id.loginButton1)
        loginButton1.isEnabled = false
        apiKey = view.findViewById(R.id.apiKey)
        val textWatcher1 = CustomTextWatcherApiKey(apiKey, loginButton1)
        apiKey.addTextChangedListener(textWatcher1)


        loginButton1.setOnClickListener {
            checkOnError()
        }
        apiKeyViewModel.getApiKey()
    }

    private fun checkOnError() {
        val action = KeyApiFragmentDirections.actionKeyApiToCatsFragment()
        apiKeyViewModel.updateApiKey(apiKey.text.toString())
        apiKeyViewModel.errorApiKeyData.observe(viewLifecycleOwner) {
            if (apiKeyViewModel.checkOnStatus()) {
                showErrorWindow(it.message)
            } else {
                apiKeyViewModel.apiKeyLiveData.observe(viewLifecycleOwner) {
                    Navigation.findNavController(view!!).navigate(action)
                }
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_key_api, container, false)
    }
}

