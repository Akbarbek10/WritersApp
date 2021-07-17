package uz.mobiler.adiblar.ui.home

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_home.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.adapters.ViewPagerAdapter
import uz.mobiler.adiblar.ui.library.LibraryFragment
import uz.mobiler.adiblar.ui.saved.ChooseFragment
import uz.mobiler.adiblar.ui.setting.SettingFragment
import uz.mobiler.adiblar.ui.writer.WritersFragment
import uz.mobiler.adiblar.utils.MySharedPreference
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_home, container, false)
        MySharedPreference.init(root.context)

        val listFragment = arrayListOf<Fragment>(
            WritersFragment(), LibraryFragment(),ChooseFragment()
        )
        val adapter = ViewPagerAdapter(listFragment, childFragmentManager)

        root.viewPager.adapter = adapter

        root.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                root.bottomBar.itemActiveIndex = position
            }

        })

        root.bottomBar.onItemSelected = {
            root.viewPager.currentItem = it
        }

        return root
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