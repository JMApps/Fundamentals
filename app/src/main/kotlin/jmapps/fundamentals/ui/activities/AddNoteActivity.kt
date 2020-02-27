package jmapps.fundamentals.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import jmapps.fundamentals.R
import jmapps.fundamentals.databinding.ActivityAddNoteBinding
import jmapps.fundamentals.ui.model.Notes
import jmapps.fundamentals.ui.viewmodel.NotesViewModel

class AddNoteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        binding.btnAddNote.setOnClickListener(this)
    }

    override fun onBackPressed() {
        if (checkEditTextForNulls()) {
            super.onBackPressed()
        } else {
            alertAddClose()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (checkEditTextForNulls()) {
                    finish()
                } else {
                    alertAddClose()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAddNote -> {
                addWordState()
            }
        }
    }

    private fun addWordState() {
        if (!checkEditTextForNulls()) {
            addNote()
            finish()
        } else {
            finish()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun addNote() {
        val noteTitle = binding.etNoteTitle.text.toString()
        val noteContent = binding.etNoteContent.text.toString()
        val notes = Notes(0, noteTitle, noteContent, 0)
        notesViewModel.insertNote(notes)
    }

    private fun checkEditTextForNulls(): Boolean {
        return TextUtils.isEmpty(binding.etNoteTitle.text) &&
                TextUtils.isEmpty(binding.etNoteContent.text)

    }

    private fun alertAddClose() {
        val addWordDialog = AlertDialog.Builder(this)
        addWordDialog.setIcon(R.drawable.ic_warning)
        addWordDialog.setTitle(getString(R.string.action_attention))
        addWordDialog.setMessage(getString(R.string.dialog_question_add))
        addWordDialog.setNeutralButton(getString(R.string.dialog_cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        addWordDialog.setNegativeButton(getString(R.string.dialog_not)) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        addWordDialog.setPositiveButton(getString(R.string.dialog_add)) { dialog, _ ->
            if (!checkEditTextForNulls()) {
                addNote()
                dialog.dismiss()
                finish()
            }
        }
        addWordDialog.show()
    }
}