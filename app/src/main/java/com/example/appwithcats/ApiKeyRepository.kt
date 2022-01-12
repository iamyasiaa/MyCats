package com.example.appwithcats

import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.domain.CatModel


class ApiKeyRepository {
    var const = 20

  /*  private val compositeDisposable = CompositeDisposable()
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
        }*/
}