package com.example.appwithcats.view.authrization.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.appwithcats.utils.validator.CustomTextWatcher
import com.example.appwithcats.R
import com.example.appwithcats.databinding.FragmentAutorizationBinding
import com.example.appwithcats.domain.CatModel
import com.example.appwithcats.domain.PersonalData
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


