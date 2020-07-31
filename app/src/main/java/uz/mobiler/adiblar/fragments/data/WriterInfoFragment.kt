package uz.mobiler.adiblar.fragments.data

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.content_scrolling.view.*
import kotlinx.android.synthetic.main.fragment_writer_info.view.*
import kotlinx.android.synthetic.main.fragment_writer_info.view.like_back
import kotlinx.android.synthetic.main.item_writer.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.database.MyDBHelper
import uz.mobiler.adiblar.models.Writer


class WriterInfoFragment : Fragment() {

    private lateinit var root: View
    lateinit var writer: Writer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_writer_info, container, false)

        val myDBHelper = MyDBHelper(root.context)
        if (arguments != null) {
            writer = arguments?.getSerializable("writer") as Writer
        }

        val toolbar: androidx.appcompat.widget.Toolbar = root.findViewById(R.id.toolbar)
        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(toolbar)

        root.toolbar_layout.title = writer.writer
        root.toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)
        root.toolbar_layout.setExpandedTitleColor(Color.BLACK)

        root.like_btn1.isLiked = myDBHelper.getWriterById(writer)

        if (myDBHelper.getWriterById(writer)) {
            root.like_back.setBackgroundResource(R.drawable.unlike_background)
        } else {
            root.like_back.setBackgroundResource(R.drawable.like_background)
        }

        root.like_btn1.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                myDBHelper.addWriter(writer)
                root.like_back.setBackgroundResource(R.drawable.unlike_background)
            }

            override fun unLiked(likeButton: LikeButton?) {
                myDBHelper.deleteWriter(writer)
                root.like_back.setBackgroundResource(R.drawable.like_background_white)
            }

        })

        Glide.with(root).load(writer.imgUrl).placeholder(R.drawable.place_holder)
            .centerCrop().error(R.drawable.error_image).into(root.iv_image)

        root.tv_info.text = writer.description

        root.tv_birthDeath.text = ("(${writer.birthDead})")

        root.btn_back.setOnClickListener {
            findNavController().popBackStack()
        }
        return root
    }

}