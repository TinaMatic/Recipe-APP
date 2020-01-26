package com.example.recipe.model.recipeSearchModel

data class RecipeSearchResponse(val q: String?, val from: Int?, val to: Int?, val more: Boolean?,
                                val count: Int?, val hits: List<HitsSearch>?)