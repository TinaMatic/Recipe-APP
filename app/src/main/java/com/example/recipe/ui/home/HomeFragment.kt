package com.example.recipe.ui.home


import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager

import com.example.recipe.R
import com.example.recipe.base.ViewModelFactory
import com.example.recipe.model.Hits
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private lateinit var homeViewModel : HomeViewModel

    private var homeAdapter: HomeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        var allLabels = ""

        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        getRecipes()

        swipeLayout.setColorSchemeResources(R.color.colorPrimary)
        swipeLayout.setOnRefreshListener {
            getRecipes()
            swipeLayout.isRefreshing = false
        }

    }

    private fun getRecipes(){
        homeViewModel.getAllRecipes().observe(this, Observer {
            showRecipe(it)
            progressBarHome.visibility = View.GONE
        })
    }

    private fun showRecipe(hitsList: List<Hits>){
        homeAdapter = HomeAdapter(context!!, hitsList)

        if(this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerViewHome.layoutManager = (GridLayoutManager(context, 2))
        } else{
            recyclerViewHome.layoutManager = (GridLayoutManager(context, 4))
        }

        recyclerViewHome.itemAnimator = DefaultItemAnimator()
        recyclerViewHome.adapter = homeAdapter

        homeAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.clear()
    }
}
