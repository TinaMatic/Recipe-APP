package com.example.recipe.ui.search

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

class SearchViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var recipeSearchRepository: RecipeSearchRepository

    var recipes: ArrayList<HitsSearch> = arrayListOf()
    var recipeLiveData : MutableLiveData<List<HitsSearch>> = MutableLiveData()

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getAllRecipes(searchedRecipe: String){
        recipes.clear()
        compositeDisposable.add(
            recipeSearchRepository.getRecipesResponse(searchedRecipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable {
                    it.hits
                }
                .subscribe ({
                    recipes.add(it)
                },{error->
                    Log.d("Error for api call", error.toString())
                },{
                    recipeLiveData.postValue(recipes)
                }))

//        return recipeLiveData
//        return recipeSearchRepository.recipeLiveData
    }

    fun clear(){
        compositeDisposable.clear()
//        recipeSearchRepository.clear()
    }
}