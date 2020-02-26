package jmapps.fundamentals.mvp.read

import android.content.SharedPreferences
import android.view.ViewTreeObserver
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView

class ScrollPresenterImpl(
    private val scrollView: ScrollContract.ScrollView,
    private var contentNumber: Int,
    private val mainScroll: NestedScrollView,
    private val progressScroll: ProgressBar) :
    ScrollContract.ScrollPresenter, ViewTreeObserver.OnScrollChangedListener {

    override fun scrollCount() {
        val observer: ViewTreeObserver = mainScroll.viewTreeObserver
        observer.addOnScrollChangedListener(this)
    }

    override fun saveLastCount(editor: SharedPreferences.Editor) {
        editor.putInt("$contentNumber=scrollY", mainScroll.scrollY).apply()
        editor.putInt("$contentNumber=scrollX", mainScroll.scrollX).apply()
    }

    override fun loadLastCount(preferences: SharedPreferences) {
        mainScroll.post {
            val scrollX = preferences.getInt("$contentNumber=scrollX", 0)
            val scrollY = preferences.getInt("$contentNumber=scrollY", 0)
            mainScroll.scrollTo(scrollX, scrollY)
        }
    }

    override fun onScrollChanged() {
        progressScroll.max = mainScroll.getChildAt(0).height - mainScroll.height
        progressScroll.progress = mainScroll.scrollY
    }
}