package androidx.core.extension.net

import androidx.core.extension.app.showProgressDialog
import androidx.core.extension.content.showToastShort
import androidx.core.extension.os.postMainThread
import androidx.core.extension.viewmodel.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Fragment.simpleHttp(
    isDelayed: Boolean = false,
    transparent: Boolean = true,
    action: suspend () -> Unit,
) {
    activity?.simpleHttp(isDelayed, transparent, action)
}

fun FragmentActivity.simpleHttp(
    isDelayed: Boolean = false,
    transparent: Boolean = true,
    action: suspend () -> Unit,
) {
    postMainThread {
        showProgressDialog(isDelayed, transparent) { dialog ->
            launch({
                dialog?.dismiss()
                postMainThread { showToastShort(it.message.orEmpty()) }
            }) {
                action.invoke()
                dialog?.dismiss()
            }
        }
    }
}