package uz.mobiler.adiblar.adapters.data

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.mobiler.adiblar.fragments.list.WriterDataFragment

class MyFragmentAdapter(fm: FragmentManager?) :
    FragmentStatePagerAdapter(
        fm!!,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    override fun getItem(position: Int): Fragment {
        return WriterDataFragment.newInstance(position)
    }

    override fun getCount(): Int = 3


}