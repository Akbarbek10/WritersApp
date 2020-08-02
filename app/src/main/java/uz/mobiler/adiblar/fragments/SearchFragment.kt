package uz.mobiler.adiblar.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_search.view.rv_writers
import kotlinx.android.synthetic.main.fragment_writer_data.view.*
import kotlinx.android.synthetic.main.fragment_writers.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.adapters.recycler.RecyclerViewAdapter
import uz.mobiler.adiblar.database.MyDBHelper
import uz.mobiler.adiblar.models.Writer
import uz.mobiler.adiblar.utils.MySharedPreference
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment(), RecyclerViewAdapter.OnItemClick {

    private lateinit var root: View
    private lateinit var writerList: ArrayList<Writer>
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_search, container, false)
        firebaseDatabase = FirebaseDatabase.getInstance()

        MySharedPreference.init(root.context)
        setLocale()

        val myDBHelper = MyDBHelper(root.context)

        when (arguments?.getInt("search")) {
            0 -> {
                val category = listOf("classic", "uzbek", "world")
                writerList = ArrayList()
                category.forEach { it ->
                    databaseReference = firebaseDatabase.getReference(it)
                    databaseReference.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {

                        }

                        override fun onDataChange(snapshot: DataSnapshot) {

                            val children = snapshot.children
                            for (child in children) {
                                val writer = child.getValue(Writer::class.java)
                                if (writer != null) {
                                    writerList.add(writer)
                                }
                            }
                            root.rv_writers.adapter =
                                RecyclerViewAdapter(
                                    writerList,
                                    this@SearchFragment,
                                    root.context,
                                    0
                                )
                            root.et_search.addTextChangedListener { item ->
                                val searchList = ArrayList<Writer>()
                                for (writer in writerList) {
                                    if (writer.writer.toLowerCase(Locale.getDefault())
                                            .startsWith(
                                                item.toString().toLowerCase(Locale.getDefault())
                                            )
                                    ) {
                                        searchList.add(writer)
                                    }
                                }
                                root.rv_writers.adapter =
                                    RecyclerViewAdapter(
                                        searchList,
                                        this@SearchFragment,
                                        root.context,
                                        0
                                    )
                            }
                            root.iv_cancel.setOnClickListener {
                                if (root.et_search.text.toString().isEmpty()) {
                                    findNavController().popBackStack()
                                } else {
                                    root.et_search.setText("")

                                }
                            }
                        }
                    })
                }
            }
            1 -> {
                val allWriters = myDBHelper.getAllWriters()
                root.rv_writers.adapter =
                    RecyclerViewAdapter(
                        allWriters as ArrayList<Writer>,
                        this@SearchFragment,
                        root.context,
                        0
                    )
                root.et_search.addTextChangedListener { item ->
                    val searchList = ArrayList<Writer>()
                    for (writer in allWriters) {
                        if (writer.writer.toLowerCase(Locale.getDefault())
                                .startsWith(item.toString().toLowerCase(Locale.getDefault()))
                        ) {
                            searchList.add(writer)
                        }
                    }
                    root.rv_writers.adapter =
                        RecyclerViewAdapter(searchList, this@SearchFragment, root.context, 0)
                }
                root.iv_cancel.setOnClickListener {
                    if (root.et_search.text.toString().isEmpty()) {
                        findNavController().popBackStack()
                    } else {
                        root.et_search.setText("")

                    }
                }
            }
        }


        return root
    }

    override fun onItemClickListener(writer: Writer) {
        val bundle = Bundle()
        bundle.putSerializable("writer", writer)
        findNavController().navigate(R.id.writerInfoFragment, bundle)
    }

    private fun setLocale() {
        val locale = Locale(MySharedPreference.language!!)
        Locale.setDefault(locale)
        val config: Configuration = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}