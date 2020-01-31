package com.example.recipe.service

import com.example.recipe.model.recipeFoodDatabase.RecipeFoodDatabaseResponse
import com.example.recipe.model.recipeSearchModel.RecipeSearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("search")
    fun getSearchedRecipes(@Query("app_key") appKey: String, @Query("app_id") appId: String, @Query("q") searchText: String): Observable<RecipeSearchResponse>

    @GET("api/food-database/parser")
    fun getAllRecipes(@Query("app_key") appKey: String, @Query("app_id") appId: String, @Query("ingr") ingredient: String): Observable<RecipeFoodDatabaseResponse>
}