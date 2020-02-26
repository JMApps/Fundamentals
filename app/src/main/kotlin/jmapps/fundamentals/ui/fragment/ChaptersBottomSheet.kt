package jmapps.fundamentals.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.fundamentals.R
import jmapps.fundamentals.data.database.sqlite.lists.BookContentList
import jmapps.fundamentals.ui.adapter.ChapterListAdapter
import jmapps.fundamentals.ui.model.BookContent
import kotlinx.android.synthetic.main.bottom_sheet_chapters.view.*

class ChaptersBottomSheet : BottomSheetDialogFragment(), ChapterListAdapter.OnItemChapterClick {

    override fun getTheme(): Int = R.style.BottomSheetStyleFull

    private lateinit var rootAboutUs: View

    private lateinit var bookContentList: MutableList<BookContent>
    private lateinit var chapterListAdapter: ChapterListAdapter

    private lateinit var setCurrentChapter: SetCurrentChapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootAboutUs = inflater.inflate(R.layout.bottom_sheet_chapters, container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        bookContentList = BookContentList(context).getBookContentList

        val verticalLayout = LinearLayoutManager(context)
        rootAboutUs.rvChapterList.layoutManager = verticalLayout

        chapterListAdapter = ChapterListAdapter(context!!, bookContentList, this)
        rootAboutUs.rvChapterList.adapter = chapterListAdapter

        return rootAboutUs
    }

    companion object {
        const val chaptersTag = "ChaptersBottomSheet"
    }

    override fun onItemClick(chapterId: Int) {
        setCurrentChapter.setCurrentChapter(chapterId)
        dialog?.dismiss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SetCurrentChapter) {
            setCurrentChapter = context
        } else {
            throw RuntimeException("$context must implement SetCurrentChapter")
        }
    }

    interface SetCurrentChapter {
        fun setCurrentChapter(chapterId: Int)
    }
}