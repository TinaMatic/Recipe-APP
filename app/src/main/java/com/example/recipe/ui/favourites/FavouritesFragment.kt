package com.example.recipe.ui.favourites


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.recipe.R
import com.example.recipe.database.RecipeDao
import com.example.recipe.database.RecipeDatabase
import com.example.recipe.database.RecipeTable
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_favourites.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class FavouritesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var favouriteViewModel: FavouritesViewModel

    private var favouriteAdapter: FavouritesAdapter? = null

    var database: RecipeDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteViewModel = ViewModelProviders.of(this, viewModelFactory).get(FavouritesViewModel::class.java)

        database = RecipeDatabase.getInstance(context!!).recipeDao()

        getAllFavourites()

    }

    private fun getAllFavourites(){
        favouriteViewModel.showFavourites(database!!)

        favouriteViewModel.favouriteRecipeLiveData.observe(this, Observer {
            showProgressBar(false)
            showFavourite(it)
            favouriteAdapter?.notifyDataSetChanged()
        })

        favouriteViewModel.favouriteError.observe(this, Observer {isError->
            if(isError){
                showProgressBar(false)
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        })

        favouriteViewModel.favouriteLoading.observe(this, Observer {isLoading->
            if(isLoading != null){
                showProgressBar(isLoading)
                if(isLoading){
                    favouritesRecyclerView.visibility = View.GONE
                    clNoResultFavourite.visibility = View.GONE
                }
            }

        })
    }


    private fun showFavourite(favourites: List<RecipeTable>){

        if(favourites.isEmpty()){
            favouritesRecyclerView?.visibility = View.GONE
            clNoResultFavourite?.visibility = View.VISIBLE
        } else{
            favouritesRecyclerView?.visibility = View.VISIBLE
            clNoResultFavourite?.visibility = View.GONE

            favouriteAdapter = FavouritesAdapter(context!!, favourites)
            favouritesRecyclerView.layoutManager = LinearLayoutManager(context)
            favouritesRecyclerView.setHasFixedSize(true)
            favouritesRecyclerView.adapter = favouriteAdapter
        }

        favouriteAdapter?.notifyDataSetChanged()
    }

    private fun showProgressBar(show: Boolean){
        if(favouritesProgressBar != null){
            if(show){
                favouritesProgressBar.visibility = View.VISIBLE
            } else{
                favouritesProgressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favouriteViewModel.clear()
    }
}
