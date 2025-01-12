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
import androidx.annotation.IdRes
import androidx.core.extension.R
import androidx.core.extension.content.activityInfo
import androidx.core.extension.databinding.ExtLayoutDialogLoadingBinding
import androidx.core.extension.net.filePath
import androidx.core.extension.os.getNotNull
import androidx.core.extension.os.getOrNull
import androidx.core.extension.os.mainHandler
import androidx.core.extension.util.lazyValue
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
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
    lazyValue { intent.extras?.getOrNull(key, defaultValue) ?: defaultValue }

inline fun <reified T> Activity.bundle(key: String): Lazy<T> =
    lazyValue { requireNotNull(intent.extras?.getNotNull<T>(key)) }

inline fun <reified T> Activity.bundleOrNull(key: String): Lazy<T?> =
    lazyValue { intent.extras?.getOrNull<T>(key) }

fun Activity?.isAlive(): Boolean {
    return this != null && !isFinishing && !isDestroyed
}

@Suppress("DEPRECATION")
fun Activity.hideStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}

@Suppress("DEPRECATION")
fun Activity.showStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.show(WindowInsets.Type.statusBars())
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
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


inline fun <reified T : Fragment> FragmentActivity.findFragmentByTag(tag: String): T? {
    return supportFragmentManager.findFragmentByTag(tag) as? T
}

inline fun <reified T : Fragment> FragmentActivity.findFragmentByTag(): T? {
    return findFragmentByTag(T::class.java.simpleName) as? T
}

inline fun <reified T : Fragment> FragmentActivity.findFragmentByTag(
    tag: String,
    ifNone: (String) -> T,
): T = findFragmentByTag(tag) ?: ifNone(tag)

fun FragmentActivity.fragments(): MutableList<Fragment> =
    supportFragmentManager.fragments

fun FragmentActivity.findFragmentByTag(
    tag: String,
    of: (fragment: Fragment?) -> Unit,
) {
    of.invoke(supportFragmentManager.findFragmentByTag(tag))
}

fun FragmentActivity.findFragmentById(
    @IdRes id: Int,
    of: (fragment: Fragment?) -> Unit,
) {
    of.invoke(supportFragmentManager.findFragmentById(id))
}

fun FragmentActivity.beginTransaction() = supportFragmentManager.beginTransaction()

fun FragmentActivity.showRunOnCommit(
    fragment: Fragment,
    runnable: Runnable = Runnable { },
): FragmentTransaction = beginTransaction().show(fragment).runOnCommit(runnable)

fun FragmentActivity.showFragment(
    fragment: Fragment,
    type: CommitType = CommitType.COMMIT_ALLOWING_STATE_LOSS,
) = beginTransaction().show(fragment).commit(type)

fun FragmentActivity.hideFragment(
    fragment: Fragment,
    type: CommitType = CommitType.COMMIT_ALLOWING_STATE_LOSS,
) = beginTransaction().hide(fragment).commit(type)

fun FragmentActivity.addFragment(
    id: Int,
    fragment: Fragment,
    type: CommitType = CommitType.COMMIT_ALLOWING_STATE_LOSS,
) = beginTransaction().add(id, fragment, fragment.javaClass.simpleName).commit(type)

fun FragmentActivity.replaceFragment(
    id: Int,
    fragment: Fragment,
    type: CommitType = CommitType.COMMIT_ALLOWING_STATE_LOSS,
) = beginTransaction().replace(id, fragment, fragment.javaClass.simpleName).commit(type)

private fun FragmentTransaction.commit(type: CommitType) {
    when (type) {
        CommitType.COMMIT -> commit()
        CommitType.COMMIT_ALLOWING_STATE_LOSS -> commitAllowingStateLoss()
        CommitType.NOW -> commitNow()
        CommitType.NOW_ALLOWING_STATE_LOSS -> commitNowAllowingStateLoss()
    }
}

enum class CommitType {
    COMMIT,
    COMMIT_ALLOWING_STATE_LOSS,
    NOW,
    NOW_ALLOWING_STATE_LOSS
}