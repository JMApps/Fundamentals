package jmapps.fundamentals.data.database.sqlite.helper

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

private var DBVersion = 1

class SQLiteOpenHelper(context: Context) :
    SQLiteAssetHelper(context, "Books", null, DBVersion) {

    init {
        setForcedUpgrade(DBVersion)
    }
}