package androidx.core.extension.recyclerview.databinding.adapter

import android.view.ViewGroup
import androidx.core.extension.recyclerview.SimpleAdapter
import androidx.core.extension.recyclerview.databinding.DATABIND_ING_HIDE_MESSAGE
import androidx.core.extension.recyclerview.onClick
import androidx.core.extension.recyclerview.onLongClick
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

@Deprecated(DATABIND_ING_HIDE_MESSAGE)
class SimpleDataBindAdapter : SimpleAdapter<Any, SimpleDataBindAdapter.DataBindViewHolder>() {

    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    class DataBindViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    private val holders = arrayListOf<DataBindHolder<Any>>()

    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    fun <T> addHolder(holder: DataBindHolder<T>) = also {
        if (holders.find { it.clazz == holder.clazz } != null) return@also
        @Suppress("UNCHECKED_CAST")
        holders.add(holder as DataBindHolder<Any>)
    }

    override fun getItemViewType(position: Int): Int {
        return holders.indexOfFirst { it.clazz == getItem(position)::class.java }
    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): DataBindViewHolder {
        val holder = holders[viewType]
        val variables = holder.variables.filter { it.immutable }
        val factory = holder.factory
        return DataBindViewHolder(factory.onCreateViewBind(parent).apply {
            variables.forEach { setVariable(it.variable, it.value) }
            factory.immutableVariable.forEach { setVariable(it.variable, it.value) }
        }).apply {
            onClick {
                factory.onClickItem(itemView, it, getItem(it))
                factory.onClickItem(itemView, it, getItem(it), items)
            }
            onLongClick {
                factory.onLongClickItem(itemView, it, getItem(it))
                factory.onLongClickItem(itemView, it, getItem(it), items)
            }
        }
    }

    override fun onBindViewHolder(holder: DataBindViewHolder, position: Int, item: Any) {
        val bindHolder = holders.getOrNull(getItemViewType(position)) ?: return
        val variables = bindHolder.variables.filter { !it.immutable }
        variables.forEach {
            holder.binding.setVariable(it.variable, it.value ?: item)
        }
        bindHolder.factory.onBindVariable(holder.binding, position, item)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        holders.clear()
    }

    override fun setOnItemClickListener(listener: OnItemClickListener<Any>) {
        throw RuntimeException("Unsupported method")
    }

}