package com.example.appwithcats.view.authrization.viewmodel

import android.app.Application
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.*
import com.example.appwithcats.data.CatRepository
import com.example.appwithcats.domain.PersonalData
import com.example.appwithcats.domain.UserModel
import com.example.appwithcats.view.interfaces.ISharPref
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance().appComponent.inject(this)

    }


    @Inject
    lateinit var myRepository: CatRepository
    var sharedPreference: ISharPref? = null
        @Inject set

    private var _loginInLiveData = MutableLiveData<UserModel>()
    val loginInLiveData: LiveData<UserModel>
        get() = _loginInLiveData



    var emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+")
    val email = MutableLiveData("")
    val emailError = MutableLiveData<String>(null)

    fun afterEmailChanged(s: Editable){
        email.value = s.toString()
        emailError.value = if (s.toString().matches(emailPattern)) null else "Invalid email"
    }
    var descriptionPattern = Regex("[a-zA-Z0-9._-]")

    val description = MutableLiveData("")
    val descriptionError = MutableLiveData<String>(null)

    fun afterDescriptionChanged(s: Editable){
        description.value = s.toString()
        description.value = if (s.toString().matches(descriptionPattern)) null else "Invalid email"
    }



    private fun postRequest() {
        val user =
            PersonalData(email.value.toString(), description.value.toString())
        myRepository.postLoginIn(user)
            .subscribe({
                _loginInLiveData.value = it
            }, {
                if (it is HttpException) {
                    val body = it.response()?.errorBody()
                    val gson = Gson()
                    val adapter: TypeAdapter<UserModel> =
                        gson.getAdapter(UserModel::class.java)
                    try {
                        val error: UserModel =
                            adapter.fromJson(body?.string())
                        _loginInLiveData.value = error
                    } catch (e: IOException) {
                        Timber.d(e.toString())
                    }
                }
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
