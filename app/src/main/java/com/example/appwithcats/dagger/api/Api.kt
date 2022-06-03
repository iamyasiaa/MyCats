package com.example.appwithcats.dagger.api

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
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface Api {
    @GET("images/search")
    fun getRandomImage(
        @Query("limit") amountOfCats: Int,
        @Query("page") page: Int
    ): Single<MutableList<CatModel>>

    @POST("user/passwordlesssignup")
    fun loginUser(@Body body: PersonalData): Observable<UserModel>

    @GET("favourites")
    fun getApiKey(
        @Query("api_key") apiKey: String,
    ): Observable<List<UserModel>>

    @POST("votes")
    fun voteCats(@Body body: VoteCatsModel): Observable<VoteModel>

    @GET("favourites")
    fun getFavoritesImage(
        @Query("limit") amountOfFavorites: Int,
    ): Observable<MutableList<FavoritesModel>>

    @POST("favourites")
    fun favoritesCats(@Body body: PostFavorites): Observable<PostFavoritesModel>

    @DELETE("favourites/{id}")
    fun deleteFavorites(
        @Path ("id") id: String,
    ) : Observable<DeleteFavorites>

    @GET("votes")
    fun getVotesImage(
    ): Observable<GetVotes>

}

