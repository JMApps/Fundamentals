package jmapps.fundamentals.ui.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.fundamentals.R
import jmapps.fundamentals.ui.adapter.BookListAdapter

class BookHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val tvBookId: TextView = itemView.findViewById(R.id.tvBookId)
    val tvBookName: TextView = itemView.findViewById(R.id.tvBookName)

    fun findItemClick(onItemBookClick: BookListAdapter.OnItemBookClick, bookId: Int) {
        itemView.setOnClickListener {
            onItemBookClick.onItemClick(bookId)
        }
    }
}