package jmapps.fundamentals.data.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jmapps.fundamentals.ui.model.Notes

@Dao
interface NotesDao {
    @Query("SELECT * FROM Table_of_notes ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<MutableList<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(notes: Notes?)

    @Query("UPDATE Table_of_notes SET noteTitle = :noteTitle, noteContent = :noteContent WHERE noteId = :noteId")
    suspend fun updateNote(noteTitle: String, noteContent: String, noteId: Long)

    @Query("DELETE FROM Table_of_notes WHERE noteId = :noteId")
    suspend fun deleteNote(noteId: Long)

    @Query("DELETE FROM TABLE_OF_NOTES")
    suspend fun deleteAllNotes()
}