package com.example.appwithcats.view.authrization.viewmodel

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.*
import com.example.appwithcats.domain.authorization.PersonalData
import com.example.appwithcats.domain.apikey.UserModel
import com.example.appwithcats.view.interfaces.ICatRepo
import com.example.appwithcats.view.interfaces.ISharPref
import timber.log.Timber
import javax.inject.Inject

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance().appComponent.inject(this)
    }


    @Inject
    lateinit var catRepository: ICatRepo

    var sharedPreference: ISharPref? = null
        @Inject set


    private var _loginInLiveData = MutableLiveData<UserModel>()
    val loginInLiveData: LiveData<UserModel>
        get() = _loginInLiveData


    private var emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+")
    val email = MutableLiveData("")
    val emailError = MutableLiveData<String>(null)

    var isActive =  MutableLiveData(false)

    fun afterEmailChanged(s: Editable) {
        email.value = s.toString()
        emailError.value = if (s.toString()
                .matches(emailPattern)
        ) {
            if (description.value.toString().isNotEmpty()) {
                isActive.value = true
            }
            null
        } else {
            isActive.value = false
            getApplication<Application>().resources.getString(R.string.error_email)
        }
    }

    val description = MutableLiveData("")
    val descriptionError = MutableLiveData<String>(null)

    fun afterDescriptionChanged(s: Editable) {
        description.value = s.toString()
        descriptionError.value = if (s.toString().isNotEmpty()) {
            if (email.value.toString().matches(emailPattern)) {
                isActive.value = true
            }
            null
        } else {
            isActive.value = false
            getApplication<Application>().resources.getString(R.string.error_description)
        }
    }

    private fun postRequest() {
        val user =
            PersonalData(email.value.toString(), description.value.toString())
        catRepository!!.postLoginIn(user)
            .subscribe({
                _loginInLiveData.value = it
            }, {
                val error = catRepository!!.helperRequest(it)
                _loginInLiveData.value = error!!
            })
    }

    fun onLoginClick() {
        postRequest()
    }

    fun setEmail() {
        sharedPreference?.email = email.value.toString()
    }

    fun checkOnStatus(): Boolean {
        return if (loginInLiveData.value?.status == 400) {
            Timber.e("400")
            true
        } else {
            false
        }
    }


}
