package jmapps.fundamentals.mvp.other

interface OtherContract {

    interface OtherView {
        fun showSettings()
        fun showAboutUs()
        fun getNightMode(state: Boolean)
        fun isNightMode(state: Boolean)
    }

    interface OtherPresenter {
        fun getSettings()
        fun getAboutUs()
        fun setNightMode(state: Boolean)
        fun shareLink()
        fun rateApp()
    }
}