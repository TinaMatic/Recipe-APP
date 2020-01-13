package com.example.recipe.model

data class RecipeResponse(val q: String?, val from: Int?, val to: Int?, val more: Boolean?,
                          val count: Int?, val hits: List<Hits>?)