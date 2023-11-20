package androidx.core.extension.widget

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager

fun View.addOnPreDrawListener(action: () -> Unit) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            action.invoke()
            return true
        }
    })
}

fun View.addOnGlobalLayoutListener(action: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            action.invoke()
        }
    })
}

fun View.postDelayed(delayInMillis: Long, action: () -> Unit): Runnable {
    val runnable = Runnable { action() }
    postDelayed(runnable, delayInMillis)
    return runnable
}

fun View.offKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun View.openKeyboard() {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    findFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(this, 0)
}

fun View.closeKeyboard() {
    val imm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        context.getSystemService(InputMethodManager::class.java)
    else
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

inline fun <reified T : View> View.parentView(): T? {
    if (parent is T) return parent as T
    return null
}

inline fun <reified T : ViewGroup> View.parentViewGroup(): T? {
    return parentView()
}

inline fun <reified T : View> View.isParentView(): Boolean {
    return parent is T
}

inline fun <reified T : ViewGroup> View.isParentViewGroup(): Boolean {
    return isParentView<T>()
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.isGone() = visibility == View.GONE

fun View.isInVisible() = visibility == View.INVISIBLE

fun View.visible() = apply { if (!isVisible()) visibility = View.VISIBLE }

fun View.gone() = apply { if (!isGone()) visibility = View.GONE }

fun View.invisible() = apply { if (!isInVisible()) visibility = View.INVISIBLE }

fun goneViews(vararg views: View?) {
    for (view in views) {
        view?.gone()
    }
}

fun visibleViews(vararg views: View?) {
    for (view in views) {
        view?.visible()
    }
}

fun invisibleViews(vararg views: View?) {
    for (view in views) {
        view?.invisible()
    }
}

fun View.inflate(): LayoutInflater {
    return LayoutInflater.from(context)
}

fun View.removeViewFormParent() {
    val parent = parent
    if (parent is ViewGroup) {
        parent.removeView(this)
    }
}

fun View.parent(container: (group: ViewGroup) -> Unit) {
    val parent = parent
    if (parent is ViewGroup) {
        container.invoke(parent)
    }
}