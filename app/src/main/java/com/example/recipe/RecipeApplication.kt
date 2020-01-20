package com.example.recipe

import com.example.recipe.di.component.ApplicationComponent
import com.example.recipe.di.component.DaggerApplicationComponent
import com.example.recipe.di.module.ApplicationModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class RecipeApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        //Build app component
        val appComponent: ApplicationComponent = DaggerApplicationComponent.builder()
            .application(this)
            .applicationModule(ApplicationModule(applicationContext))
            .build()

        //inject application instance
        appComponent.inject(this)

        return appComponent
    }
}