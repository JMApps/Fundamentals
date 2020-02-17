package jmapps.fundamentals.mvp.other

import android.content.Context
import android.content.Intent
import android.net.Uri

class OtherPresenterImpl(
    private val context: Context,
    private val otherView: OtherContract.OtherView?) :
    OtherContract.OtherPresenter {

    private val linkDescription = "Матны требующего знания\n"
    private val linkApp = "https://play.google.com/store/apps/details?id=jmapps.fundamentals"

    override fun getSettings() {
        otherView?.showSettings()
    }

    override fun getAboutUs() {
        otherView?.showAboutUs()
    }

    override fun shareLink() {
        val shareLink = Intent(Intent.ACTION_SEND)
        shareLink.type = "text/plain"
        shareLink.putExtra(Intent.EXTRA_TEXT, "$linkDescription$linkApp")
        context.startActivity(shareLink)
    }

    override fun rateApp() {
        val rate = Intent(Intent.ACTION_VIEW)
        rate.data = Uri.parse(linkApp)
        context.startActivity(rate)
    }
}