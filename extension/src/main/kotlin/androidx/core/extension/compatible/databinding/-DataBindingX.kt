package androidx.core.extension.compatible.databinding

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.extension.compatible.databinding.adapter.DataBindAdapter
import androidx.core.extension.compatible.databinding.adapter.DataBindHolder
import androidx.core.extension.compatible.databinding.adapter.SimpleDataBindAdapter
import androidx.core.extension.compatible.databinding.adapter.Variable
import androidx.core.extension.widget.gone
import androidx.core.extension.widget.recyclerview.SimpleAdapter
import androidx.core.extension.widget.visible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

const val DATABIND_ING_HIDE_MESSAGE = "ksp does not support databinding"

@Deprecated(DATABIND_ING_HIDE_MESSAGE)
fun RecyclerView.dataBindAdapter(): SimpleDataBindAdapter {
    if (adapter == null) {
        adapter = SimpleDataBindAdapter()
    }
    return adapter as SimpleDataBindAdapter
}

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated(DATABIND_ING_HIDE_MESSAGE)
val RecyclerView.simpleAdapter: SimpleAdapter<*, *>?
    get() = adapter as? SimpleAdapter<*, *>

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated(DATABIND_ING_HIDE_MESSAGE)
val RecyclerView.simpleAdapterItems: List<Any>
    get() = simpleAdapter?.items.orEmpty()

@Deprecated(DATABIND_ING_HIDE_MESSAGE)
fun <T> RecyclerView.simpleAdapterItems(): List<T> {
    @Suppress("UNCHECKED_CAST")
    return simpleAdapter?.items.orEmpty() as List<T>
}

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated(DATABIND_ING_HIDE_MESSAGE)
inline fun <reified T> SimpleDataBindAdapter.addHolder(
    variable: Int,
    factory: DataBindAdapter<T>
) = addHolder(arrayOf(Variable(variable)), factory)

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated(DATABIND_ING_HIDE_MESSAGE)
inline fun <reified T> SimpleDataBindAdapter.addHolder(
    variables: Array<Variable>,
    factory: DataBindAdapter<T>
) = addHolder(DataBindHolder(variables, T::class.java, factory))

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated(DATABIND_ING_HIDE_MESSAGE)
@BindingAdapter(value = ["bindVisible"])
fun View.bindVisible(boolean: Boolean) {
    if (boolean) visible() else gone()
}

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated(DATABIND_ING_HIDE_MESSAGE)
@BindingAdapter(value = ["bindTagKey", "bindTagValue"], requireAll = true)
fun View.bindTag(key: Int, value: Any) {
    setTag(key, value)
}

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated(DATABIND_ING_HIDE_MESSAGE)
@BindingAdapter(value = ["bindText"])
fun TextView.bindText(value: String) {
    text = value
}

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated(DATABIND_ING_HIDE_MESSAGE)
@BindingAdapter(value = ["bindText"])
fun Toolbar.bindText(value: String) {
    title = value
}