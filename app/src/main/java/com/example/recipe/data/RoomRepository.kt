package com.example.recipe.data

import android.util.Log
import com.example.recipe.database.RecipeDao
import com.example.recipe.database.RecipeTable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object RoomRepository {

    var database: RecipeDao? = null
    private var compositeDisposable = CompositeDisposable()

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

    private fun insertFavouriteIfNotDuplicate(recipe: RecipeTable, database: RecipeDao){
        compositeDisposable.add(
            database.insertFavourite(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("Inserted row", "recipe was inserted")
                },{
                    Log.d("Error inserting", it.toString())
                })
        )
    }


    fun insertFavourite(recipe: RecipeTable, database: RecipeDao): Observable<Boolean>{

        return Observable.create { emitter ->
            database.ifFavouriteExists(recipe.label, recipe.image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isEmpty()){
                        insertFavouriteIfNotDuplicate(recipe, database)
                        emitter.onNext(true)
                    }
                },{
                    Log.d("Error inserting", it.toString())
                    emitter.onNext(false)
                })
        }
    }

    fun updateFavourites(recipe: RecipeTable, database: RecipeDao, isFavourite: Boolean){
//
//        return Observable.create { emitter ->
//            if(isFavourite){
//                insertFavourite(recipe, database)
//                emitter.onNext()
//            }else{
//                removeFavourite(recipe, database).subscribe {
//
//                }
//            }
//        }


//        compositeDisposable.add(
//            database.ifFavouriteExists(recipe.label, recipe.image)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    if(it.isEmpty()){
//                        insertFavourite(recipe, database)
//                    }
//                },{
//                    Log.d("Error inserting", it.toString())
//                })
//        )
    }
}