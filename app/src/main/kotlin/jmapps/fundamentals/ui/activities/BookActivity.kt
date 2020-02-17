package jmapps.fundamentals.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jmapps.fundamentals.R
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        setSupportActionBar(toolbar)
    }
}
