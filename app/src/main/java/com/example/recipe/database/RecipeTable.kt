package com.example.recipe.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "recipes", indices = [Index(value = ["label", "image"], unique = true)])
class RecipeTable(
    @ColumnInfo(name = "label") var label: String,
    @ColumnInfo(name = "image") var image: String){

    @PrimaryKey(autoGenerate = true) var id: Long? = null
}