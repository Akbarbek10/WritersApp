package uz.mobiler.adiblar.utils

import android.content.Context
import android.content.SharedPreferences

object MySharedPreference {
    private const val NAME = "adiblar"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var darkMode: Boolean?
        get() = preferences.getBoolean("mode", false)
        set(value) = preferences.edit {
            if (value != null) {
                it.putBoolean("mode", value)
            }
        }
    var language: String?
        get() = preferences.getString("language", "uz")
        set(value) = preferences.edit {
            if (value != null) {
                it.putString("language", value)
            }
        }
    var spinnerPosition: Int?
        get() = preferences.getInt("position", 0)
        set(value) = preferences.edit {
            if (value != null) {
                it.putInt("position", value)
            }
        }
}
