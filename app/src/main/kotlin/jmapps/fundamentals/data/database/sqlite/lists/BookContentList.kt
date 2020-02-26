package jmapps.fundamentals.data.database.sqlite.lists

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import jmapps.fundamentals.data.database.sqlite.helper.SQLiteOpenHelper
import jmapps.fundamentals.ui.model.BookContent

class BookContentList(private val context: Context?) {

    private lateinit var database: SQLiteDatabase

    val getBookContentList: MutableList<BookContent>
        @SuppressLint("Recycle")
        get() {

            database = SQLiteOpenHelper(context!!).readableDatabase

            val cursor: Cursor = database.query(
                "Table_of_book_one",
                null,
                null,
                null,
                null,
                null,
                null
            )

            val bookContentList = ArrayList<BookContent>()

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val bookContent = BookContent(
                        cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("NameChapter")),
                        cursor.getString(cursor.getColumnIndex("ContentArabic")),
                        cursor.getString(cursor.getColumnIndex("ContentTranslation"))
                    )
                    bookContentList.add(bookContent)
                    cursor.moveToNext()
                    if (cursor.isClosed) {
                        cursor.close()
                    }
                }
            }
            return bookContentList
        }
}