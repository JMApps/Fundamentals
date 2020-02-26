package jmapps.fundamentals.ui.fragment

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import jmapps.fundamentals.R
import jmapps.fundamentals.data.database.sqlite.lists.BookContentList
import jmapps.fundamentals.ui.model.BookContent
import kotlinx.android.synthetic.main.fragment_main.view.*

class PlaceholderFragment : Fragment() {

    private lateinit var numberOfPager: MutableList<BookContent>

    private lateinit var rootMain: View
    private var sectionNumber: Int? = null

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootMain = inflater.inflate(R.layout.fragment_main, container, false)

        sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER)
        numberOfPager = BookContentList(context).getBookContentList

        rootMain.tvBookContentArabic.text = numberOfPager[sectionNumber!! - 1].contentArabic
        rootMain.tvBookContentTranslation.text = Html.fromHtml(numberOfPager[sectionNumber!! - 1].contentTranslation)

        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = preferences.edit()

        return rootMain
    }
}