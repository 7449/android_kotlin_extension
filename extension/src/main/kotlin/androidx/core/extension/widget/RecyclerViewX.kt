package androidx.core.extension.widget

import android.annotation.SuppressLint
import androidx.core.extension.widget.recyclerview.SimpleOnLoadMoreListener
import androidx.core.extension.widget.recyclerview.SimpleScrollListener
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

fun RecyclerView.ViewHolder.onClick(action: (position: Int) -> Unit) {
    itemView.setOnClickListener {
        val position = bindingAdapterPosition
        if (position == RecyclerView.NO_POSITION) return@setOnClickListener
        action.invoke(position)
    }
}

fun RecyclerView.ViewHolder.onLongClick(action: (position: Int) -> Unit) {
    itemView.setOnLongClickListener {
        val position = bindingAdapterPosition
        if (position == RecyclerView.NO_POSITION) return@setOnLongClickListener true
        action.invoke(position)
        return@setOnLongClickListener true
    }
}

@SuppressLint("NotifyDataSetChanged")
fun RecyclerView.notify() {
    adapter?.notifyDataSetChanged()
}

class ViewBindingViewHolder<T : ViewBinding>(val viewBinding: T) :
    RecyclerView.ViewHolder(viewBinding.root)

fun RecyclerView.addSimpleLoadMoreScroll(listener: SimpleOnLoadMoreListener) = apply {
    addOnScrollListener(SimpleScrollListener(listener))
}