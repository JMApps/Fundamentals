package jmapps.fundamentals.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import jmapps.fundamentals.R
import jmapps.fundamentals.ui.holder.BookHolder
import jmapps.fundamentals.ui.model.Books

class BookListAdapter(
    context: Context,
    private var bookList: MutableList<Books>,
    private val onItemChapterClick: OnItemBookClick) : RecyclerView.Adapter<BookHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var mainBookList: List<Books>? = null

    init {
        this.mainBookList = bookList
    }

    interface OnItemBookClick {
        fun onItemClick(bookId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val bookView = inflater.inflate(R.layout.item_book, parent, false)
        return BookHolder(bookView)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val current = bookList[position]

        holder.tvBookId.text = current.bookId.toString()
        holder.tvBookName.text = current.strBookName

        holder.findItemClick(onItemChapterClick, current.bookId)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                bookList = if (charString.isEmpty()) {
                    mainBookList as MutableList<Books>
                } else {
                    val filteredList = ArrayList<Books>()
                    for (row in mainBookList!!) {
                        if (row.strBookName.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = bookList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                bookList = filterResults.values as ArrayList<Books>
                notifyDataSetChanged()
            }
        }
    }
}