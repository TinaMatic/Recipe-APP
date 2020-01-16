package com.example.recipe.di.module

import android.content.Context
import com.example.recipe.service.RecipeApi
import com.example.recipe.service.RetrofitInstance
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    private val BASE_URL: String = "https://api.edamam.com/"

    @Provides
    @Singleton
    fun provideContext(): Context{
        return context
    }

    @Provides
    @Singleton
    fun provideOKHttpClient(): OkHttpClient{
        return okhttp3.OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): RecipeApi{
        return retrofit.create(RecipeApi::class.java)
    }
}