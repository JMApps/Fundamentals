package jmapps.fundamentals.ui.repository

import androidx.lifecycle.LiveData
import jmapps.fundamentals.data.database.room.NotesDao
import jmapps.fundamentals.ui.model.Notes

class NotesRepository(private val notedDao: NotesDao) {

    val getAllNotes: LiveData<MutableList<Notes>> = notedDao.getAllNotes()

    suspend fun insertNote(notes: Notes) {
        notedDao.insertNote(notes)
    }

    suspend fun updateNote(newNoteTitle: String, newNoteContent: String, noteId: Long) {
        notedDao.updateNote(newNoteTitle, newNoteContent, noteId)
    }

    suspend fun deleteNote(noteId: Long) {
        notedDao.deleteNote(noteId)
    }

    suspend fun deleteAllNotes() {
        notedDao.deleteAllNotes()
    }
}