package com.example.recipe.database

import androidx.room.*
import com.example.recipe.model.recipeSearchModel.HitsSearch
import io.reactivex.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavourite(recipes: RecipeTable): Completable

    @Query("SELECT * FROM recipes")
    fun showFavourites(): Observable<List<RecipeTable>>

    @Delete
    fun deleteFavourite(recipeTable: RecipeTable): Completable

    @Query("DELETE FROM recipes WHERE id = :id")
    fun deleteFavouriteById(id: Long): Completable

    @Query("SELECT * FROM recipes WHERE label = :label AND image = :image")
    fun ifFavouriteExists(label: String, image: String): Observable<RecipeTable>
}