package jmapps.fundamentals.mvp.other

interface OtherContract {

    interface OtherView {

        fun showSettings()

        fun showAboutUs()
    }

    interface OtherPresenter {

        fun getSettings()

        fun getAboutUs()

        fun shareLink()

        fun rateApp()
    }
}