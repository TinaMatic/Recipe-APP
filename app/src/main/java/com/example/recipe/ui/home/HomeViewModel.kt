package com.example.recipe.ui.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.data.RecipeSearchRepository
import com.example.recipe.database.RecipeDao
import com.example.recipe.database.RecipeDatabase
import com.example.recipe.database.RecipeTable
import com.example.recipe.model.recipeSearchModel.HitsSearch
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var recipeSearchRepository: RecipeSearchRepository

    private var recipes: ArrayList<HitsSearch> = arrayListOf()

    var recipeLiveData: MutableLiveData<List<HitsSearch>> = MutableLiveData()
    var recipeError: MutableLiveData<Boolean> = MutableLiveData()
    var recipeLoading: MutableLiveData<Boolean> = MutableLiveData()

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getAllRecipes(searchedRecipe: String) {

        recipes.clear()
        recipeLoading.value = true

        compositeDisposable.add(
            recipeSearchRepository.getRecipesResponse(searchedRecipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable {
                    it.hits
                }
                .subscribe({
                    recipes.add(it)
                    recipeError.value = false
                }, { error ->
                    Log.d("Error for api call", error.toString())
                    recipeError.value = true
                    recipeLoading.value = false
                }, {
                    recipeLiveData.postValue(recipes)
                    recipeError.value = false
                    recipeLoading.value = false
                })
        )
    }

    fun updateFavourites(recipe: RecipeTable, isFavourite: Boolean, database: RecipeDao){
        if(isFavourite){
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
        }else{
            compositeDisposable.add(
                database.ifFavouriteExists(recipe.label, recipe.image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    if (it.isNotEmpty()){
                        database.deleteFavourite(recipe)
                        Log.d("Delete favourite", "Favourite is deleted")
                    }
                },{
//                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                })
            )
        }
    }

    fun clear() {
        compositeDisposable.clear()
    }

}