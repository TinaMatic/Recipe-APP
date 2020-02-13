package com.example.recipe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecipeTable::class], version = 1)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object{
        private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context): RecipeDatabase{
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        RecipeDatabase::class.java, "recipes.db")
                        .build()
                }
            }
            return INSTANCE as RecipeDatabase
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}