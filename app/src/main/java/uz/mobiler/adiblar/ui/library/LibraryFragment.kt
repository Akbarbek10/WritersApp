package uz.mobiler.adiblar.ui.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_writers.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.adapters.recycler.BooksRecyclerViewAdapter
import uz.mobiler.adiblar.databinding.FragmentLibraryBinding
import uz.mobiler.adiblar.models.Book


class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: BooksRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("library")

        adapter = BooksRecyclerViewAdapter {
            val bundle = Bundle()
            bundle.putSerializable("book", it)
            findNavController().navigate(R.id.bookFragment, bundle)
        }

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<Book>()
                snapshot.children.forEach {
                    val value = it.getValue(Book::class.java)
                    if (value != null) {
                        for (i in 0..4){
                            list.add(value)
                        }

                    }
                }
                adapter.differ.submitList(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(binding.root.context, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        binding.ivSearch.setOnClickListener {
            val bundle = Bundle()
            findNavController().navigate(R.id.bookSearchFragment)
        }

        binding.rvLibraryWorldHistory.adapter = adapter
        binding.rvLibraryPopular.adapter = adapter

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}