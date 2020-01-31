package com.example.recipe.di.module

import com.example.recipe.settings.SettingsFragment
import com.example.recipe.ui.home.HomeFragment
import com.example.recipe.ui.search.SearchFragment
import com.example.recipe.ui.favourites.FavouritesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun provideSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun provideSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun provideFavouriteFragment(): FavouritesFragment
}