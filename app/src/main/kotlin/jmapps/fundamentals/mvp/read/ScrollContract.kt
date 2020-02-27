package jmapps.fundamentals.mvp.read

import android.content.SharedPreferences

interface ScrollContract {

    interface ScrollView

    interface ScrollPresenter {

        fun scrollCount()

        fun saveLastCount(editor: SharedPreferences.Editor)

        fun loadLastCount(preferences: SharedPreferences)
    }
}