package hu.bme.aut.android.recipes_app.feature.recipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.recipes_app.databinding.ActivityRecipesBinding
import hu.bme.aut.android.recipes_app.feature.details.DetailsActivity
import hu.bme.aut.android.recipes_app.models.RecipeDetails
import hu.bme.aut.android.recipes_app.models.RecipeDetailsList
import hu.bme.aut.android.recipes_app.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipesActivity : AppCompatActivity(), RecipesAdapter.OnRecipeSelectedListener {

    private lateinit var binding: ActivityRecipesBinding
    private lateinit var adapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        loadRecipesData()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecipesAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadRecipesData() {
        NetworkManager.getRecipes(object : Callback<RecipeDetailsList?> {
            override fun onResponse(call: Call<RecipeDetailsList?>, response: Response<RecipeDetailsList?>) {
                if (response.isSuccessful) {
                    val recipeDetailsList = response.body()
                    if (recipeDetailsList != null) {
                        Log.d("activity good","mukodik-e adapter")
                        adapter.setRecipes(recipeDetailsList)
                    }
                } else {
                    // Handle unsuccessful response here
                }
            }

            override fun onFailure(call: Call<RecipeDetailsList?>, t: Throwable) {
                // Handle failure here
            }
        })
    }

    override fun onRecipeSelected(recipe: RecipeDetails) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("STRING_KEY", recipe.name)
        intent.putExtra("INT_KEY", recipe.id)
        startActivity(intent)
    }
}
