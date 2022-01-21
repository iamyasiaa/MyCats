package com.example.appwithcats.repository

import androidx.lifecycle.MutableLiveData
import com.example.appwithcats.api.Api
import com.example.appwithcats.model.CatModel
import com.example.appwithcats.model.PersonalData
import com.example.appwithcats.model.UserModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jetbrains.annotations.NotNull


class CatRepository(private val api: Api) {
    var const = 20

    private val compositeDisposable = CompositeDisposable()
    val getCatLiveData: MutableLiveData<MutableList<CatModel>>
        get() {
            val data: MutableLiveData<MutableList<CatModel>> =
                MutableLiveData<MutableList<CatModel>>()
            api.getRandomImage(const)
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())?.let { it ->
                    compositeDisposable.add(
                        it.subscribe {
                            it?.let {
                                data.value = it
                            }
                            compositeDisposable.dispose()
                        })

                }

            return data
        }

    fun postLoginIn(user: PersonalData): Observable<UserModel> {
        return api.loginUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getApiKey(apiKey: String): @NotNull Observable<UserModel> {
        return api.getApiKey(apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}