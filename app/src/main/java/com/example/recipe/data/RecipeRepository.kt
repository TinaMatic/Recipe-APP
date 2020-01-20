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
    private var recipes: ArrayList<Hits> = arrayListOf()

    fun getRecipesResponse(): Observable<RecipeResponse>{
        return api.getRecipes(Constants.API_KEY_RECIPE_SEARCH, Constants.APP_ID_RECIPE_SEARCH, Constants.Q_PARAMETER)
    }

    fun getRecipes(): MutableLiveData<List<Hits>>{
        compositeDisposable.add(
            getRecipesResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable {
                    Log.d("Hits", it.hits.toString())
                    it.hits
                }
                .subscribe ({
                    recipes.add(it)
                    Log.d("api response", it.toString())
//                    allLabels += "${it.recipe.label} "
//                    textViewHome.text = allLabels

                    Log.d("Image", it.recipe.image)
//                    Picasso.with(context)
//                        .load(it.recipe.image)
//                        .placeholder(R.drawable.recipes)
//                        .into(imageHomeFragment)
                },{error->
                    Log.d("Error for api call", error.toString())
                },{
                    recipeLiveData.postValue(recipes)
                }))

        return recipeLiveData
    }

    fun clear(){
        compositeDisposable.clear()
    }
}