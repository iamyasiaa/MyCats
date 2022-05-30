package com.example.appwithcats.view.interfaces

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.appwithcats.domain.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import org.jetbrains.annotations.NotNull

interface ICatRepo {
    fun postLoginIn(user: PersonalData): Observable<UserModel>
    fun getApiKey(apiKey: String):@NotNull Observable<List<UserModel>>
    fun postFavorites(vote: VoteCatsModel): Observable<VoteModel>
    fun getFavoritesCats(count: Int):Observable<MutableList<FavoritesModel>>
    fun postFavoritesCats(fav: PostFavorites):Observable<PostFavoritesModel>
    fun deleteFavorites(id:String):Observable<DeleteFavorites>
    fun getVotesCats():Observable<GetVotes>
    fun helperRequest(it: Throwable): UserModel?

}