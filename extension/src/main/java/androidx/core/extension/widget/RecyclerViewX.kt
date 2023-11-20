package androidx.core.extension.widget

import androidx.core.extension.widget.recyclerview.SimpleOnLoadMoreListener
import androidx.core.extension.widget.recyclerview.SimpleScrollListener
import androidx.recyclerview.widget.RecyclerView

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


fun RecyclerView.addSimpleLoadMoreScroll(listener: SimpleOnLoadMoreListener) = apply {
    addOnScrollListener(SimpleScrollListener(listener))
}