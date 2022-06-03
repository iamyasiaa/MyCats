package com.example.appwithcats.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.example.appwithcats.dagger.api.Api
import com.example.appwithcats.data.paging.CatsPagingSource
import com.example.appwithcats.domain.apikey.UserModel
import com.example.appwithcats.domain.authorization.PersonalData
import com.example.appwithcats.domain.cats.CatModel
import com.example.appwithcats.domain.cats.votes.GetVotes
import com.example.appwithcats.domain.cats.votes.VoteCatsModel
import com.example.appwithcats.domain.cats.votes.VoteModel
import com.example.appwithcats.domain.favorites.DeleteFavorites
import com.example.appwithcats.domain.favorites.FavoritesModel
import com.example.appwithcats.domain.favorites.PostFavorites
import com.example.appwithcats.domain.favorites.PostFavoritesModel
import com.example.appwithcats.view.interfaces.ICatRepo
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jetbrains.annotations.NotNull
import retrofit2.HttpException


class CatRepository(private val api: Api): ICatRepo {

    fun getCatList(pageConfig: PagingConfig = getDefaultPageConfig()): Flowable<PagingData<CatModel>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { CatsPagingSource(api) }
        ).flowable
    }

    fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
        )
    }


    override fun postLoginIn(user: PersonalData): Observable<UserModel> {
        return api.loginUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getApiKey(apiKey: String): @NotNull Observable<List<UserModel>> {
        return api.getApiKey(apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun postFavorites(vote: VoteCatsModel): Observable<VoteModel> {
        return api.voteCats(vote)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    override fun getFavoritesCats(count: Int): Observable<MutableList<FavoritesModel>> {
        return api.getFavoritesImage(count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun postFavoritesCats(fav: PostFavorites): Observable<PostFavoritesModel> {
        return api.favoritesCats(fav)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteFavorites(id:String): Observable<DeleteFavorites> {
        return api.deleteFavorites(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    override fun getVotesCats(): Observable<GetVotes>{
        return api.getVotesImage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    override fun helperRequest(it: Throwable): UserModel?{
        if (it is HttpException) {
            val body = it.response()?.errorBody()
            val gson = Gson()
            val adapter: TypeAdapter<UserModel> = gson.getAdapter(UserModel::class.java)
            val error: UserModel =
                adapter.fromJson(body?.string())

            return error
        } else{
            return null
        }
    }


}