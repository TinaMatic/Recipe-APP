package com.example.recipe.model.recipeSearchModel

import java.io.Serializable

data class HitsSearch(val recipe: Recipe, val bookmarked: String, val bought: Boolean): Serializable