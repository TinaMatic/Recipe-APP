package com.example.recipe.ui.favourites

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.data.RoomRepository
import com.example.recipe.database.RecipeDao
import com.example.recipe.database.RecipeTable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_favourites.*
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(): ViewModel() {

    var favouriteRecipeLiveData: MutableLiveData<List<RecipeTable>> = MutableLiveData()
    var favouriteError: MutableLiveData<Boolean> = MutableLiveData()
    var favouriteLoading: MutableLiveData<Boolean> = MutableLiveData()

    private var compositeDisposable = CompositeDisposable()

    fun showFavourites(database: RecipeDao){

        favouriteLoading.value = true

        compositeDisposable.add(
            database.showFavourites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    favouriteRecipeLiveData.postValue(it)
                },{
                    favouriteError.value = true
                },{
                    favouriteError.value = false
                    favouriteLoading.value = false
                    }
                )
        )
    }

    fun removeFavourite(recipe: RecipeTable, database: RecipeDao): Observable<Boolean>{
        return RoomRepository.removeFavourite(recipe, database)
    }

    fun clear(){
        compositeDisposable.clear()
    }
}