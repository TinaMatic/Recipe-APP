package com.example.recipe.model.recipeSearchModel

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class HitsSearch(val recipe: Recipe, val bookmarked: String, val bought: Boolean): Serializable