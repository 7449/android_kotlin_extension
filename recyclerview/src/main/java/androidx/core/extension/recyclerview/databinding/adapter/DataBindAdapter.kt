package androidx.core.extension.recyclerview.databinding.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.extension.recyclerview.databinding.DATABIND_ING_HIDE_MESSAGE
import androidx.databinding.ViewDataBinding

@Deprecated(DATABIND_ING_HIDE_MESSAGE)
interface DataBindAdapter<T> {
    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    val immutableVariable: Array<Variable> get() = arrayOf()

    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    fun onCreateViewBind(parent: ViewGroup): ViewDataBinding

    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    fun onBindVariable(bind: ViewDataBinding, index: Int, item: T) {
    }

    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    fun onClickItem(view: View, index: Int, item: T) {
    }

    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    fun onClickItem(view: View, index: Int, item: T, items: List<Any>) {
    }

    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    fun onLongClickItem(view: View, index: Int, item: T) {
    }

    @Deprecated(DATABIND_ING_HIDE_MESSAGE)
    fun onLongClickItem(view: View, index: Int, item: T, items: List<Any>) {
    }
}