package uz.mobiler.adiblar

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import uz.mobiler.adiblar.adapters.ViewPagerAdapter
import uz.mobiler.adiblar.fragments.ChooseFragment
import uz.mobiler.adiblar.fragments.SettingFragment
import uz.mobiler.adiblar.fragments.WritersFragment
import uz.mobiler.adiblar.utils.MySharedPreference
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MySharedPreference.init(this)
        if (MySharedPreference.darkMode!!) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
        setLocale()
    }

    override fun onResume() {
        super.onResume()
        setLocale()
    }

    private fun setLocale() {
        val locale = Locale(MySharedPreference.language!!)
        Locale.setDefault(locale)
        val config: Configuration = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}