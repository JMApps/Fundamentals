package jmapps.fundamentals.ui.activities

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import jmapps.fundamentals.R
import jmapps.fundamentals.data.database.sqlite.helper.SQLiteOpenHelper
import jmapps.fundamentals.data.database.sqlite.lists.BookList
import jmapps.fundamentals.ui.adapter.BookListAdapter
import jmapps.fundamentals.ui.model.Books
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BookListAdapter.OnItemBookClick {

    private lateinit var database: SQLiteDatabase

    private lateinit var bookList: MutableList<Books>
    private lateinit var bookListAdapter: BookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        database = SQLiteOpenHelper(this).readableDatabase
        bookList = BookList(this).getBookList

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onItemClick(bookId: Int) {
        Toast.makeText(this, "$bookId", Toast.LENGTH_SHORT).show()
    }
}