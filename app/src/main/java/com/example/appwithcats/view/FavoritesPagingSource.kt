package com.example.appwithcats.view

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.example.appwithcats.dagger.api.Api
import com.example.appwithcats.domain.CatModel
import com.example.appwithcats.domain.FavoritesModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoritesPagingSource(private val api: Api) : RxPagingSource<Int, FavoritesModel>() {
    override fun getRefreshKey(state: PagingState<Int, FavoritesModel>): Int? {
        return state.anchorPosition
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, FavoritesModel>> {
        val page = params.key ?: 1
        return api
            .getFavoritesImage(10, page)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, FavoritesModel>> {
                LoadResult.Page(
                    data = it,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}