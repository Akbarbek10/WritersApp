package uz.mobiler.adiblar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_setting.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.utils.MySharedPreference


class SettingFragment : Fragment() {

    private lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_setting, container, false)

        MySharedPreference.init(root.context)

        root.themeSwitch.isChecked = MySharedPreference.darkMode!!

        root.themeSwitch.addSwitchObserver { switchView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                MySharedPreference.darkMode = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                MySharedPreference.darkMode = false
            }

        }
        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            root.context,
            R.array.language, R.layout.spinner_item
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        root.spinner.adapter = adapter
        return root
    }
}