package jmapps.fundamentals.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import jmapps.fundamentals.R
import jmapps.fundamentals.ui.holder.ChapterHolder
import jmapps.fundamentals.ui.model.BookContent

class ChapterListAdapter(
    context: Context,
    private var bookContentList: MutableList<BookContent>,
    private val onItemChapterClick: OnItemChapterClick) : RecyclerView.Adapter<ChapterHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var mainBookContentList: List<BookContent>? = null

    init {
        this.mainBookContentList = bookContentList
    }

    interface OnItemChapterClick {
        fun onItemClick(chapterId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterHolder {
        val contentView = inflater.inflate(R.layout.item_chapter, parent, false)
        return ChapterHolder(contentView)
    }

    override fun getItemCount(): Int {
        return bookContentList.size
    }

    override fun onBindViewHolder(holder: ChapterHolder, position: Int) {
        val current = bookContentList[position]

        holder.tvChapterId.text = current.contentId.toString()
        holder.tvChapterName.text = current.nameChapter

        holder.findItemClick(onItemChapterClick, current.contentId)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                bookContentList = if (charString.isEmpty()) {
                    mainBookContentList as MutableList<BookContent>
                } else {
                    val filteredList = ArrayList<BookContent>()
                    for (row in mainBookContentList!!) {
                        if (row.nameChapter.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = bookContentList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                bookContentList = filterResults.values as ArrayList<BookContent>
                notifyDataSetChanged()
            }
        }
    }
}