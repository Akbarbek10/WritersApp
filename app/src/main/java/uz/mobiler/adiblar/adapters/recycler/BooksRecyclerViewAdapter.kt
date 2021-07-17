package uz.mobiler.adiblar.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_writer.view.*
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.databinding.ItemBookBinding
import uz.mobiler.adiblar.models.Book

class BooksRecyclerViewAdapter(val onClick: (book: Book) -> Unit) :
    RecyclerView.Adapter<BooksRecyclerViewAdapter.VH>() {

    private var lastPosition = -1

    private val itemCallback = object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, itemCallback)

    inner class VH(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(book: Book?) {

            book?.apply {
                binding.tvName.text = name
                binding.tvAuthor.text = author

                Picasso.get().load(image)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.error_image)
                    .into(binding.ivBook)
            }

            binding.root.setOnClickListener {
                book?.let { b -> onClick(b) }
            }

            binding.likeBtn.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton?) {
                    itemView.like_back.setBackgroundResource(R.drawable.unlike_background)
                }

                override fun unLiked(likeButton: LikeButton?) {
                    itemView.like_back.setBackgroundResource(R.drawable.like_background)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        setAnimation(holder.itemView, position, holder.itemView.context.applicationContext)
        holder.onBind(differ.currentList[position])

    }

    override fun getItemCount(): Int = differ.currentList.size

    private fun setAnimation(viewToAnimate: View, position: Int, context: Context) {
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}