package hu.bme.aut.android.recipes_app.network


import hu.bme.aut.android.recipes_app.models.RecipeDetails
import hu.bme.aut.android.recipes_app.models.RecipeDetailsList
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val retrofit: Retrofit
    private val recipeApi: RecipeApi

    private const val SERVICE_URL = "https://dummyjson.com/"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        recipeApi = retrofit.create(RecipeApi::class.java)
    }

    fun getRecipe(recipeId: Int?): Call<RecipeDetails?>? {
        return recipeApi.getRecipe(recipeId)
    }

    fun getRecipes(callback: Callback<RecipeDetailsList?>): Call<RecipeDetailsList?>? {
        val call = recipeApi.getRecipes()
        if (call != null) {
            call.enqueue(object : Callback<RecipeDetailsList?> {
                override fun onResponse(call: Call<RecipeDetailsList?>?, response: Response<RecipeDetailsList?>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            val jsonData = response?.body()
                            callback?.onResponse(call, Response.success(jsonData))
                        } else {
                            callback?.onFailure(call, Throwable("Unsuccessful response: ${response.code()}"))
                        }
                    }
                }

                override fun onFailure(call: Call<RecipeDetailsList?>?, t: Throwable) {
                    if (callback != null) {
                        callback.onFailure(call, t)
                    }
                }
            })
        }
        return call
    }
}