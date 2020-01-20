package com.example.recipe.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.data.RecipeRepository
import com.example.recipe.model.Hits
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var recipeRepository: RecipeRepository

    fun getAllRecipes(): LiveData<List<Hits>>{
        return recipeRepository.getRecipes()
    }

    fun clear(){
        recipeRepository.clear()
    }
}