package jmapps.fundamentals.data.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import jmapps.fundamentals.ui.model.Notes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class RoomDatabaseHelper : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabaseHelper? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RoomDatabaseHelper {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabaseHelper::class.java,
                    "NotesDatabase"
                )
                    .addCallback(NotesDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class NotesDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                INSTANCE.let { database ->
                    scope.launch(Dispatchers.IO) {
                        database?.notesDao()?.let { populateNotes(it) }
                    }
                }
            }
        }

        fun populateNotes(notesDao: NotesDao) {
            notesDao.getAllNotes()
        }
    }
}