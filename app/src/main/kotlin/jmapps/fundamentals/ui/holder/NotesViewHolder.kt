package jmapps.fundamentals.ui.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.fundamentals.R
import jmapps.fundamentals.ui.adapter.NotesAdapter

class NotesViewHolder(noteView: View) : RecyclerView.ViewHolder(noteView) {

    val tvNumberOfNote: TextView = itemView.findViewById(R.id.tvNumberOfNote)
    val tvNoteTitle: TextView = itemView.findViewById(R.id.tvNoteTitle)
    val tvNoteContent: TextView = itemView.findViewById(R.id.tvNoteContent)

    fun findWordItemClick(noteItemClick: NotesAdapter.NoteItemClick,
                          tvNoteContent: TextView, noteId: Long, noteTitle: String, noteContent: String) {
        itemView.setOnClickListener {
            noteItemClick.itemClick(tvNoteContent, noteId, noteTitle, noteContent)
        }
    }
}