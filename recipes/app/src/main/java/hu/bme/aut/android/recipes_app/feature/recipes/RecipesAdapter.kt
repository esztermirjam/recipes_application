package hu.bme.aut.android.recipes_app.feature.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hu.bme.aut.android.recipes_app.R
import hu.bme.aut.android.recipes_app.models.RecipeDetails
import hu.bme.aut.android.recipes_app.models.RecipeDetailsList

class RecipesAdapter(private val listener: OnRecipeSelectedListener) : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {

    private val recipesList: MutableList<RecipeDetails?> = mutableListOf()

    interface OnRecipeSelectedListener {
        fun onRecipeSelected(recipe: RecipeDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipes, parent, false)
        return RecipesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.bind(recipesList)
    }

    override fun getItemCount(): Int = recipesList.size

    fun setRecipes(newRecipes: RecipeDetailsList) {
        recipesList.clear()
        if (newRecipes != null) {
            newRecipes.recipes.toCollection(recipesList)
        }
        notifyDataSetChanged()
    }

    inner class RecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val recipe = recipesList[position]
                    if (recipe != null) {
                        listener.onRecipeSelected(recipe)
                    }
                }
            }
        }

        fun bind(recipeList: MutableList<RecipeDetails?>) {
            val context = itemView.context
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 0, 0, 16)
            for (recipe in recipeList) {
                val recipeLayout = LayoutInflater.from(context).inflate(R.layout.item_recipes, null)
                recipeLayout.findViewById<TextView>(R.id.tvName).text = recipe?.name
                recipeLayout.findViewById<TextView>(R.id.tvIngredients).text =
                    recipe?.ingredients?.joinToString(separator = "\n")
                Glide.with(context)
                    .load("https://cdn.dummyjson.com/recipe-images/${recipe?.id}.webp")
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(recipeLayout.findViewById(R.id.ivIcon))
                recipeLayout.layoutParams = layoutParams
                (itemView as ViewGroup).addView(recipeLayout)
            }
        }
    }
}