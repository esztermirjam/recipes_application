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
import hu.bme.aut.android.recipes_app.databinding.ItemRecipesBinding
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
        val item = recipesList[position]
        holder.bind(item)
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
        var binding = ItemRecipesBinding.bind(itemView)
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val recipe = recipesList[position]
                    if (recipe != null) {
                        listener.onRecipeSelected(recipe)
                    }
                }
            }
        }

        fun bind(recipe: RecipeDetails?) {
            val context = itemView.context
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 0, 0, 16)
            binding.tvName.text = recipe?.name
            binding.tvIngredients.text = recipe?.ingredients?.joinToString(separator = "\n - ", prefix = "- ")
            Glide.with(context)
                .load(recipe?.image)
                .transition(DrawableTransitionOptions().crossFade())
                .into((binding.ivIcon))
        }
    }
}