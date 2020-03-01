package com.example.recipe.ui.favourites

import com.example.recipe.database.RecipeTable

interface OnFavouriteItemClick {
    fun removeFavourite(recipe: RecipeTable)
}