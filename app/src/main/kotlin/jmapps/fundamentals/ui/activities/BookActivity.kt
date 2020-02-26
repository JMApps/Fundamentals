package jmapps.fundamentals.ui.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import jmapps.fundamentals.R
import jmapps.fundamentals.data.database.sqlite.lists.BookContentList
import jmapps.fundamentals.ui.adapter.SectionsPagerAdapter
import jmapps.fundamentals.ui.fragment.ChaptersBottomSheet
import jmapps.fundamentals.ui.model.BookContent
import kotlinx.android.synthetic.main.activity_book.*
import kotlinx.android.synthetic.main.content_book.*

class BookActivity : AppCompatActivity(), ViewPager.OnPageChangeListener, View.OnClickListener,
    ChaptersBottomSheet.SetCurrentChapter {

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var numberOfPager: MutableList<BookContent>

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        setSupportActionBar(toolbar)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences.edit()

        numberOfPager = BookContentList(this).getBookContentList
        tvNameChapter.text = numberOfPager[0].nameChapter

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, numberOfPager)
        bookPager.adapter = sectionsPagerAdapter

        bookPager.addOnPageChangeListener(this)

        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset < 0) {
                fabChapters.hide()
            } else {
                fabChapters.show()
            }
        })

        fabChapters.setOnClickListener(this)
        loadLastPagerPosition()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        tvNameChapter.text = numberOfPager[position].nameChapter
    }

    override fun onClick(v: View?) {
        val chaptersBottomSheet = ChaptersBottomSheet()
        chaptersBottomSheet.show(supportFragmentManager, ChaptersBottomSheet.chaptersTag)
    }

    override fun setCurrentChapter(chapterId: Int) {
        bookPager.currentItem = chapterId - 1
    }

    override fun onStop() {
        super.onStop()
        saveLastPagerPosition()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveLastPagerPosition()
    }

    private fun saveLastPagerPosition() {
        editor.putInt("key_last_pager_position", bookPager.currentItem).apply()
    }

    private fun loadLastPagerPosition() {
        val lastPosition = preferences.getInt("key_last_pager_position", 0)
        bookPager.currentItem = lastPosition
    }
}