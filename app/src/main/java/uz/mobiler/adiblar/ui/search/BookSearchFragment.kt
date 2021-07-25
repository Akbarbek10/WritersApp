package uz.mobiler.adiblar.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_book_search.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.adapters.recycler.BooksGirdRecyclerViewAdapter
import uz.mobiler.adiblar.adapters.recycler.BooksRecyclerViewAdapter
import uz.mobiler.adiblar.databinding.FragmentBookSearchBinding
import uz.mobiler.adiblar.models.Book


class BookSearchFragment : Fragment() {

    private lateinit var firebaseDatabase: FirebaseDatabase

    private var _binding: FragmentBookSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookSearchBinding.inflate(inflater, container, false)

        val title = arguments?.getString("category")
        binding.tvTitle.text = title?.replace("_", " ") ?: "Category"

        val adapter = BooksGirdRecyclerViewAdapter {
            val bundle = Bundle()
            bundle.putSerializable("book", it)
            findNavController().navigate(R.id.bookFragment, bundle)
        }

        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase.getReference("library/$title")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val arrayList = arrayListOf<Book>()
                    snapshot.children.forEach {
                        val value = it.getValue(Book::class.java)
                        if (value != null) {
                            arrayList.add(value)
                        }
                    }

                    binding.progressCircular.visibility = View.GONE
                    adapter.differ.submitList(arrayList)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        binding.rvBookList.adapter = adapter

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}