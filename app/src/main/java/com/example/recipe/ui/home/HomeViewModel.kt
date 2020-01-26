package com.example.recipe.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.data.HomeRecipeRepository
import com.example.recipe.model.recipeSearchModel.HitsSearch
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var homeRecipeRepository: HomeRecipeRepository

    fun getAllRecipes(): LiveData<List<HitsSearch>>{
        return homeRecipeRepository.recipeLiveData
    }

    fun clear(){
        homeRecipeRepository.clear()
    }
}