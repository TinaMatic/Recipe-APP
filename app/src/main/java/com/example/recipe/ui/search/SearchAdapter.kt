package com.example.recipe.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe.R
import com.example.recipe.model.recipeSearchModel.HitsSearch
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipe_list_item.view.*

class SearchAdapter(val context: Context, val recipeHitsList: List<HitsSearch>):
    RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return recipeHitsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipeHitsList[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(hitsSearch: HitsSearch){
            Picasso.with(context)
                .load(hitsSearch.recipe.image)
                .placeholder(R.drawable.recipes)
                .into(itemView.imageViewRecipe)

            itemView.recipeTitle.text = hitsSearch.recipe.label
        }
    }
}