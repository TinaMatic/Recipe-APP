package com.example.recipe.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeTable(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "label") var label: String,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "isFavourite") var isFavourite: Int = 0)