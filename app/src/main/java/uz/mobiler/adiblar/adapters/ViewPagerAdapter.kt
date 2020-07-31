package uz.mobiler.adiblar.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(
    list: ArrayList<Fragment>,
    fm: FragmentManager
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val listFragment = list

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getCount(): Int = listFragment.size


}