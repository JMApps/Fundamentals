package jmapps.fundamentals.ui.fragment

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.fundamentals.R
import jmapps.fundamentals.databinding.BottomSheetSettingsBinding

class SettingsBottomSheet : BottomSheetDialogFragment(), SeekBar.OnSeekBarChangeListener,
    CompoundButton.OnCheckedChangeListener {

    override fun getTheme(): Int = R.style.BottomSheetStyleFull

    private lateinit var settingBinding: BottomSheetSettingsBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var textSizeValues = (16..30).toList().filter { it % 2 == 0 }

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        settingBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_settings, container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = preferences.edit()

        settingBinding.sbArabicTextSize.setOnSeekBarChangeListener(this)
        settingBinding.sbTranslationTextSize.setOnSeekBarChangeListener(this)

        settingBinding.showArabicText.setOnCheckedChangeListener(this)
        settingBinding.showTranslationText.setOnCheckedChangeListener(this)

        val lastProgressArabic = preferences.getInt("key_arabic_text_size_progress",1)
        val lastProgressTranslation = preferences.getInt("key_translation_text_size_progress",1)

        val lastArabicTextShowState = preferences.getBoolean("key_arabic_text_show_state", true)
        val lastTranslationTextShowState = preferences.getBoolean("key_translation_text_show_state", true)

        settingBinding.apply {
            sbArabicTextSize.progress = lastProgressArabic
            sbTranslationTextSize.progress = lastProgressTranslation

            tvArabicSizeCounter.text = textSizeValues[lastProgressArabic].toString()
            tvTranslationSizeCounter.text = textSizeValues[lastProgressTranslation].toString()

            showArabicText.isChecked = lastArabicTextShowState
            showTranslationText.isChecked = lastTranslationTextShowState
        }

        return settingBinding.root
    }

    companion object {
        const val settingsUsTag = "SettingsBottomSheet"
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar?.id) {

            R.id.sbArabicTextSize -> {
                settingBinding.tvArabicSizeCounter.text = textSizeValues[progress].toString()
                editor.putInt("key_arabic_text_size_progress", progress).apply()
            }

            R.id.sbTranslationTextSize -> {
                settingBinding.tvTranslationSizeCounter.text = textSizeValues[progress].toString()
                editor.putInt("key_translation_text_size_progress", progress).apply()
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {

            R.id.showArabicText -> {
                editor.putBoolean("key_arabic_text_show_state", settingBinding.showArabicText.isChecked).apply()
                if (!isChecked) {
                    if (!settingBinding.showTranslationText.isChecked) {
                        settingBinding.showTranslationText.isChecked = true
                    }
                }
            }

            R.id.showTranslationText -> {
                editor.putBoolean("key_translation_text_show_state", settingBinding.showTranslationText.isChecked).apply()
                if (!isChecked) {
                    if (!settingBinding.showArabicText.isChecked) {
                        settingBinding.showArabicText.isChecked = true
                    }
                }
            }
        }
    }
}