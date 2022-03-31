package com.example.appwithcats.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.example.appwithcats.dagger.api.Api
import com.example.appwithcats.domain.*
import com.example.appwithcats.view.cats.fragments.CatsPagingSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jetbrains.annotations.NotNull


class CatRepository(private val api: Api) {


    fun getCatList(pageConfig: PagingConfig = getDefaultPageConfig()): Flowable<PagingData<CatModel>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { CatsPagingSource(api) }
        ).flowable
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
        )
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