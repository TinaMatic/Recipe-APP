package com.example.recipe.ui.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe.R
import com.example.recipe.database.RecipeTable
import com.example.recipe.ui.base.CheckableImageView
import com.example.recipe.ui.home.OnFavouriteItemClickHome
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipe_list_item.view.*

class FavouritesAdapter(private val context: Context,private val favourites: List<RecipeTable>,
                        val onFavouriteItemClick: OnFavouriteItemClick
):
    RecyclerView.Adapter<FavouritesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return favourites.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favourites[position])

        holder.itemView.ivFavouriteRecipe.setOnClickListener {
            (it as CheckableImageView).isChecked = false
            onFavouriteItemClick.removeFavourite(favourites[position])
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(favourite: RecipeTable){
            Picasso.with(context)
                .load(favourite.image)
                .placeholder(R.drawable.recipes)
                .into(itemView.imageViewRecipe)

            itemView.recipeTitle.text = favourite.label

            itemView.ivFavouriteRecipe.isChecked = true
        }
    }
}