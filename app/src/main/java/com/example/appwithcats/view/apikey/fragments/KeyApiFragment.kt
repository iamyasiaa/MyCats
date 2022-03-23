package com.example.appwithcats.view.apikey.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.appwithcats.utils.validator.CustomTextWatcherApiKey
import com.example.appwithcats.R
import com.example.appwithcats.databinding.FragmentKeyApiBinding
import com.example.appwithcats.view.apikey.viewmodel.ApiKeyViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText


class KeyApiFragment : Fragment() {
    private lateinit var keyButton: Button
    private lateinit var apiKey: TextInputEditText
    private lateinit var backAuthorization: ImageButton

    private val apiKeyViewModel by lazy { ViewModelProviders.of(this)[ApiKeyViewModel::class.java] }
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
        initUI()
        checkOnError()

        apiKey.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                apiKeyViewModel.updateApiKey(apiKey.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

    }
    private fun initUI() {
        backAuthorization.setOnClickListener {
            val action =
                KeyApiFragmentDirections.actionKeyApiToAuthorization()
            Navigation.findNavController(requireView()).navigate(action)

        }
        keyButton.setOnClickListener {
            apiKeyViewModel.apply {
                updateApiKey(apiKey.text.toString())
                getApiKey()
            }
        }

    }
     private fun checkOnError() {
         apiKeyViewModel.errorApiKeyData.observe(viewLifecycleOwner) {
             when (it.status) {
                 401 -> showErrorWindow(it.message)
             }
         }
         apiKeyViewModel.apiKeyLiveData.observe(viewLifecycleOwner) {
             val action =
                 KeyApiFragmentDirections.actionKeyApiToCatsFragment(
                     String()
                 )
              if (it){
                  Navigation.findNavController(requireView()).navigate(action)
              }
         }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentKeyApiBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_key_api, container, false)

        binding.lifecycleOwner = this
        binding.viewModelApi = apiKeyViewModel

        return binding.root
    }
}


