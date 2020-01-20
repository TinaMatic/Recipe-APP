package com.example.recipe.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory

    @Inject
    constructor(private val viewModelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>):
        ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        viewModelMap.let {
            it[modelClass]?.get()?.let {viewModel ->
                return viewModel as T
            }
            throw IllegalArgumentException("Unknown ViewModel Type")
        }
    }
}