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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import uz.mobiler.adiblar.R
import uz.mobiler.adiblar.databinding.ItemMainCategoryBinding
import uz.mobiler.adiblar.models.Book

class BooksMainCategoryRecyclerAdapter(
    val onClick: (book: Book) -> Unit,
    val onBooksClick: (s: String) -> Unit
) :
    RecyclerView.Adapter<BooksMainCategoryRecyclerAdapter.VH>() {

    private var lastPosition = -1

    private val itemCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, itemCallback)

    inner class VH(private val binding: ItemMainCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(s: String) {
            binding.tvTitle.text = s.replace("_", " ")

            val adapter = BooksRecyclerViewAdapter {
                onClick(it)
            }

            val firebaseDatabase = FirebaseDatabase.getInstance()
            firebaseDatabase.getReference("library/$s")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.tvCount.text = "${snapshot.childrenCount.toInt()}"
                        val arrayList = arrayListOf<Book>()
                        snapshot.children.forEach {
                            val value = it.getValue(Book::class.java)
                            if (value != null) {
                                arrayList.add(value)
                            }
                        }
                        adapter.differ.submitList(arrayList)
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            binding.tvAll.setOnClickListener {
                onBooksClick(s)
            }

            binding.rvBooks.adapter = adapter
            OverScrollDecoratorHelper.setUpOverScroll(binding.rvBooks, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        setAnimation(holder.itemView, position, holder.itemView.context.applicationContext)
        holder.onBind(differ.currentList[position])

    }

    override fun getItemCount(): Int = differ.currentList.size

    private fun setAnimation(viewToAnimate: View, position: Int, context: Context) {
        if (position > lastPosition) {
//            val animation: Animation =
//                AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
            val animation: Animation =
                AnimationUtils.loadAnimation(context, R.anim.item_animation_from_right)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}