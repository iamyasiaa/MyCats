package com.example.appwithcats.view.apikey.viewmodel

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.App
import com.example.appwithcats.R
import com.example.appwithcats.view.interfaces.ISharPref
import com.example.appwithcats.domain.apikey.UserModel
import com.example.appwithcats.view.interfaces.ICatRepo
import javax.inject.Inject

class ApiKeyViewModel(application: Application) : AndroidViewModel(application){
    init {
        App.getInstance().appComponent.inject(this)
    }

    var sharedPreference: ISharPref? = null
        @Inject set

    @Inject
    lateinit var catRepository: ICatRepo


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
        catRepository.getApiKey(apiKey.value.toString())
            .subscribe({
                _apiKeyLiveData.postValue(true)
            }, {
                _apiKeyLiveData.postValue(false)
                val error = catRepository.helperRequest(it)
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


