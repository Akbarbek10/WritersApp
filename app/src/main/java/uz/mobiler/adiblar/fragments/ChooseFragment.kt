package uz.mobiler.adiblar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_choose.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.adapters.recycler.RecyclerViewAdapter
import uz.mobiler.adiblar.database.MyDBHelper
import uz.mobiler.adiblar.models.Writer

class ChooseFragment : Fragment() {

    lateinit var root: View
    lateinit var myDBHelper: MyDBHelper
    lateinit var writerList: ArrayList<Writer>
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_choose, container, false)


        return root
    }

    override fun onResume() {
        super.onResume()
        myDBHelper = MyDBHelper(root.context)
        writerList = myDBHelper.getAllWriters() as ArrayList<Writer>

        recyclerViewAdapter =
            RecyclerViewAdapter(writerList, object : RecyclerViewAdapter.OnItemClick {
                override fun onItemClickListener(writer: Writer) {
                    val bundle = Bundle()
                    bundle.putSerializable("writer", writer)
                    findNavController().navigate(R.id.writerInfoFragment, bundle)
                }
            }, root.context, 1)

        root.iv_search.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("search", 1)
            findNavController().navigate(R.id.searchFragment,bundle)
        }
        root.rv_writers.adapter = recyclerViewAdapter
    }
}