package com.example.recipe.ui.search


import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var searchViewModel: SearchViewModel
    private var searchAdapter: SearchAdapter? = null
    var searchedRecipeData: String? = null

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonClickStream = createButtonClickSearch()
        val textChangeStream = createTextChangeSearch()
        val searchTextObservable = Observable.merge<String>(buttonClickStream, textChangeStream)

        searchViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        clNoResultSearch.findViewById<TextView>(R.id.tvNoData).text = getString(R.string.no_recipe)

        compositeDisposable.add(
            searchTextObservable.observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    showProgressBar(true)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Handler().postDelayed({
                        searchViewModel.getAllRecipes(it)
                        getAllRecipes()
                    }, 2000)
                }
        )
    }

    fun getAllRecipes() {
        searchViewModel.recipeLiveData.observe(this, Observer {
            showRecipes(it)
            showProgressBar(false)
            searchAdapter?.notifyDataSetChanged()
        })

        searchViewModel.recipeLoadingError.observe(this, Observer { isError ->
            if (isError) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        })

        searchViewModel.recipeLoading.observe(this, Observer { isLoading ->
            if (isLoading != null) {
                showProgressBar(isLoading)
                if (isLoading) {
                    recyclerViewSearch.visibility = View.GONE
                    clNoResultSearch.visibility = View.GONE
                }
            }
        })
    }

    fun showProgressBar(show: Boolean) {
        if (progressBarSearch != null) {
            if (show) {
                progressBarSearch.visibility = View.VISIBLE
            } else {
                progressBarSearch.visibility = View.GONE
            }
        }
    }

    fun showRecipes(hitSearchList: List<HitsSearch>) {

        if (hitSearchList.isEmpty()) {
            recyclerViewSearch?.visibility = View.GONE
            clNoResultSearch?.visibility = View.VISIBLE
        } else {
            recyclerViewSearch?.visibility = View.VISIBLE
            clNoResultSearch?.visibility = View.GONE

            searchAdapter = SearchAdapter(context!!, hitSearchList)
            recyclerViewSearch?.layoutManager = LinearLayoutManager(context)
            recyclerViewSearch?.setHasFixedSize(true)
            recyclerViewSearch?.adapter = searchAdapter
        }

        searchAdapter?.notifyDataSetChanged()
    }

    fun createButtonClickSearch(): Observable<String> {
        return Observable.create { emitter ->
            btnSearch.setOnClickListener {
                emitter.onNext(etSearchRecipe.text.toString())
            }
            emitter.setCancellable {
                btnSearch.setOnClickListener(null)
            }
        }
    }

    fun createTextChangeSearch(): Observable<String> {
        val textChangeObservable = Observable.create<String> { emitter ->
            val textWatcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.toString()?.let {
                        emitter.onNext(it)
                    }
                }
            }
            etSearchRecipe.addTextChangedListener(textWatcher)

            emitter.setCancellable {
                etSearchRecipe.removeTextChangedListener(textWatcher)
            }
        }

        return textChangeObservable.filter {
            it.length >= 2
        }.debounce(1500, TimeUnit.MILLISECONDS)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchViewModel.clear()
    }
}
