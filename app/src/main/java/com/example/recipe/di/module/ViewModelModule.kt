package com.example.recipe.di.module

import androidx.lifecycle.ViewModel
import com.example.recipe.ui.home.HomeViewModel
import dagger.Module
import dagger.Provides

@Module(includes = [ApplicationModule::class])
class ViewModelModule {

//    @Provides
//    fun provideHomeViewModel(): HomeViewModel{
//        return HomeViewModel()
//    }
}