package com.example.appwithcats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.app.interseptor.App
import com.example.appwithcats.domain.Model
import com.example.appwithcats.domain.MyRepository
import com.example.appwithcats.domain.PersonalData
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
    lateinit var myRepository: MyRepository

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    private var _apiKeyLiveData = MutableLiveData<Model>()
    val apiKeyLiveData: LiveData<Model>
        get() = _apiKeyLiveData

    fun postRequest() {
        myRepository.getApiKey(sharedPreferenceRepository.apikey)
            .subscribe({
                _apiKeyLiveData.value = it
            }, {
                if (it is HttpException) {
                    val body = it.response()?.errorBody()
                    val gson = Gson()
                    val adapter: TypeAdapter<Model> =
                        gson.getAdapter(Model::class.java)
                    try {
                        val error: Model =
                            adapter.fromJson(body?.string())
                        _apiKeyLiveData.value = error
                    } catch (e: IOException) {
                        Timber.d(e.toString())
                    }
                }
            })
    }
    fun updateApiKey(apiKey: String) {
        sharedPreferenceRepository.apikey = apiKey
    }


}