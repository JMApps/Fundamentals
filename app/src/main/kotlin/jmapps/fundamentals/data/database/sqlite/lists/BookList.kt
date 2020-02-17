package jmapps.fundamentals.data.database.sqlite.lists

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import jmapps.fundamentals.data.database.sqlite.helper.SQLiteOpenHelper
import jmapps.fundamentals.ui.model.Books

class BookList(private val context: Context) {

    private lateinit var database: SQLiteDatabase

    val getBookList: MutableList<Books>
        @SuppressLint("Recycle")
        get() {

            database = SQLiteOpenHelper(context).readableDatabase

            val cursor: Cursor = database.query(
                "Table_of_books",
                null,
                null,
                null,
                null,
                null,
                null
            )

            val bookList = ArrayList<Books>()

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val books = Books(
                        cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("BookName"))
                    )
                    bookList.add(books)
                    cursor.moveToNext()
                    if (cursor.isClosed) {
                        cursor.close()
                    }
                }
            }
            return bookList
        }
}