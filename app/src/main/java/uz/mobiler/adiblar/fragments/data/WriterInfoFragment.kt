package uz.mobiler.adiblar.fragments.data

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_writer_info.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.models.Writer


class WriterInfoFragment : Fragment() {

    private lateinit var root: View
    lateinit var writer: Writer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_writer_info, container, false)

        if (arguments != null) {
            writer = arguments?.getSerializable("writer") as Writer
        }

        val toolbar: androidx.appcompat.widget.Toolbar = root.findViewById(R.id.toolbar)

        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(toolbar)

        root.toolbar_layout.title = writer.writer
        root.toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)


        return root
    }

}