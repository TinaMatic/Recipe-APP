package com.example.recipe.database

import androidx.room.*
import com.example.recipe.model.recipeSearchModel.HitsSearch

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllRecipes(recipes: List<RecipeTable>)

    @Update
    fun updateFavourite(recipe: RecipeTable): Int

    @Query("SELECT label, image FROM recipes WHERE isFavourite = 1 AND id LIKE :id")
    fun isFavourite(id: Long): Int
}