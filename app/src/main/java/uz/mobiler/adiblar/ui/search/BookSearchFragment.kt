package uz.mobiler.adiblar.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_book_search.view.*
import uz.mobiler.adiblar.R


class BookSearchFragment : Fragment(){

    private lateinit var root: View
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_book_search, container, false)
        firebaseDatabase = FirebaseDatabase.getInstance()

        val popUpMenu = PopupMenu(root.context, root.spinner_left)
        popUpMenu.menuInflater.inflate(R.menu.menu_book_filter, popUpMenu.menu)
        popUpMenu.setOnMenuItemClickListener { menuItem ->
            var btnText = ""
            when(menuItem.itemId){
                R.id.all ->{
                    Toast.makeText(root.context, "all", Toast.LENGTH_SHORT).show()
                    btnText = "all"
                }
                R.id.red ->{
                    Toast.makeText(root.context, "red", Toast.LENGTH_SHORT).show()
                    btnText = "red"
                }
                R.id.green ->{
                    Toast.makeText(root.context, "green", Toast.LENGTH_SHORT).show()
                    btnText = "green"
                }
                R.id.blue ->{
                    Toast.makeText(root.context, "blue", Toast.LENGTH_SHORT).show()
                    btnText = "blue"
                }

            }
            root.spinner_left.text = btnText
            false
        }

        root.spinner_left.setOnClickListener {
            popUpMenu.show()
        }




        return root
    }


}