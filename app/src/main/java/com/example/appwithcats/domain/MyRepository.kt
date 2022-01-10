package com.example.appwithcats.domain

import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.app.interseptor.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MyRepository (private val api: Api?) {
    var const = 20;

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
}