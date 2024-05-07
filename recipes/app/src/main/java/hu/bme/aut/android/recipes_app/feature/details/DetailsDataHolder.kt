package hu.bme.aut.android.recipes_app.feature.details

import hu.bme.aut.android.recipes_app.models.RecipeDetails
import hu.bme.aut.android.recipes_app.models.RecipeDetailsList

interface DetailsDataHolder {
    fun getRecipeDetails(): RecipeDetails?
}