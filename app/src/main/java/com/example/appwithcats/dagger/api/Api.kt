package com.example.appwithcats.dagger.api

import com.example.appwithcats.domain.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @GET("images/search")
    fun getRandomImage(
        @Query("limit") amountOfCats: Int,
    ): Observable<MutableList<CatModel>>

    @POST("user/passwordlesssignup")
    fun loginUser(@Body body: PersonalData): Observable<UserModel>

    @GET("favourites")
    fun getApiKey(
        @Query("api_key") apiKey: String,
    ): Observable<List<UserModel>>

    @POST("votes")
    fun voteCats(@Body body: VoteCatsModel): Observable<VoteModel>

}
