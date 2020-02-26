package jmapps.fundamentals.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import jmapps.fundamentals.ui.fragment.PlaceholderFragment
import jmapps.fundamentals.ui.model.BookContent

class SectionsPagerAdapter(fm: FragmentManager, private val numberOfPages: MutableList<BookContent>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getCount(): Int {
        return numberOfPages.size
    }
}