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


class Authorization : Fragment() {
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
        loginButton = view!!.findViewById(R.id.loginButton)
        loginButton.isEnabled = false
        email = view!!.findViewById(R.id.email)
        description = view!!.findViewById(R.id.description)
        val edList = arrayOf(email, description)
        val textWatcher = CustomTextWatcher(edList = edList, loginButton)
        for (editText in edList) editText.addTextChangedListener(textWatcher)


        loginButton.setOnClickListener {
            val action = AuthorizationDirections.actionAuthorizationToKeyApi()
            val action1 = AuthorizationDirections.actionAuthorizationToCatsFragment()
            if(authorizationViewModel.sharedPreferenceRepository.email != ""){
                Navigation.findNavController(view).navigate(action1)
            }
                Navigation.findNavController(view).navigate(action1)

                authorizationViewModel.loginInLiveData.observe(viewLifecycleOwner) {
                    if (it.status == 400 ) {
                        showErrorWindow(it.message)
                    } else {
                        Navigation.findNavController(view).navigate(action)
                    }
                }
                authorizationViewModel.updateEmail(email.text.toString())
                authorizationViewModel.updateDescription(description.text.toString())
                authorizationViewModel.postRequest()

            }
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val view = inflater.inflate(R.layout.fragment_autorization, container, false)
            return view
        }


    }


