package com.example.appwithcats.presentation

import androidx.annotation.NonNull
import com.example.appwithcats.app.interseptor.Api
import com.example.appwithcats.domain.Model
import com.example.appwithcats.domain.PersonalData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


/*
class AuthorizationRepository(private val api: Api) {
    fun getApiKey(apikey: String): @NonNull Observable<List<PersonalData>> {
        return api?.getApiKey(apikey)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread()) as @NonNull Observable<List<PersonalData>>
    }
}*/
