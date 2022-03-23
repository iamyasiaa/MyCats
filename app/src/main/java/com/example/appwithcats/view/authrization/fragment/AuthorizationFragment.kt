package com.example.appwithcats.view.authrization.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputEditText
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.appwithcats.utils.validator.CustomTextWatcher
import com.example.appwithcats.R
import com.example.appwithcats.databinding.FragmentAutorizationBinding
import com.example.appwithcats.view.authrization.viewmodel.AuthorizationViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AuthorizationFragment : Fragment() {
    private lateinit var loginButton: Button
    lateinit var email: TextInputEditText
    lateinit var description: TextInputEditText


    private val authorizationViewModel by lazy { ViewModelProviders.of(this)[AuthorizationViewModel::class.java] }
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
        loginButton = view.findViewById(R.id.loginButton)
        loginButton.isEnabled = false
        email = view.findViewById(R.id.email)
        description = view.findViewById(R.id.description)
        val edList = arrayOf(email, description)
        val textWatcher = CustomTextWatcher(edList = edList, loginButton)

        email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                authorizationViewModel.updateEmail(email.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                authorizationViewModel.updateDescription(description.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        for (editText in edList) {
            editText.addTextChangedListener(textWatcher)
        }
        checkOnError()
        val action1 =
            AuthorizationFragmentDirections.actionAuthorizationToCatsFragment(
                String()
            )
        if (authorizationViewModel.sharedPreference?.email != "") {
            Navigation.findNavController(view).navigate(action1)
        }

        authorizationViewModel.emailInLiveData.observe(viewLifecycleOwner) {
            email.text.toString()
            description.text.toString()
        }


    }


    private fun checkOnError() {
        val action =
            AuthorizationFragmentDirections.actionAuthorizationToKeyApi()
        authorizationViewModel.loginInLiveData.observe(viewLifecycleOwner) {
            if (authorizationViewModel.checkOnStatus()) {
                showErrorWindow(it.message)
                authorizationViewModel.checkOnEmpty()
            } else {
                Navigation.findNavController(requireView()).navigate(action)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAutorizationBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_autorization, container, false
        )
        binding.lifecycleOwner = this

        binding.viewModelAuth = authorizationViewModel

        return binding.root
    }
}


