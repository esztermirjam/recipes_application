package hu.bme.aut.android.recipes_app.feature.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        private const val NUM_PAGES: Int = 1
    }

    override fun createFragment(position: Int): Fragment {
        return DetailsFragment()
    }

    override fun getItemCount(): Int = NUM_PAGES
}