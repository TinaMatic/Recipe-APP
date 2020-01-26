package com.example.recipe.ui.home


import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recipe.R
import com.example.recipe.model.recipeSearchModel.HitsSearch
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

    private fun showRecipe(hitsSearchList: List<HitsSearch>){
        homeAdapter = HomeAdapter(context!!, hitsSearchList)
        val spacesItemDecoration = SpacesItemDecoration(10)

        if(this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
//            recyclerViewHome.layoutManager = (GridLayoutManager(context, 2))
            recyclerViewHome.layoutManager = (StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))
        } else{
            recyclerViewHome.layoutManager = (GridLayoutManager(context, 4))
        }

        recyclerViewHome.itemAnimator = DefaultItemAnimator()
        recyclerViewHome.addItemDecoration(spacesItemDecoration)
        recyclerViewHome.adapter = homeAdapter


        homeAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.clear()
    }
}
