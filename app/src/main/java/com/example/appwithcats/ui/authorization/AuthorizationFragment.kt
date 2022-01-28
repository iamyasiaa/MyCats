package com.example.appwithcats.ui.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.appwithcats.textwatcher.CustomTextWatcher
import com.example.appwithcats.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AuthorizationFragment : Fragment() {
    private lateinit var loginButton: Button
    private lateinit var email: TextInputEditText
    private lateinit var description: TextInputEditText


    private val authorizationViewModel: AuthorizationViewModel by viewModels()
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
        for (editText in edList) editText.addTextChangedListener(textWatcher)
        val action1 = AuthorizationFragmentDirections.actionAuthorizationToCatsFragment()
        if (authorizationViewModel.sharedPreferenceRepository.email != "") {
            Navigation.findNavController(view).navigate(action1)
        }

        loginButton.setOnClickListener {
            checkOnError()
            authorizationViewModel.apply {
                updateEmail(email.text.toString())
                updateDescription(description.text.toString())
                postRequest()
            }

        }
    }

    private fun checkOnError() {
        val action = AuthorizationFragmentDirections.actionAuthorizationToKeyApi()
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_autorization, container, false)
    }


}


