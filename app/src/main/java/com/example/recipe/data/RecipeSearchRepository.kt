package com.example.recipe.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.recipe.constants.Constants
import com.example.recipe.model.recipeSearchModel.HitsSearch
import com.example.recipe.model.recipeSearchModel.RecipeSearchResponse
import com.example.recipe.service.RecipeApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecipeSearchRepository @Inject constructor(private val api: RecipeApi) {


    var recipeLiveData : MutableLiveData<List<HitsSearch>> = MutableLiveData()
    var recipes: ArrayList<HitsSearch> = arrayListOf()

    init {

//        compositeDisposable.add(
//            getRecipesResponse()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMapIterable {
//                    it.hits
//                }
//                .subscribe ({
//                    recipes.add(it)
//                },{error->
//                    Log.d("Error for api call", error.toString())
//                },{
//                    recipeLiveData.postValue(recipes)
//                }))
    }

    fun getRecipesResponse(searchedRecipe: String): Observable<RecipeSearchResponse>{

        return if(searchedRecipe.isEmpty()){
            api.getSearchedRecipes(Constants.API_KEY_RECIPE_SEARCH, Constants.APP_ID_RECIPE_SEARCH, "chicken")
        }else{
            api.getSearchedRecipes(Constants.API_KEY_RECIPE_SEARCH, Constants.APP_ID_RECIPE_SEARCH, searchedRecipe)
        }

    }

//    private fun getSearchedRecipe(searchedRecipe: String): Observable<RecipeSearchResponse>{
//        return api.getSearchedRecipes(Constants.API_KEY_RECIPE_SEARCH, Constants.APP_ID_RECIPE_SEARCH, searchedRecipe)
//    }


    fun clear(){
//        compositeDisposable.clear()
    }
}