package uz.mobiler.adiblar.ui.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.adapters.recycler.BooksMainCategoryRecyclerAdapter
import uz.mobiler.adiblar.adapters.recycler.BooksRecyclerViewAdapter
import uz.mobiler.adiblar.databinding.FragmentLibraryBinding
import uz.mobiler.adiblar.models.Book


class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: BooksMainCategoryRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)

        firebaseDatabase = FirebaseDatabase.getInstance()

        adapter = BooksMainCategoryRecyclerAdapter({
            val bundle = Bundle()
            bundle.putSerializable("book", it)
            findNavController().navigate(R.id.bookFragment, bundle)
        }, {
            val bundle = Bundle()
            bundle.putString("category", it)
            findNavController().navigate(R.id.bookSearchFragment, bundle)
        })


        databaseReference = firebaseDatabase.getReference("library")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<String>()
                snapshot.children.forEach {
                    val value = it.key
                    if (value != null) {
                        list.add(value)
                    }
                }
                adapter.differ.submitList(list)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        binding.rvMainCategories.adapter = adapter

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}