package uz.mobiler.adiblar.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_writer_data.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.adapters.recycler.RecyclerViewAdapter

private const val ARG_PARAM1 = "param1"

class WriterDataFragment : Fragment() {
    private var param1: Int? = null
    private lateinit var root: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_writer_data, container, false)

        root.rv_writers.adapter = RecyclerViewAdapter(object : RecyclerViewAdapter.OnItemClick {
            override fun onItemClickListener() {
                findNavController().navigate(R.id.writerInfoFragment)
            }

        }, root.context)
        return root

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            WriterDataFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}