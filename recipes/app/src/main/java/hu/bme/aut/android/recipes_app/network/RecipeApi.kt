package hu.bme.aut.android.recipes_app.network

import hu.bme.aut.android.recipes_app.models.RecipeDetails
import hu.bme.aut.android.recipes_app.models.RecipeDetailsList

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.math.log

interface RecipeApi {
    @GET("/recipes/{id}")
    fun getRecipe(
        @Path("id") id: Int?
    ): Call<RecipeDetails?>?

    @GET("/recipes")
    fun getRecipes(
    ): Call<RecipeDetailsList?>?
}