package com.example.recipe.database

import androidx.room.*
import com.example.recipe.model.recipeSearchModel.HitsSearch
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavourite(recipes: RecipeTable): Completable

    @Query("SELECT * FROM recipes")
    fun showFavourites(): Observable<List<RecipeTable>>

    @Delete
    fun deleteFavourite(recipeTable: RecipeTable): Completable

    @Query("SELECT * FROM recipes WHERE label = :label AND image = :image")
    fun ifFavouriteExists(label: String, image: String): Maybe<List<RecipeTable>>
}