package com.example.recipe.ui.favourites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.database.RecipeDao
import com.example.recipe.database.RecipeTable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_favourites.*
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(): ViewModel() {

    var favouriteRecipeLiveData: MutableLiveData<List<RecipeTable>> = MutableLiveData()
    var favouriteError: MutableLiveData<Boolean> = MutableLiveData()
    var favouriteLoading: MutableLiveData<Boolean> = MutableLiveData()

    private var favouriteRecipes: ArrayList<RecipeTable> = arrayListOf()

    private var compositeDisposable = CompositeDisposable()

    fun showFavourites(database: RecipeDao){
        favouriteRecipes.clear()

        favouriteLoading.value = true

        compositeDisposable.add(
            database.showFavourites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    favouriteRecipeLiveData.postValue(it)
//                    it.forEach {
//                        favouriteRecipes.add(it)
//                        favouriteError.value = false
//
//                    }
                },{
                    favouriteError.value = true
//                    favouriteLoading.value = false
                }
                    ,{
//                    favouriteRecipeLiveData.postValue(favouriteRecipes)
                    favouriteError.value = false
                    favouriteLoading.value = false
                }
                )
        )
    }

    fun clear(){
        compositeDisposable.clear()
    }
}