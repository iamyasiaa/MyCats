package com.example.appwithcats.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.example.appwithcats.dagger.api.Api
import com.example.appwithcats.domain.cats.CatModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers


class CatsPagingSource(private val api: Api) : RxPagingSource<Int, CatModel>(){
    override fun getRefreshKey(state: PagingState<Int, CatModel>): Int? {
        return state.anchorPosition
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, CatModel>> {
        val page = params.key ?: 1
        return api
            .getRandomImage(10, page)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, CatModel>> {
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