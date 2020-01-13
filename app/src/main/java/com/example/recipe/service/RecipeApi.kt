package com.example.recipe.service

import com.example.recipe.model.RecipeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("search")
    fun getRecipes(@Query("app_key") appKey: String, @Query("app_id") appId: String, @Query("q") searchText: String): Observable<RecipeResponse>
}