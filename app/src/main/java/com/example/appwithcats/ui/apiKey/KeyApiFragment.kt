package com.example.appwithcats.ui.apiKey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.appwithcats.textwatcher.CustomTextWatcherApiKey
import com.example.appwithcats.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class KeyApiFragment : Fragment() {
    private lateinit var keyButton: Button
    private lateinit var apiKey: TextInputEditText
    private lateinit var backAuthorization: ImageButton

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
        backAuthorization = view.findViewById(R.id.back_authorization)
        keyButton = view.findViewById(R.id.loginButton1)
        keyButton.isEnabled = false
        apiKey = view.findViewById(R.id.apiKey)
        val textWatcher1 = CustomTextWatcherApiKey(apiKey, keyButton)
        apiKey.addTextChangedListener(textWatcher1)


        backAuthorization.setOnClickListener {
            val action = KeyApiFragmentDirections.actionKeyApiToAuthorization()
                Navigation.findNavController(view).navigate(action)

        }
        keyButton.setOnClickListener {
            apiKeyViewModel.apply {
                checkOnError()
                updateApiKey(apiKey.text.toString())
                getApiKey()
            }
        }
    }

    private fun checkOnError() {
        apiKeyViewModel.errorApiKeyData.observe(viewLifecycleOwner) {
            if (apiKeyViewModel.checkOnStatus()) {
                showErrorWindow(it.message)
            }
        }
        val action = KeyApiFragmentDirections.actionKeyApiToCatsFragment()
        apiKeyViewModel.apiKeyLiveData.observe(viewLifecycleOwner) {
            Navigation.findNavController(requireView()).navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_key_api, container, false)
    }
}


