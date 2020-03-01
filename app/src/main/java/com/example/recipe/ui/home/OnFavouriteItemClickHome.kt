package com.example.recipe.ui.home

import com.example.recipe.database.RecipeTable

interface OnFavouriteItemClickHome {
    fun updateFavouriteClick(recipeTable: RecipeTable, isFavourite: Boolean)
    fun insertFavourite(recipe: RecipeTable)
    fun removeFavourite(recipe: RecipeTable)
}