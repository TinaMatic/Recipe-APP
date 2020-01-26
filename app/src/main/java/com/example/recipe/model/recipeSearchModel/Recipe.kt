package com.example.recipe.model.recipeSearchModel

import com.example.recipe.model.recipeSearchModel.Digest
import com.example.recipe.model.recipeSearchModel.Ingredient
import com.example.recipe.model.recipeSearchModel.NutrientInfo

data class Recipe (val uri: String?, val label: String, val image: String?, val source: String?,
                   val url: String?, val shareAs: String?, val yield: Int?, val dietLabels: List<String>?,
                   val healthLabels: List<String>?, val cautions: List<String>?,
                   val ingredientLines: List<String>?, val ingredients: List<Ingredient>?,
                   val calories: Double?, val totalWeight: Double?, val totalTime: Double?,
                   val totalNutrients: NutrientInfo?, val totalDaily: NutrientInfo?,
                   val digest: List<Digest>?)