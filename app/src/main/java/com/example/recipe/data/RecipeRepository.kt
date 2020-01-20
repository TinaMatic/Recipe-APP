package com.example.recipe.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.recipe.R
import com.example.recipe.constants.Constants
import com.example.recipe.model.Hits
import com.example.recipe.model.RecipeResponse
import com.example.recipe.service.RecipeApi
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val api: RecipeApi) {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var recipeLiveData : MutableLiveData<List<Hits>> = MutableLiveData()
    var recipes: ArrayList<Hits> = arrayListOf()

    init {
        compositeDisposable.add(
            getRecipesResponse()
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
    }

    fun getRecipesResponse(): Observable<RecipeResponse>{
        return api.getRecipes(Constants.API_KEY_RECIPE_SEARCH, Constants.APP_ID_RECIPE_SEARCH, Constants.Q_PARAMETER)
    }


    fun clear(){
        compositeDisposable.clear()
    }
}