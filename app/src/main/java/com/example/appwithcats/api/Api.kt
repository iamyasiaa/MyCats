package com.example.appwithcats.api

import com.example.appwithcats.model.CatModel
import com.example.appwithcats.model.Model
import com.example.appwithcats.model.PersonalData
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
        fun loginUser(@Body body: PersonalData): Observable<Model>

        @GET("favourites")
        fun getApiKey(
         @Query("api_key") apiKey: String,
): Observable<Model>
}

