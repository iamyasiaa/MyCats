package com.example.appwithcats.view.apikey.viewmodel

import android.app.Application
import android.text.Editable
import android.widget.Button
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.R
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

    var isActive =  MutableLiveData(false)

    private val apiKey = MutableLiveData("")
    val apiKeyError = MutableLiveData<String>(null)

    fun afterApiKeyChanged(s: Editable){
        apiKey.value = s.toString()
        apiKeyError.value = if (s.toString().isNotEmpty()){
            isActive.value = true
            null
        } else {
            isActive.value = false
            getApplication<Application>().resources.getString(
            R.string.error_api_key)
        }
    }

    fun getApiKey() {
        myRepository.getApiKey(apiKey.value.toString())
            .subscribe({
                _apiKeyLiveData.postValue(true)
            }, {
                _apiKeyLiveData.postValue(false)
                val error = myRepository.helperRequest(it)
                _errorApiKeyData.value = error!!

            })
    }

    fun onClickApiKey() {
        getApiKey()
    }

    fun setApiKey() {
        sharedPreference?.apikey = apiKey.value.toString()
    }
}


