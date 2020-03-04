package com.example.recipe.data

import android.util.Log
import com.example.recipe.database.RecipeDao
import com.example.recipe.database.RecipeTable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object RoomRepository {

    var database: RecipeDao? = null
    private var compositeDisposable = CompositeDisposable()
    var id: Long? = null

    fun removeFavourite(recipe: RecipeTable, database: RecipeDao): Observable<Boolean> {
        return Observable.create<Boolean> {emitter->
            database.deleteFavourite(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("Favourite removed", "removed")
                    emitter.onNext(true)
                }, {
                    emitter.onNext(false)
                })
        }
    }

    fun insertFavourite(recipe: RecipeTable, database: RecipeDao): Observable<Boolean>{
        return Observable.create<Boolean> { emitter ->
            database.insertFavourite(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("Inserted row", "recipe was inserted")
                    emitter.onNext(true)
                },{
                    Log.d("Error inserting", it.toString())
                    emitter.onNext(false)
                })
        }
    }

    fun removeFavouriteById(database: RecipeDao, label: String, image: String): Observable<Boolean>{
        return Observable.create<Boolean> {emitter ->
            database.ifFavouriteExists(label, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({recipeExist->
                    if(recipeExist.id != null){
                        removeExistingFavouriteById(recipeExist.id!!, database)
                        emitter.onNext(true)
                    }
                },{
                    emitter.onNext(false)
                    Log.d("Error removing", it.toString())
                })
        }
    }

    private fun removeExistingFavouriteById(id: Long, database: RecipeDao){
        compositeDisposable.add(
            database.deleteFavouriteById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    Log.d("Favourite removed", "removed")
                },{
                    Log.d("Error when removing", it.toString())
                })
        )
    }
}