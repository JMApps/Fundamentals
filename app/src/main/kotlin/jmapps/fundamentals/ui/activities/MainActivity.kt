package jmapps.fundamentals.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import jmapps.fundamentals.R
import jmapps.fundamentals.data.database.sqlite.helper.SQLiteOpenHelper
import jmapps.fundamentals.data.database.sqlite.lists.BookList
import jmapps.fundamentals.databinding.ActivityMainBinding
import jmapps.fundamentals.mvp.other.OtherContract
import jmapps.fundamentals.mvp.other.OtherPresenterImpl
import jmapps.fundamentals.ui.adapter.BookListAdapter
import jmapps.fundamentals.ui.fragment.AboutUsBottomSheet
import jmapps.fundamentals.ui.fragment.SettingsBottomSheet
import jmapps.fundamentals.ui.model.Books


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BookListAdapter.OnItemBookClick, OtherContract.OtherView {

    private lateinit var binding: ActivityMainBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var database: SQLiteDatabase? = null
    private lateinit var otherPresenterImpl: OtherPresenterImpl

    private lateinit var bookList: MutableList<Books>
    private lateinit var bookListAdapter: BookListAdapter

    private var valNightMode: Boolean = false
    private lateinit var nightModeItem: MenuItem

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.appBarMain.toolbar)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences.edit()

        otherPresenterImpl = OtherPresenterImpl(this, this)

        valNightMode = preferences.getBoolean("key_night_mode", false)
        isNightMode(valNightMode)

        database = SQLiteOpenHelper(this).readableDatabase
        bookList = BookList(this).getBookList

        val verticalLayout = LinearLayoutManager(this)
        binding.appBarMain.mainContent.rvMainBooks.layoutManager = verticalLayout

        bookListAdapter = BookListAdapter(this, bookList, this)
        binding.appBarMain.mainContent.rvMainBooks.adapter = bookListAdapter

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        nightModeItem = menu.findItem(R.id.action_night_mode)
        nightModeItem.isChecked = valNightMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_night_mode -> {
                otherPresenterImpl.setNightMode(!nightModeItem.isChecked)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.nav_settings -> otherPresenterImpl.getSettings()

            R.id.nav_about_us -> otherPresenterImpl.getAboutUs()

            R.id.nav_rate -> otherPresenterImpl.rateApp()

            R.id.nav_share -> otherPresenterImpl.shareLink()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onItemClick(bookId: Int) {
        val toBookActivity = Intent(this, BookActivity::class.java)
        startActivity(toBookActivity)
    }

    override fun showSettings() {
        val settings = SettingsBottomSheet()
        settings.show(supportFragmentManager, SettingsBottomSheet.settingsUsTag)
    }

    override fun showAboutUs() {
        val aboutUs = AboutUsBottomSheet()
        aboutUs.show(supportFragmentManager, AboutUsBottomSheet.aboutUsTag)
    }

    override fun getNightMode(state: Boolean) {
        isNightMode(state)
        editor.putBoolean("key_night_mode", state).apply()
    }

    override fun isNightMode(state: Boolean) {
        if (state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        database?.close()
    }
}