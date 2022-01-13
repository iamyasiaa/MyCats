package com.example.appwithcats.domain

import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.app.interseptor.Api
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MyRepository (private val api: Api) {
    var const = 20

    private val compositeDisposable = CompositeDisposable()
    val getCatLiveData: MutableLiveData<MutableList<CatModel>>
        get() {
            val data: MutableLiveData<MutableList<CatModel>> = MutableLiveData<MutableList<CatModel>>()
            api?.getRandomImage(const)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())?.let { it ->
                    compositeDisposable.add(
                        it
                            .subscribe{
                                if (it != null) {
                                    data.value = it
                                }
                            })
                }
            return data
        }
    fun postLoginIn(user:PersonalData): Observable<Model> {
        return api.loginUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
   /* fun postApiKeyIn(user:aaaaaaaa): Observable<Model> {
        return api.getApiKey(user)
            .subscribeOn(Schedulers.io())

    }*/
}