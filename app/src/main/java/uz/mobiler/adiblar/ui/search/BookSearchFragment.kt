package uz.mobiler.adiblar.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import uz.mobiler.adiblar.R


class BookSearchFragment : Fragment(){

    private lateinit var root: View
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_search, container, false)
        firebaseDatabase = FirebaseDatabase.getInstance()




        return root
    }


}