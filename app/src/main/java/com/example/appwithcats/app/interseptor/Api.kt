package com.example.appwithcats.app.interseptor

import com.example.appwithcats.domain.CatModel
import com.example.appwithcats.domain.Model
import com.example.appwithcats.domain.PersonalData
import com.example.appwithcats.presentation.Authorization
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
        @GET("images/search")
        fun getRandomImage(
            @Query("limit") amountOfCats: Int,
        ): Observable<MutableList<CatModel>>

        @POST("user/passwordlesssignup")
        fun loginUser(): Observable<List<PersonalData>>

        @GET("favourites")
        fun getApiKey(
         @Query("api_key") apiKey: String,
): Observable<List<Model>>
}

