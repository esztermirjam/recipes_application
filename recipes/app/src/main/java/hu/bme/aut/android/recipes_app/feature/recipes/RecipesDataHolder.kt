package hu.bme.aut.android.recipes_app.feature.recipes

import hu.bme.aut.android.recipes_app.models.RecipeDetails
import hu.bme.aut.android.recipes_app.models.RecipeDetailsList

interface RecipesDataHolder {
    fun getRecipeMain(): RecipeDetailsList?
}