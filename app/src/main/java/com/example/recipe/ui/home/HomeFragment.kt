package com.example.recipe.ui.home


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.recipe.R
import com.example.recipe.data.RecipeRepository
import com.example.recipe.model.RecipeResponse
import com.example.recipe.service.RecipeApi
import com.example.recipe.service.RetrofitInstance
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header_main.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    
    private val repository: RecipeRepository = RecipeRepository(RetrofitInstance.getService())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var allLabels = ""

        compositeDisposable.add(
            repository.getRecipes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapIterable {
                Log.d("Hits", it.hits.toString())
                it.hits
            }
            .subscribe ({
                Log.d("api response", it.toString())
                allLabels += "${it.recipe.label} "
                textViewHome.text = allLabels

                Log.d("Image", it.recipe.image)
                Picasso.with(context)
                    .load(it.recipe.image)
                    .placeholder(R.drawable.recipes)
                    .into(imageHomeFragment)
            },{error->
                Log.d("Error for api call", error.toString())
            }))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}
