package com.example.appwithcats.repository

import com.example.appwithcats.api.Api
import com.example.appwithcats.model.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jetbrains.annotations.NotNull


class CatRepository(private val api: Api) {


    fun getCatList(count: Int): Observable<MutableList<CatModel>> {
        return api.getRandomImage(count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun postLoginIn(user: PersonalData): Observable<UserModel> {
        return api.loginUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getApiKey(apiKey: String): @NotNull Observable<List<UserModel>> {
        return api.getApiKey(apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun postFavorites(vote: VoteCatsModel): Observable<VoteModel> {
        return api.voteCats(vote)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}