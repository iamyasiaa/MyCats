package com.example.appwithcats.view.authrization.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.appwithcats.R
import com.example.appwithcats.databinding.FragmentAutorizationBinding
import com.example.appwithcats.view.authrization.viewmodel.AuthorizationViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AuthorizationFragment : Fragment() {


    private val authorizationViewModel : AuthorizationViewModel by viewModels()
    private fun showErrorWindow(message: String) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.Error))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        checkOnError()
        val action1 =
            AuthorizationFragmentDirections.actionAuthorizationToCatsFragment(
                String()
            )
        if (authorizationViewModel.sharedPreference?.email != "") {
            Navigation.findNavController(view).navigate(action1)
        }
    }


    private fun checkOnError() {
        val action =
            AuthorizationFragmentDirections.actionAuthorizationToKeyApi()
        authorizationViewModel.loginInLiveData.observe(viewLifecycleOwner) {
            if (authorizationViewModel.checkOnStatus()) {
                showErrorWindow(it.message)
            } else {

                authorizationViewModel.setEmail()
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


