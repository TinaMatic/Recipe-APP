package com.example.recipe.ui.home

import com.example.recipe.database.RecipeTable

interface OnFavouriteItemClickHome {
    fun insertFavourite(recipe: RecipeTable)
    fun removeFavourite(label: String, image: String)
}