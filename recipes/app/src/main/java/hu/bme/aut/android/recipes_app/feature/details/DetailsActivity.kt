package hu.bme.aut.android.recipes_app.feature.details

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.recipes_app.R
import hu.bme.aut.android.recipes_app.databinding.ActivityDetailsBinding
import hu.bme.aut.android.recipes_app.models.RecipeDetails
import hu.bme.aut.android.recipes_app.models.RecipeDetailsList
import hu.bme.aut.android.recipes_app.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity(), DetailsDataHolder{

    private lateinit var binding: ActivityDetailsBinding
    private var recipe: String? = null
    private var recipeDetails: RecipeDetails? = null

    companion object {
        private const val TAG = "DetailsActivity"
        const val EXTRA_RECIPE_NAME = "extra.recipe_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipe = intent.getStringExtra(EXTRA_RECIPE_NAME)

        supportActionBar?.title = getString(R.string.details, recipe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        val detailsPagerAdapter = DetailsPagerAdapter(this)
        binding.mainViewPager.adapter = detailsPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.mainViewPager) { tab, position ->
            tab.text = when(position) {
                0 -> getString(R.string.details)
                else -> ""
            }
        }.attach()
        loadDetailsData()
    }

    private fun loadDetailsData() {
        NetworkManager.getRecipe(1)?.enqueue(object : Callback<RecipeDetails?> {
            override fun onResponse(
                call: Call<RecipeDetails?>,
                response: Response<RecipeDetails?>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    displayDetailData(response.body())
                } else {
                    Toast.makeText(this@DetailsActivity, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<RecipeDetails?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(this@DetailsActivity, "Network request error occured, check LOG", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun getRecipeDetails(): RecipeDetails? {
        return recipeDetails
    }

    private fun displayDetailData(receivedRecipeData: RecipeDetails?) {
        recipeDetails = receivedRecipeData
        val detailsPagerAdapter = DetailsPagerAdapter(this)
        binding.mainViewPager.adapter = detailsPagerAdapter
    }
}