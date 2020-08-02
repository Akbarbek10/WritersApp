package uz.mobiler.adiblar.adapters.recycler

import android.content.Context
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
import uz.mobiler.adiblar.utils.LotinKrilService
import uz.mobiler.adiblar.utils.MySharedPreference


class RecyclerViewAdapter(
    var writerList: ArrayList<Writer>,
    var listener: OnItemClick?,
    var context: Context,
    var type: Int
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    val myDBHelper = MyDBHelper(context)
    private var lastPosition = -1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(writer: Writer, position: Int) {
            MySharedPreference.init(context)

            val split = writer.writer.split(" ")
            var name: String = ""
            if (split.size > 1) {
                name = split[0] + "\n" + split[1]
            } else {
                name = writer.writer
            }
            when (MySharedPreference.language) {
                "uz" -> {
                    itemView.tv_name.text = name
                }
                "ru" -> {
                    itemView.tv_name.text = LotinKrilService.convert(name)
                }
            }
            itemView.birthDeath.text = writer.birthDead

            if (myDBHelper.getWriterById(writer)) {
                itemView.like_back.setBackgroundResource(R.drawable.unlike_background)
            } else {
                itemView.like_back.setBackgroundResource(R.drawable.like_background)
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
                    if (type == 1) {
                        writerList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, itemCount)
                    }
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

        holder.onBind(writerList[position], position)
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
                AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}