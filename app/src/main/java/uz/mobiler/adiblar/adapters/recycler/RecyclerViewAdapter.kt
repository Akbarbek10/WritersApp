package uz.mobiler.adiblar.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import uz.mobiler.adiblar.R


class RecyclerViewAdapter(
    var listener: OnItemClick?,
    var context: Context
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var lastPosition = -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(writer: String) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_writer, parent, false)
        )
    }

    override fun getItemCount(): Int = 15

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        setAnimation(holder.itemView, position)

        holder.itemView.setOnClickListener {
            listener?.onItemClickListener()
        }
    }

    interface OnItemClick {
        fun onItemClickListener()
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