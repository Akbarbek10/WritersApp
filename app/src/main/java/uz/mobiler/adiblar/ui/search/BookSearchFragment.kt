package uz.mobiler.adiblar.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.item_writer.view.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.adapters.recycler.BooksGirdRecyclerViewAdapter
import uz.mobiler.adiblar.databinding.FragmentBookSearchBinding
import uz.mobiler.adiblar.models.Book
import uz.mobiler.adiblar.utils.LotinKrilService
import uz.mobiler.adiblar.utils.MySharedPreference


class BookSearchFragment : Fragment() {
    private var _binding: FragmentBookSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var bookList: ArrayList<Book>
    private lateinit var adapter: BooksGirdRecyclerViewAdapter
    private var title: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookSearchBinding.inflate(inflater, container, false)

        title = arguments?.getString("category")

        var newTitle = title?.replace("_", " ")?.trim() ?: "Category"
        binding.tvTitle.text = when (newTitle) {
            "Badiiy Adabiyot" ->
                getString(R.string.fiction_txt)
            "Fan va talim" ->
                getString(R.string.science_education_txt)
            "IT sohasi" ->
                getString(R.string.it_txt)
            else ->
                "Category"
        }

        adapter = BooksGirdRecyclerViewAdapter {
            val bundle = Bundle()
            bundle.putSerializable("book", it)
            findNavController().navigate(R.id.bookFragment, bundle)
        }
        binding.rvBookList.adapter = adapter
        OverScrollDecoratorHelper.setUpOverScroll(binding.rvBookList, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        getBooks()
        filterActivate()


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun filterActivate() {
        val filterAdapter = ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.languages,
            R.layout.item_spinner
        )
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.filterLanguage.adapter = filterAdapter

        binding.filterLanguage?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    var tempList = arrayListOf<Book>()
                    when (position) {
                        0 -> {
                            tempList = bookList
                        }
                        1 -> {
                            bookList.forEach {
                                if(it.language.equals("English"))
                                    tempList.add(it)
                            }
                        }
                        2 -> {
                            bookList.forEach {
                                if(it.language.equals("Russian"))
                                    tempList.add(it)
                            }
                        }
                        3 -> {
                            bookList.forEach {
                                if(it.language.equals("Uzbek"))
                                    tempList.add(it)

                            }
                        }
                    }
                    adapter.differ.submitList(tempList)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
    }

    private fun getBooks() {
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase.getReference("library/$title")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    bookList = arrayListOf()
                    snapshot.children.forEach {
                        val value = it.getValue(Book::class.java)
                        if (value != null) {
                            bookList.add(value)
                        }
                    }
                    binding.progressCircular.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}