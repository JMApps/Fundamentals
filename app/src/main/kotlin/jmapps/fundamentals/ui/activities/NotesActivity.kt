package jmapps.fundamentals.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jmapps.fundamentals.R
import jmapps.fundamentals.databinding.ActivityNotesBinding
import jmapps.fundamentals.ui.adapter.NotesAdapter
import jmapps.fundamentals.ui.viewmodel.NotesViewModel

class NotesActivity : AppCompatActivity(), View.OnClickListener, NotesAdapter.NoteItemClick {

    private lateinit var binding: ActivityNotesBinding

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes)
        setSupportActionBar(binding.toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        notesViewModel.allNotes.observe(this, Observer { notes ->
            notes?.let {
                val verticalLayout = LinearLayoutManager(this)
                binding.contentNotes.rvNotes.layoutManager = verticalLayout

                notesAdapter = NotesAdapter(this, notes, this)
                binding.contentNotes.rvNotes.adapter = notesAdapter

                if (notes.size <= 0) {
                    binding.contentNotes.rvNotes.visibility = View.GONE
                    binding.contentNotes.tvIsRecyclerEmpty.visibility = View.VISIBLE
                } else {
                    binding.contentNotes.rvNotes.visibility = View.VISIBLE
                    binding.contentNotes.tvIsRecyclerEmpty.visibility = View.GONE
                }
            }
        })

        binding.contentNotes.rvNotes.addOnScrollListener(onScrollListener)
        binding.fabAddNote.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        val toAddNoteActivity = Intent(this, AddNoteActivity::class.java)
        startActivity(toAddNoteActivity)
    }

    override fun itemClick(tvNoteContent: TextView, noteId: Long, noteTitle: String, noteContent: String) {
        val popup = PopupMenu(this, tvNoteContent)
        popup.inflate(R.menu.note_options)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.popup_share -> {
                    val shareWords = Intent(Intent.ACTION_SEND)
                    shareWords.type = "text/plain"
                    shareWords.putExtra(Intent.EXTRA_TEXT, "$noteTitle\n$noteContent").toString()
                    startActivity(shareWords)
                }
//                R.id.popup_change -> {
//                    val toChangeWord = Intent(this, ChangeWordActivity::class.java)
//                    toChangeWord.putExtra(ChangeWordActivity.extraWordIdReply, wordId)
//                    toChangeWord.putExtra(ChangeWordActivity.extraNewWordReply, currentWord)
//                    toChangeWord.putExtra(ChangeWordActivity.extraNewWordTranslationReply, currentWordTranslate)
//                    toChangeWord.putExtra(ChangeWordActivity.extraCategoryPositionReply, categoryPosition)
//                    startActivity(toChangeWord)
//                }
                R.id.popup_delete -> {
                    notesViewModel.deleteNote(noteId)
                    Toast.makeText(this, getString(R.string.dialog_deleted), Toast.LENGTH_SHORT).show()
                }

                R.id.popup_delete_all -> {
                    notesViewModel.deleteAllNotes()
                    Toast.makeText(this, getString(R.string.dialog_deleted), Toast.LENGTH_SHORT).show()
                }
            }
            false
        }
        popup.show()
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dx < dy) {
                binding.fabAddNote.hide()
            } else {
                binding.fabAddNote.show()
            }
        }
    }
}