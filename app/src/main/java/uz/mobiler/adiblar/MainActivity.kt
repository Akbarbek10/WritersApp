package uz.mobiler.adiblar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import uz.mobiler.adiblar.adapters.ViewPagerAdapter
import uz.mobiler.adiblar.fragments.ChooseFragment
import uz.mobiler.adiblar.fragments.SettingFragment
import uz.mobiler.adiblar.fragments.WritersFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}