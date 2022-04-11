package uz.mobiler.adiblar.adapters.data

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.mobiler.adiblar.ui.list.WriterDataFragment

class ChosenViewPagerAdapter(
    private val fragments: List<Fragment>,
    fm: FragmentManager
) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]
}