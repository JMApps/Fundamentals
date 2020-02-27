package jmapps.fundamentals.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.fundamentals.R
import jmapps.fundamentals.ui.holder.NotesViewHolder
import jmapps.fundamentals.ui.model.Notes

class NotesAdapter internal constructor(
    context: Context,
    private var notes: MutableList<Notes>,
    private val noteItemClick: NoteItemClick) :
    RecyclerView.Adapter<NotesViewHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var mainNotes: MutableList<Notes>? = null

    init {
        this.mainNotes = notes
    }

    interface NoteItemClick {
        fun itemClick(tvNoteContent: TextView, noteId: Long, noteTitle: String, noteContent: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val noteView = inflater.inflate(R.layout.item_note, parent, false)
        return NotesViewHolder(noteView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val current = notes[position]

        holder.tvNoteTitle.text = current.noteTitle
        holder.tvNoteContent.text = current.noteContent

        holder.tvNumberOfNote.text = (position + 1).toString()

        if (current.noteTitle.isEmpty()) {
            holder.tvNoteTitle.visibility = View.GONE
        } else {
            holder.tvNoteTitle.visibility = View.VISIBLE
        }

        if (current.noteContent.isEmpty()) {
            holder.tvNoteContent.visibility = View.GONE
        } else {
            holder.tvNoteContent.visibility = View.VISIBLE
        }

        holder.findWordItemClick(noteItemClick, holder.tvNoteContent, current.noteId, current.noteTitle, current.noteContent)
    }

    override fun getItemCount() = notes.size

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                notes = if (charString.isEmpty()) {
                    mainNotes as MutableList<Notes>
                } else {
                    val filteredList = ArrayList<Notes>()
                    for (row in mainNotes!!) {
                        if (row.noteTitle.toLowerCase().contains(charString.toLowerCase()) ||
                            row.noteContent.toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = notes
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                notes = filterResults.values as ArrayList<Notes>
                notifyDataSetChanged()
            }
        }
    }
}