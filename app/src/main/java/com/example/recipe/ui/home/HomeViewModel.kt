package com.example.recipe.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.data.RecipeSearchRepository
import com.example.recipe.model.recipeSearchModel.HitsSearch
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

    fun clear() {
        compositeDisposable.clear()
    }
}