package com.example.recipe.data

import com.example.recipe.constants.Constants
import com.example.recipe.model.RecipeResponse
import com.example.recipe.service.RecipeApi
import io.reactivex.Observable

class RecipeRepository(private val api: RecipeApi) {

    fun getRecipes(): Observable<RecipeResponse>{
        return api.getRecipes(Constants.API_KEY_RECIPE_SEARCH, Constants.APP_ID_RECIPE_SEARCH, Constants.Q_PARAMETER)
    }
}