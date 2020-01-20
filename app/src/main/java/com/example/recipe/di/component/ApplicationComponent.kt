package com.example.recipe.di.component

import android.app.Application
import com.example.recipe.RecipeApplication
import com.example.recipe.di.module.ActivityModule
import com.example.recipe.ui.main.MainActivity
import com.example.recipe.di.module.ApplicationModule
import com.example.recipe.di.module.FragmentModule
import com.example.recipe.di.module.ViewModelModule
import com.example.recipe.ui.home.HomeFragment
import com.example.recipe.ui.search.SearchFragment
import com.example.recipe.settings.SettingsFragment
import com.example.recipe.ui.showAllRecipe.ShowAllRecipeFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, ActivityModule::class,
    ViewModelModule::class, FragmentModule::class])

interface ApplicationComponent: AndroidInjector<RecipeApplication> {

//    fun inject(act: MainActivity)
//    fun inject(frag: HomeFragment)
//    fun inject(frag: SearchFragment)
//    fun inject(frag: SettingsFragment)
//    fun inject(frag: ShowAllRecipeFragment)

    override fun inject(recipeApplication: RecipeApplication)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application) : Builder
        fun applicationModule(applicationModule: ApplicationModule) : Builder
        fun build(): ApplicationComponent
    }
}