package hu.bme.aut.android.recipes_app.feature.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hu.bme.aut.android.recipes_app.databinding.FragmentDetailsBinding
import java.io.File.separator

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private var detailsDataHolder: DetailsDataHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsDataHolder = if (activity is DetailsDataHolder) {
            activity as DetailsDataHolder?
        } else {
            throw RuntimeException(
                "Activity must implement WeatherDataHolder interface!"
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentDetailsBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (detailsDataHolder?.getRecipeDetails() != null) {
            displayDetailData()
        }
    }

    private fun displayDetailData() {
        val details = detailsDataHolder?.getRecipeDetails()
        if (details != null) {
            binding.tvName.text = details.name
        }
        binding.tvIngredients.text = details?.ingredients?.joinToString(separator = "; ")
        binding.tvInstructions.text = details?.instructions?.joinToString(separator = "\n - ", prefix = "- ")
        binding.tvTags.text = details?.tags?.joinToString(separator = ", ")
        Glide.with(this)
            .load(details?.image)
            .transition(DrawableTransitionOptions().crossFade())
            .into(binding.ivIcon)
    }
}