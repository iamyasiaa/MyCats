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
    private lateinit var keyButton: Button
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
        keyButton = view.findViewById(R.id.loginButton1)
        keyButton.isEnabled = false
        apiKey = view.findViewById(R.id.apiKey)
        val textWatcher1 = CustomTextWatcherApiKey(apiKey, keyButton)
        apiKey.addTextChangedListener(textWatcher1)

        apiKeyViewModel.errorApiKeyData.observe(viewLifecycleOwner) {
            val action = KeyApiFragmentDirections.actionKeyApiToCatsFragment()
            if (apiKeyViewModel.checkOnStatus()) {
                showErrorWindow(it.message)
            } else Navigation.findNavController(view!!).navigate(action)

        }

        keyButton.setOnClickListener {
            apiKeyViewModel.apply {
                updateApiKey(apiKey.text.toString())
                getApiKey()
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

