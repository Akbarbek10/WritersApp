package uz.mobiler.adiblar.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_writer_data.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.adapters.recycler.RecyclerViewAdapter
import uz.mobiler.adiblar.models.Writer

private const val ARG_PARAM1 = "param1"

class WriterDataFragment : Fragment() {
    private var param1: Int? = null
    private lateinit var root: View

    private lateinit var writerList: ArrayList<Writer>
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var category: String
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
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
        firebaseDatabase = FirebaseDatabase.getInstance()
        if (param1 == 0) {
            category = "classic"
        } else if (param1 == 1) {
            category = "uzbek"
        } else {
            category = "world"
        }

        databaseReference = firebaseDatabase.getReference(category)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                writerList = ArrayList()
                val children = snapshot.children
                for (child in children) {
                    val writer = child.getValue(Writer::class.java)
                    if (writer != null) {
                        writerList.add(writer)
                    }
                }
                recyclerViewAdapter =
                    RecyclerViewAdapter(writerList, object : RecyclerViewAdapter.OnItemClick {
                        override fun onItemClickListener(writer: Writer) {
                            val bundle = Bundle()
                            bundle.putSerializable("writer", writer)
                            findNavController().navigate(R.id.writerInfoFragment, bundle)
                        }

                    }, root.context, 0)
                root.rv_writers.adapter = recyclerViewAdapter
            }
        })

        return root

    }

    override fun onResume() {
        super.onResume()

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