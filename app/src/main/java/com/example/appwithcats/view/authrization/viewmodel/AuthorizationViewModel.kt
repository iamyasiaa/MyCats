package com.example.appwithcats.view.authrization.viewmodel

import android.app.Application
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

    private var _emailInLiveData = MutableLiveData<PersonalData>()
    val emailInLiveData: LiveData<PersonalData>
    get() = _emailInLiveData

    private var _loginInLiveData = MutableLiveData<UserModel>()
    val loginInLiveData: LiveData<UserModel>
        get() = _loginInLiveData


    private fun postRequest() {
        val user =
            PersonalData(sharedPreference?.email.toString(), sharedPreference?.description.toString())
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

     fun updateEmail(email: String) {
        sharedPreference?.email = email
     }

     fun updateDescription(description: String) {
        sharedPreference?.description = description
    }

    fun onLoginClick() {
        postRequest()
    }

    fun checkOnStatus(): Boolean {
        return if (loginInLiveData.value?.status == 400) {
            Timber.e("400")
            true
        } else {
            false
        }
    }

    fun checkOnEmpty() {
        updateEmail("")
        updateDescription("")
    }
}
