package jmapps.fundamentals.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.fundamentals.R
import jmapps.fundamentals.databinding.BottomSheetSettingsBinding

class SettingsBottomSheet : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetStyleFull

    private lateinit var settingBinding: BottomSheetSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        settingBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_settings, container, false)

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return settingBinding.root
    }

    companion object {
        const val settingsUsTag = "SettingsBottomSheet"
    }
}