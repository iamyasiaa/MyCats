package com.example.appwithcats.view.apikey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.data.CatRepository
import com.example.appwithcats.view.interfaces.ISharPref
import com.example.appwithcats.domain.UserModel
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ApiKeyViewModel(application: Application) : AndroidViewModel(application){
    init {
        App.getInstance().appComponent.inject(this)
    }

    @Inject
    lateinit var myRepository: CatRepository

    var sharedPreference: ISharPref? = null
        @Inject set


    private var _apiKeyLiveData = MutableLiveData<Boolean>()
    val apiKeyLiveData: LiveData<Boolean>
        get() = _apiKeyLiveData

    private var _errorApiKeyData = MutableLiveData<UserModel>()
    val errorApiKeyData: LiveData<UserModel>
        get() = _errorApiKeyData

    fun getApiKey() {
        myRepository.getApiKey(sharedPreference!!.apikey)
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
        sharedPreference!!.apikey = apiKey
    }


}

