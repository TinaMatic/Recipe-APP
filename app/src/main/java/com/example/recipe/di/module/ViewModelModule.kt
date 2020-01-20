package com.example.recipe.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe.base.ViewModelFactory
import com.example.recipe.di.annotation.ViewModelKey
import com.example.recipe.settings.SettingsViewModel
import com.example.recipe.ui.home.HomeViewModel
import com.example.recipe.ui.search.SearchViewModel
import com.example.recipe.ui.showAllRecipe.ShowAllRecipeViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShowAllRecipeViewModel::class)
    abstract fun bindShowAllRecipeViewModel(showAllRecipeViewModel: ShowAllRecipeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}