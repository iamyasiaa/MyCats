package com.example.appwithcats.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.repository.MyRepository
import com.example.appwithcats.repository.SharedPreferenceRepository
import com.example.appwithcats.model.Model
import com.example.appwithcats.model.PersonalData
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AuthorizationViewModel (application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance().appComponent.inject(this)
    }

    @Inject
    lateinit var myRepository: MyRepository

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    private var _loginInLiveData = MutableLiveData<Model>()
    val loginInLiveData: LiveData<Model>
        get() = _loginInLiveData

    fun postRequest() {
        val user = PersonalData(sharedPreferenceRepository.email, sharedPreferenceRepository.description)
        myRepository.postLoginIn(user)
            .subscribe({
                _loginInLiveData.value = it
            }, {
                if (it is HttpException) {
                    val body = it.response()?.errorBody()
                    val gson = Gson()
                    val adapter: TypeAdapter<Model> =
                        gson.getAdapter(Model::class.java)
                    try {
                        val error: Model =
                            adapter.fromJson(body?.string())
                        _loginInLiveData.value = error
                    } catch (e: IOException) {
                        Timber.d(e.toString())
                    }
                }
            })
    }
    fun updateEmail(email: String) {
        sharedPreferenceRepository.email = email
    }
    fun updateDescription(description: String) {
        sharedPreferenceRepository.description = description
    }
}