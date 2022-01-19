package com.example.appwithcats.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.repository.MyRepository
import com.example.appwithcats.repository.SharedPreferenceRepository
import com.example.appwithcats.model.Model
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ApiKeyViewModel (application: Application) : AndroidViewModel(application) {
    init {
        App.getInstance().appComponent.inject(this)
    }

    @Inject
    lateinit var myRepository: MyRepository

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    private var _apiKeyLiveData = MutableLiveData<Boolean>()
    val apiKeyLiveData: LiveData<Boolean>
        get() = _apiKeyLiveData

    private var _errorApiKeyData = MutableLiveData<Model>()
    val errorApiKeyData: LiveData<Model>
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
                    val adapter: TypeAdapter<Model> =
                        gson.getAdapter(Model::class.java)
                    try {
                        val error: Model =
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


}