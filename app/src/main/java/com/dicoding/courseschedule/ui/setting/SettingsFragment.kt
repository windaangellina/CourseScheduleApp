package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.FunctionLibrary
import com.dicoding.courseschedule.util.NightMode
import com.dicoding.courseschedule.util.QueryType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingsFragment : PreferenceFragmentCompat() {

    private val dailyReminder = DailyReminder.getInstance()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        setThemePreference()
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        setNotificationPreference()
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

    private fun setThemePreference(){
        //getString(R.string.pref_key_dark) -> get  id di xml preference
        val prefTheme = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        prefTheme?.setOnPreferenceChangeListener { preference, newValue ->
            when(newValue){
                "auto"  -> updateTheme(NightMode.AUTO.value)
                "on"    -> updateTheme(NightMode.ON.value)
                "off"   -> updateTheme(NightMode.OFF.value)
            }
            true
        }
    }

    private fun setNotificationPreference(){
        val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
            FunctionLibrary.showToast(requireContext(), "notif : $newValue")
            if (newValue == true){
                val repo = DataRepository.getInstance(requireContext())
                GlobalScope.launch {
                    if (repo != null) {
                        dailyReminder.showNotification(requireContext(), repo.getTodaySchedule())
                    }
                }
                //dailyReminder.setDailyReminder(requireContext())
            }
            else{
                dailyReminder.cancelAlarm(requireContext())
            }
            true
        }
    }
}
