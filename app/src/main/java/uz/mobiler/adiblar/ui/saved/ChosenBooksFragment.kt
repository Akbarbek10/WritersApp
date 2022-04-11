package uz.mobiler.adiblar.ui.saved

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_chosen_books.view.*
import kotlinx.android.synthetic.main.fragment_chosen_writers.view.tv_noData
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.adapters.recycler.BooksGirdRecyclerViewAdapter
import uz.mobiler.adiblar.database.MyDBHelperBook
import uz.mobiler.adiblar.models.Book
import kotlin.collections.ArrayList

class ChosenBooksFragment : Fragment() {
    private lateinit var root: View
    private lateinit var myDBHelperBook: MyDBHelperBook
    private lateinit var booksList: ArrayList<Book>
    private lateinit var adapter: BooksGirdRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_chosen_books, container, false)
        myDBHelperBook = MyDBHelperBook(root.context)
        booksList = myDBHelperBook.getAllBooks()
        adapter = BooksGirdRecyclerViewAdapter() {
            val bundle = Bundle()
            bundle.putSerializable("book", it)
            findNavController().navigate(R.id.bookFragment, bundle)
        }
        adapter.differ.submitList(booksList)
        root.rv_books.adapter = adapter
        OverScrollDecoratorHelper.setUpOverScroll(root.rv_books, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        return root
    }

    override fun onResume() {
        super.onResume()
        booksList = myDBHelperBook.getAllBooks()

        if (booksList.isEmpty())
            root.tv_noData.visibility = View.VISIBLE
        else
            root.tv_noData.visibility = View.GONE
    }

}