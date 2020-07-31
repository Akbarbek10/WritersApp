package uz.mobiler.adiblar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.custom_tab.view.*
import kotlinx.android.synthetic.main.fragment_writers.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.adapters.data.MyFragmentAdapter

class WritersFragment : Fragment() {

    private lateinit var tabTitles: ArrayList<String>
    private lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_writers, container, false)


        tabTitles = ArrayList()
        tabTitles.add("Mumtoz adabiyoti")
        tabTitles.add("O'zbek adabiyoti")
        tabTitles.add("Jahon adabiyoti")

        val adapter = MyFragmentAdapter(childFragmentManager)
        root.pager.adapter = adapter
        root.tabs.setupWithViewPager(root.pager)

        setTabs()
        root.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                root.pager.currentItem = tab.position
                val tabItem =
                    tab.customView?.findViewById<View>(R.id.tab_item)

                val tabName = tab.customView?.findViewById<TextView>(R.id.tab_text)
                tabName?.setTextColor(resources.getColor(R.color.white))
                tabName?.text = tabTitles[tab.position]
                tabItem?.background = resources.getDrawable(R.drawable.tablayout_default)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tabItem =
                    tab.customView?.findViewById<View>(R.id.tab_item)
                tabItem?.background = resources.getDrawable(R.drawable.tablayout_selected)
                val tabName = tab.customView?.findViewById<TextView>(R.id.tab_text)

                tabName?.setTextColor(resources.getColor(R.color.grey))
                tabName?.text = tabTitles[tab.position]
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        root.iv_search.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
        return root
    }

    private fun setTabs() {
        for (i in 0..2) {
            val tabView =
                View.inflate(root.context, R.layout.custom_tab, null)
            root.tabs.getTabAt(i)?.customView = tabView
            if (i == 0) {
                tabView.background = resources.getDrawable(R.drawable.tablayout_default)
                tabView.tab_text.setTextColor(resources.getColor(R.color.white))
            }
            tabView.tab_text.text = tabTitles[i]
        }
    }
}