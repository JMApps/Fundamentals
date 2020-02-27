package jmapps.fundamentals.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import jmapps.fundamentals.data.database.room.RoomDatabaseHelper
import jmapps.fundamentals.ui.model.Notes
import jmapps.fundamentals.ui.repository.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val notesRepository: NotesRepository
    val allNotes: LiveData<MutableList<Notes>>

    init {
        val notesDao = RoomDatabaseHelper.getDatabase(application, viewModelScope).notesDao()
        notesRepository = NotesRepository(notesDao)
        allNotes = notesRepository.getAllNotes
    }

    fun insertNote(notes: Notes) = viewModelScope.launch {
        notesRepository.insertNote(notes)
    }

    fun updateNote(newNoteTitle: String, newNoteContent: String, noteId: Long) = viewModelScope.launch {
            notesRepository.updateNote(newNoteTitle, newNoteContent, noteId)
        }

    fun deleteNote(noteId: Long) = viewModelScope.launch {
        notesRepository.deleteNote(noteId)
    }

    fun deleteAllNotes() = viewModelScope.launch {
        notesRepository.deleteAllNotes()
    }
}