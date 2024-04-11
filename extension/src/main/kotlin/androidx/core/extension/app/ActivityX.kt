package androidx.core.extension.app

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.extension.R
import androidx.core.extension.compatible.getCompatible
import androidx.core.extension.compatible.getOrNullCompatible
import androidx.core.extension.compatible.hideStatusBarCompatible
import androidx.core.extension.compatible.showStatusBarCompatible
import androidx.core.extension.content.activityInfo
import androidx.core.extension.databinding.ExtLayoutDialogLoadingBinding
import androidx.core.extension.net.filePath
import androidx.core.extension.os.mainHandler
import androidx.core.extension.util.lazyValue
import androidx.core.os.postDelayed
import androidx.viewbinding.ViewBinding

@JvmName("viewBindingByView")
inline fun <reified T : ViewBinding> Activity.viewBinding(
    crossinline bindingView: (View) -> T,
) = lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    bindingView.invoke(findViewById<ViewGroup>(android.R.id.content).getChildAt(0))
}

@JvmName("viewBindingByInflater")
inline fun <reified T : ViewBinding> Activity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T,
) = lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    bindingInflater.invoke(layoutInflater).apply { setContentView(root) }
}

inline fun <reified T> Activity.bundle(key: String, defaultValue: T): Lazy<T> =
    lazyValue { intent.extras?.getCompatible(key, defaultValue) ?: defaultValue }

inline fun <reified T> Activity.bundle(key: String): Lazy<T> =
    lazyValue { requireNotNull(intent.extras?.getCompatible<T>(key)) }

inline fun <reified T> Activity.bundleOrNull(key: String): Lazy<T?> =
    lazyValue { intent.extras?.getOrNullCompatible<T>(key) }


fun Activity?.isAlive(): Boolean {
    return this != null && !isFinishing && !isDestroyed
}

fun Activity.hideStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
    } else {
        hideStatusBarCompatible()
    }
}

fun Activity.showStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.show(WindowInsets.Type.statusBars())
    } else {
        showStatusBarCompatible()
    }
}

fun Activity.flagSecure() {
    window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
}

fun Activity.taskAffinity(): String {
    return activityInfo(componentName)?.taskAffinity.orEmpty()
}

fun Activity.scanFile(uri: Uri, action: (uri: Uri) -> Unit) {
    scanFile(uri.filePath(this), action)
}

fun Activity.scanFile(path: String, action: (uri: Uri) -> Unit) {
    MediaScannerConnection.scanFile(this, arrayOf(path), null) { _: String?, uri: Uri? ->
        runOnUiThread {
            uri ?: return@runOnUiThread
            action.invoke(uri)
        }
    }
}

private const val DEFAULT_LOADING_DIALOG_DELAYED = 1500L

fun Activity.showProgressDialog(
    isDelayed: Boolean,
    transparent: Boolean,
    action: (dialog: Dialog?) -> Unit,
) {
    if (!isAlive()) return action.invoke(null)
    val dialog = Dialog(this, R.style.Theme_Sample_Dialog)
    val binding = ExtLayoutDialogLoadingBinding.inflate(LayoutInflater.from(this))
    dialog.setContentView(binding.root)
    dialog.setCancelable(false)
    dialog.window?.setGravity(Gravity.CENTER)
    if (transparent) {
        binding.root.setBackgroundColor(Color.TRANSPARENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    dialog.show()
    if (isDelayed) mainHandler.postDelayed(DEFAULT_LOADING_DIALOG_DELAYED) { action.invoke(dialog) }
    else action.invoke(dialog)
}