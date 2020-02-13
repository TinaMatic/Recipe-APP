package com.example.recipe.ui.recipeInfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.recipe.R
import com.example.recipe.model.recipeSearchModel.HitsSearch
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recipe_info.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.recipe_info.*
import kotlin.math.roundToLong

class RecipeInfo : AppCompatActivity() {

    private lateinit var recipeInfo: HitsSearch
    private lateinit var recipeImage: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_info)

        setSupportActionBar(toolbarRecipeInfo)

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

            //check if there are any values for dietLabels
            if(recipeInfo.recipe.dietLabels!!.isNotEmpty()){
                var dietLabels = ""
                recipeInfo.recipe.dietLabels!!.forEach {
                    dietLabels += "$it, "
                }
                tvDietLabel.visibility = View.VISIBLE
                tvDiet.visibility = View.VISIBLE
                tvDiet.text = dietLabels
            }else{
                tvDietLabel.visibility = View.GONE
                tvDiet.visibility = View.GONE
            }

            //check if there are any values for healthLabels
            if(recipeInfo.recipe.healthLabels!!.isNotEmpty()){
                var healthLabels = ""
                recipeInfo.recipe.healthLabels!!.forEach {
                    healthLabels += "$it, "
                }
                tvHealthLabel.visibility = View.VISIBLE
                tvHealth.visibility = View.VISIBLE
                tvHealth.text = healthLabels
            }else{
                tvHealthLabel.visibility = View.GONE
                tvHealth.visibility = View.GONE
            }


            //check if there are any values for ingredientLines
            if(recipeInfo.recipe.ingredientLines!!.isNotEmpty()){
                var ingredientLines = ""
                recipeInfo.recipe.ingredientLines!!.forEach {
                    ingredientLines += "$it \n"
                }
                tvIngredientLabel.visibility = View.VISIBLE
                tvIngredientLines.visibility = View.VISIBLE
                tvIngredientLines.text = ingredientLines
            }else{
                tvIngredientLabel.visibility = View.GONE
                tvIngredientLines.visibility = View.GONE
            }

            //check if there are any value for calories
            if(recipeInfo.recipe.calories != null){
                tvCaloriesLabel.visibility = View.VISIBLE
                tvCalories.visibility = View.VISIBLE
                tvCalories.text = ((recipeInfo.recipe.calories!! * 10.00).roundToLong() /10.00).toString()
            }else{
                tvCaloriesLabel.visibility = View.GONE
                tvCalories.visibility = View.GONE
            }

            //check if there are any value for url
            if(recipeInfo.recipe.url != null){
                tvRecipeLink.visibility = View.VISIBLE
                tvRecipeLink.text = getString(R.string.link_text)

                tvRecipeLink.setOnClickListener {
                    val url = recipeInfo.recipe.url
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }
            }else{
                tvRecipeLink.visibility = View.GONE
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
