package uz.mobiler.adiblar.ui.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_chosen_writers.view.*
import uz.mobiler.adiblar.adapters.recycler.RecyclerViewAdapter
import uz.mobiler.adiblar.database.MyDBHelper
import uz.mobiler.adiblar.models.Writer
import kotlin.collections.ArrayList
import uz.mobiler.adiblar.R as R1

class ChosenWritersFragment : Fragment() {
    lateinit var root: View
    lateinit var myDBHelper: MyDBHelper
    lateinit var writerList : ArrayList<Writer>
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R1.layout.fragment_chosen_writers, container, false)

        return root
    }

    override fun onResume() {
        super.onResume()
        myDBHelper = MyDBHelper(root.context)
        writerList = myDBHelper.getAllWriters() as ArrayList<Writer>

        if (writerList.isEmpty())
            root.tv_noData.visibility = View.VISIBLE
        else
            root.tv_noData.visibility = View.GONE


        recyclerViewAdapter =
            RecyclerViewAdapter(writerList, object : RecyclerViewAdapter.OnItemClick {
                override fun onItemClickListener(writer: Writer) {
                    val bundle = Bundle()
                    bundle.putSerializable("writer", writer)
                    findNavController().navigate(R1.id.writerInfoFragment, bundle)
                }
            }, root.context, 1)

//        root.iv_search.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putInt("search", 1)
//            findNavController().navigate(R.id.searchFragment, bundle)
//        }
        root.rv_writers.adapter = recyclerViewAdapter
    }

}