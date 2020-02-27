package jmapps.fundamentals.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Table_of_notes")
data class Notes(
    @PrimaryKey(autoGenerate = true) val noteId: Long,
    val noteTitle: String,
    val noteContent: String,
    val displayBy: Long
)