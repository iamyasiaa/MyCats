package com.example.appwithcats.ui.apiKey

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.repository.SharedPreferenceRepository
import com.example.appwithcats.model.UserModel
import com.example.appwithcats.repository.CatRepository
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class ApiKeyViewModel (application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance().appComponent.inject(this)
    }

    @Inject
    lateinit var myRepository: CatRepository

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    private var _apiKeyLiveData = MutableLiveData<Boolean>()
    val apiKeyLiveData: LiveData<Boolean>
        get() = _apiKeyLiveData

    private var _errorApiKeyData = MutableLiveData<UserModel>()
    val errorApiKeyData: LiveData<UserModel>
        get() = _errorApiKeyData

    fun getApiKey() {
        myRepository.getApiKey(sharedPreferenceRepository.apikey)
            .subscribe({
                _apiKeyLiveData.postValue(true)
            }, {
                _apiKeyLiveData.postValue(false)
                if (it is HttpException) {
                    val body = it.response()?.errorBody()
                    val gson = Gson()
                    val adapter: TypeAdapter<UserModel> =
                        gson.getAdapter(UserModel::class.java)
                    try {
                        val error: UserModel =
                            adapter.fromJson(body?.string())
                        _errorApiKeyData.value = error
                    } catch (e: IOException) {
                    }
                }
            })
    }
    fun updateApiKey(apiKey: String) {
        sharedPreferenceRepository.apikey = apiKey
    }
    fun checkOnStatus(): Boolean {
        if (errorApiKeyData.value!!.status == 401) {

            Timber.e("401")
            return true
        } else
            return false
        }
}


