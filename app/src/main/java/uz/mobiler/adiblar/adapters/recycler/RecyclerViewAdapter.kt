package uz.mobiler.adiblar.adapters.recycler

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.item_writer.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.database.MyDBHelper
import uz.mobiler.adiblar.models.Writer


class RecyclerViewAdapter(
    var writerList: List<Writer>,
    var listener: OnItemClick?,
    var context: Context
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    val myDBHelper = MyDBHelper(context)
    private var lastPosition = -1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(writer: Writer) {
            itemView.tv_name.text = writer.writer
            itemView.birthDeath.text = writer.birthDead

            if (myDBHelper.getWriterById(writer)) {
                itemView.like_back.setBackgroundResource(R.drawable.like_background)
            } else {
                itemView.like_back.setBackgroundResource(R.drawable.unlike_background)
            }
            itemView.like_btn.isLiked = myDBHelper.getWriterById(writer)
            Glide.with(itemView).load(writer.imgUrl).placeholder(R.drawable.place_holder)
                .centerCrop().error(R.drawable.error_image).into(itemView.image_url)
            itemView.like_btn.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton?) {
                    myDBHelper.addWriter(writer)
                    itemView.like_back.setBackgroundResource(R.drawable.unlike_background)
                }

                override fun unLiked(likeButton: LikeButton?) {
                    myDBHelper.deleteWriter(writer)
                    itemView.like_back.setBackgroundResource(R.drawable.like_background)
                }

            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_writer, parent, false)
        )
    }

    override fun getItemCount(): Int = writerList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBind(writerList[position])
        setAnimation(holder.itemView, position)

        holder.itemView.setOnClickListener {
            listener?.onItemClickListener(writerList[position])
        }

    }

    interface OnItemClick {
        fun onItemClickListener(writer: Writer)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}