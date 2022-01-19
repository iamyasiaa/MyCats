package com.example.appwithcats.ui

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
import com.example.appwithcats.repository.SharedPreferenceRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class Authorization : Fragment() {
    private lateinit var loginButton: Button
    private lateinit var email: TextInputEditText
    private lateinit var description: TextInputEditText



    private val loginViewModel: AuthorizationViewModel by viewModels()
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
        val sharedPreferenceRepository = SharedPreferenceRepository(context!!)
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

            if(email.text.toString() == sharedPreferenceRepository.email && sharedPreferenceRepository.apikey != ""){
                Navigation.findNavController(view).navigate(action1)
            }else{
                loginViewModel.loginInLiveData.observe(viewLifecycleOwner) {
                    if (it.status == 400 ) {
                        showErrorWindow(it.message)
                        loginViewModel.updateEmail("")
                        loginViewModel.updateDescription("")
                    } else {
                        Navigation.findNavController(view).navigate(action)
                    }
                }
                loginViewModel.updateEmail(email.text.toString())
                loginViewModel.updateDescription(description.text.toString())
                loginViewModel.postRequest()

            }
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


