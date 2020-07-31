package uz.mobiler.adiblar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_home.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.adapters.ViewPagerAdapter


class HomeFragment : Fragment() {

    private lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_home, container, false)
        val listFragment = arrayListOf<Fragment>(
            WritersFragment(), ChooseFragment(), SettingFragment()
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
}