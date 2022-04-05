package com.example.appwithcats.view.apikey.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.appwithcats.R
import com.example.appwithcats.databinding.FragmentKeyApiBinding
import com.example.appwithcats.view.apikey.viewmodel.ApiKeyViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class KeyApiFragment : Fragment() {
    private lateinit var backAuthorization: ImageButton

    private val apiKeyViewModel : ApiKeyViewModel by viewModels()
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
        backAuthorization = view.findViewById(R.id.back_authorization)
        initUI()
        checkOnError()


    }
    private fun initUI() {
        backAuthorization.setOnClickListener {
            val action =
                KeyApiFragmentDirections.actionKeyApiToAuthorization()
            Navigation.findNavController(requireView()).navigate(action)

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
                  apiKeyViewModel.setApiKey()
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


