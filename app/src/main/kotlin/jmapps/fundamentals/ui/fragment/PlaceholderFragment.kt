package jmapps.fundamentals.ui.fragment

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import jmapps.fundamentals.R
import jmapps.fundamentals.data.database.sqlite.lists.BookContentList
import jmapps.fundamentals.databinding.FragmentMainBinding
import jmapps.fundamentals.mvp.read.ScrollContract
import jmapps.fundamentals.mvp.read.ScrollPresenterImpl
import jmapps.fundamentals.ui.model.BookContent

class PlaceholderFragment : Fragment(), ScrollContract.ScrollView {

    private var textSizeValues = (16..30).toList().filter { it % 2 == 0 }

    private lateinit var binding: FragmentMainBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var sectionNumber: Int? = null

    private lateinit var numberOfPager: MutableList<BookContent>
    private lateinit var scrollPresenterImpl: ScrollPresenterImpl

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = preferences.edit()

        sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER)
        numberOfPager = BookContentList(context).getBookContentList

        binding.tvBookContentArabic.text = numberOfPager[sectionNumber!! - 1].contentArabic
        binding.tvBookContentTranslation.text = Html.fromHtml(numberOfPager[sectionNumber!! - 1].contentTranslation)

        scrollPresenterImpl = ScrollPresenterImpl(this, sectionNumber!!, binding.nsMainContainer, binding.lastReadingPosition)
        scrollPresenterImpl.scrollCount()
        scrollPresenterImpl.loadLastCount(preferences)

        isShowTexts()
        textSizes()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scrollPresenterImpl.saveLastCount(editor)
    }

    private fun isShowTexts() {
        val lastArabicTextShowState = preferences.getBoolean("key_arabic_text_show_state", true)
        val lastTranslationTextShowState = preferences.getBoolean("key_translation_text_show_state", true)

        if (numberOfPager[sectionNumber!! - 1].contentArabic != null) {
            if (lastArabicTextShowState) {
                binding.tvBookContentArabic.visibility = View.VISIBLE
            } else {
                binding.tvBookContentArabic.visibility = View.GONE
            }
        } else {
            binding.tvBookContentArabic.visibility = View.GONE
        }

        if (lastTranslationTextShowState) {
            binding.tvBookContentTranslation.visibility = View.VISIBLE
        } else {
            binding.tvBookContentTranslation.visibility = View.GONE
        }
    }

    private fun textSizes() {
        val lastProgressArabic = preferences.getInt("key_arabic_text_size_progress",1)
        val lastProgressTranslation = preferences.getInt("key_translation_text_size_progress",1)

        binding.tvBookContentArabic.textSize = textSizeValues[lastProgressArabic].toFloat()
        binding.tvBookContentTranslation.textSize = textSizeValues[lastProgressTranslation].toFloat()
    }
}