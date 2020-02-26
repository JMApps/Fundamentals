package jmapps.fundamentals.ui.activities

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import jmapps.fundamentals.R
import jmapps.fundamentals.data.database.sqlite.helper.SQLiteOpenHelper
import jmapps.fundamentals.data.database.sqlite.lists.BookList
import jmapps.fundamentals.mvp.other.OtherContract
import jmapps.fundamentals.mvp.other.OtherPresenterImpl
import jmapps.fundamentals.ui.adapter.BookListAdapter
import jmapps.fundamentals.ui.fragment.AboutUsBottomSheet
import jmapps.fundamentals.ui.fragment.SettingsBottomSheet
import jmapps.fundamentals.ui.model.Books
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BookListAdapter.OnItemBookClick, OtherContract.OtherView {

    private var database: SQLiteDatabase? = null
    private lateinit var otherPresenterImpl: OtherPresenterImpl

    private lateinit var bookList: MutableList<Books>
    private lateinit var bookListAdapter: BookListAdapter

    private lateinit var nightModeState: MenuView.ItemView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        database = SQLiteOpenHelper(this).readableDatabase
        bookList = BookList(this).getBookList

        otherPresenterImpl = OtherPresenterImpl(this, this)

        val verticalLayout = LinearLayoutManager(this)
        rvMainBooks.layoutManager = verticalLayout

        bookListAdapter = BookListAdapter(this, bookList, this)
        rvMainBooks.adapter = bookListAdapter

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_night_mode -> {
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
        drawerLayout.closeDrawer(GravityCompat.START)
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

    override fun onDestroy() {
        super.onDestroy()
        database?.close()
    }
}