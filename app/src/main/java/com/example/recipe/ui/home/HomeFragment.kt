package com.example.recipe.ui.home


import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recipe.R
import com.example.recipe.database.RecipeDao
import com.example.recipe.database.RecipeDatabase
import com.example.recipe.database.RecipeTable
import com.example.recipe.model.recipeSearchModel.HitsSearch
import com.example.recipe.util.IngredientUtil
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : DaggerFragment(), OnFavouriteItemClickHome {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel

    private var homeAdapter: HomeAdapter? = null

    private var randomIngredient: String? = null

    var database: RecipeDao? = null

    var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        database = RecipeDatabase.getInstance(context!!).recipeDao()

        for (i in 0..4) {
            val randomIngredientPosition = (0..IngredientUtil.INGREDIENT.size - 1).random()
            Log.d("Random position", randomIngredientPosition.toString())

            randomIngredient = IngredientUtil.INGREDIENT[randomIngredientPosition]
            Log.d("Ingredient ", randomIngredient.toString())

            Handler().postDelayed({
                getRecipes(randomIngredient!!)
            }, 2000)
        }


        swipeLayout.setColorSchemeResources(R.color.colorPrimary)
        swipeLayout.setOnRefreshListener {
            //            getRecipes()

            for (i in 0..3) {
                val randomIngredientPosition = (0..IngredientUtil.INGREDIENT.size - 1).random()
                Log.d("Random position", randomIngredientPosition.toString())

                randomIngredient = IngredientUtil.INGREDIENT[randomIngredientPosition]
                Log.d("Ingredient ", randomIngredient.toString())

                Handler().postDelayed({
                    getRecipes(randomIngredient!!)
                }, 2000)
            }

            swipeLayout.isRefreshing = false
        }

    }

    private fun getRecipes(searchedIngredient: String) {
        homeViewModel.getAllRecipes(searchedIngredient)

        homeViewModel.recipeLiveData.observe(this, Observer {
            showRecipe(it)
            showProgressBar(false)
            homeAdapter?.notifyDataSetChanged()
        })

        homeViewModel.recipeError.observe(this, Observer { isError ->
            if (isError) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        })

        homeViewModel.recipeLoading.observe(this, Observer { isLoading ->
            if (isLoading != null) {
                showProgressBar(isLoading)
            }
        })
    }

    private fun showProgressBar(show: Boolean) {
        if (progressBarHome != null) {
            if (show) {
                progressBarHome.visibility = View.VISIBLE
            } else {
                progressBarHome.visibility = View.GONE
            }
        }
    }

    private fun showRecipe(hitsSearchList: List<HitsSearch>) {
        homeAdapter = HomeAdapter(context!!, hitsSearchList, this)
        val spacesItemDecoration = SpacesItemDecoration(30)

        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerViewHome?.layoutManager = (StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))
        } else {
            recyclerViewHome?.layoutManager = (GridLayoutManager(context, 4))
        }

        recyclerViewHome?.itemAnimator = DefaultItemAnimator()
//        recyclerViewHome?.addItemDecoration(spacesItemDecoration)
        recyclerViewHome?.adapter = homeAdapter

        homeAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.clear()
        compositeDisposable.clear()
    }

    override fun updateFavouriteClick(recipeTable: RecipeTable, isFavourite: Boolean) {
        homeViewModel.updateFavourites(recipeTable, isFavourite, database!!)
    }

    override fun insertFavourite(recipe: RecipeTable) {
        compositeDisposable.add(
            homeViewModel.insertFavourite(recipe, database!!).subscribe {
                if (it){
                    Toast.makeText(context, "Favourite successfully added", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"Something went wrong with adding favourite", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    override fun removeFavourite(recipe: RecipeTable) {
        compositeDisposable.add(
            homeViewModel.removeFavourite(recipe, database!!).subscribe {
                if(it){
                    Toast.makeText(context,"Favourite successfully removed", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"Something went wrong with removing favourite", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}
