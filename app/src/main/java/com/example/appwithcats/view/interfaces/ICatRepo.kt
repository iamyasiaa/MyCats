package com.example.appwithcats.view.interfaces

import com.example.appwithcats.domain.apikey.UserModel
import com.example.appwithcats.domain.authorization.PersonalData
import com.example.appwithcats.domain.cats.votes.GetVotes
import com.example.appwithcats.domain.cats.votes.VoteCatsModel
import com.example.appwithcats.domain.cats.votes.VoteModel
import com.example.appwithcats.domain.favorites.DeleteFavorites
import com.example.appwithcats.domain.favorites.FavoritesModel
import com.example.appwithcats.domain.favorites.PostFavorites
import com.example.appwithcats.domain.favorites.PostFavoritesModel
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