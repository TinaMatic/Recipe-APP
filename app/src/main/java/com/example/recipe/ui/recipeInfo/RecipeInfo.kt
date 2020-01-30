package com.example.recipe.ui.recipeInfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipe.R
import com.example.recipe.model.recipeSearchModel.HitsSearch
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recipe_info.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.recipe_info.*

class RecipeInfo : AppCompatActivity() {

    private lateinit var recipeInfo: HitsSearch
    private lateinit var recipeImage: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_info)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(intent.hasExtra("recipe")){
            recipeInfo = intent.getSerializableExtra("recipe") as HitsSearch

            recipeImage = recipeInfo.recipe.image!!

            Picasso.with(applicationContext)
                .load(recipeImage)
                .placeholder(R.drawable.recipes)
                .into(ivRecipeImage)

            supportActionBar?.title = recipeInfo.recipe.label

            tvRecipeTitle.text = recipeInfo.recipe.label
        }
    }
}
