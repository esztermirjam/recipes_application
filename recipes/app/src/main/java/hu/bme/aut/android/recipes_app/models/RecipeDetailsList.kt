package hu.bme.aut.android.recipes_app.models

data class RecipeDetailsList (
    //val name: String,
    var recipes: List<RecipeDetails?> = emptyList(),
    val total: Int,
    val skip: Int,
    val limit: Int
)