@file:Suppress("MemberVisibilityCanBePrivate")

package androidx.core.extension.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    interface OnItemClickListener<T> {
        fun onItemClick(position: Int, item: T)
        fun onItemLongClick(position: Int, item: T) {}
    }

    open val items = arrayListOf<T>()
    private var clickListener: OnItemClickListener<T>? = null

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateHolder(parent, viewType).apply {
            clickListener?.let { listener ->
                onClick { listener.onItemClick(it, items[it]) }
                onLongClick { listener.onItemLongClick(it, items[it]) }
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, position, items[position])
    }

    abstract fun onCreateHolder(parent: ViewGroup, viewType: Int): VH

    abstract fun onBindViewHolder(holder: VH, position: Int, item: T)

    open fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        this.clickListener = listener
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    fun add(item: T) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun addAll(items: List<T>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun reset(items: List<T>) {
        this.items.clear()
        addAll(items)
    }

    fun put(items: List<T>, reset: Boolean) {
        if (reset) {
            reset(items)
        } else {
            addAll(items)
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

}