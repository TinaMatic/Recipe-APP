package com.example.recipe.ui.search


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.recipe.R
import com.example.recipe.base.ViewModelFactory
import com.example.recipe.model.recipeSearchModel.HitsSearch
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var searchViewModel : SearchViewModel
    private var searchAdapter : SearchAdapter? = null
    var searchedRecipeData : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        clNoResultSearch.findViewById<TextView>(R.id.tvNoData).text = getString(R.string.no_recipe)

        btnSearch.setOnClickListener {
            searchedRecipeData = etSearchRecipe.text.toString()
            searchViewModel.getAllRecipes(searchedRecipeData!!)
            searchAdapter?.notifyDataSetChanged()

        }

        getAllRecipes()
    }

    fun getAllRecipes(){
        searchViewModel.recipeLiveData.observe(this, Observer {
                showRecipes(it)
                showProgressBar(false)
                searchAdapter?.notifyDataSetChanged()
        })

        searchViewModel.recipeLoadingError.observe(this, Observer {isError->
            if(isError){
                Toast.makeText(context, "Something wrong happend", Toast.LENGTH_LONG).show()
            }
        })

        searchViewModel.recipeLoading.observe(this, Observer {isLoading->
            if(isLoading != null){
                showProgressBar(isLoading)
                if(isLoading){
                    recyclerViewSearch.visibility = View.GONE
                    clNoResultSearch.visibility = View.GONE
                }
            }

        })
    }

    fun showProgressBar(show: Boolean){
        if (progressBarSearch != null){
            if (show){
                progressBarSearch.visibility = View.VISIBLE
            }else{
                progressBarSearch.visibility = View.GONE
            }
        }
    }

    fun showRecipes(hitSearchList: List<HitsSearch>){

        if(hitSearchList.isEmpty()){
            recyclerViewSearch?.visibility = View.GONE
            clNoResultSearch?.visibility = View.VISIBLE
        }else{
            recyclerViewSearch?.visibility = View.VISIBLE
            clNoResultSearch?.visibility = View.GONE

            searchAdapter = SearchAdapter(context!!, hitSearchList)
            recyclerViewSearch?.layoutManager = LinearLayoutManager(context)
            recyclerViewSearch?.setHasFixedSize(true)
            recyclerViewSearch?.adapter = searchAdapter
        }

        searchAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchViewModel.clear()
    }
}
