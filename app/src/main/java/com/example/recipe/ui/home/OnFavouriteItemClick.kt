package com.example.recipe.ui.home

import com.example.recipe.database.RecipeTable

interface OnFavouriteItemClick {
    fun updateFavouriteClick(recipeTable: RecipeTable, isFavourite: Boolean)
}