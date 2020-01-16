package com.example.recipe.di.component

import android.app.Application
import com.example.recipe.MainActivity
import com.example.recipe.di.module.ApplicationModule
import com.example.recipe.ui.home.HomeFragment
import com.example.recipe.ui.search.SearchFragment
import com.example.recipe.settings.SettingsFragment
import com.example.recipe.ui.showAllRecipe.ShowAllRecipeFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton
//
@Singleton
@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class])
interface ApplicationComponent {

    fun inject(act: MainActivity)
    fun inject(frag: HomeFragment)
    fun inject(frag: SearchFragment)
    fun inject(frag: SettingsFragment)
    fun inject(frag: ShowAllRecipeFragment)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application) : Builder
        fun applicationModule(applicationModule: ApplicationModule) :Builder
        fun build(): ApplicationComponent
    }
}