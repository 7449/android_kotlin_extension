package androidx.core.extension.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.extension.content.activityInfo
import androidx.core.extension.getCompatible
import androidx.core.extension.getOrNullCompatible
import androidx.core.extension.hideStatusBarCompatible
import androidx.core.extension.net.filePath
import androidx.core.extension.showStatusBarCompatible
import androidx.core.extension.util.lazyValue
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

@JvmName("viewBindingByView")
inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingView: (View) -> T,
) = lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    bindingView.invoke(findViewById<ViewGroup>(android.R.id.content).getChildAt(0))
}

@JvmName("viewBindingByInflater")
inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T,
) = lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    bindingInflater.invoke(layoutInflater).apply { setContentView(root) }
}

inline fun <reified T : Activity> Context.startActivity(bundle: Bundle = bundleOf()) {
    startActivity(Intent(this, T::class.java).apply {
        putExtras(bundle)
        if (this@startActivity !is Activity) {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    })
}

inline fun <reified T : Activity> View.startActivity(bundle: Bundle = bundleOf()) {
    context.startActivity<T>(bundle)
}

inline fun <reified T : Activity> Fragment.startActivity(bundle: Bundle = bundleOf()) {
    activity?.startActivity<T>(bundle)
}

inline fun <reified T : ViewModel> AppCompatActivity.viewModels() =
    lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ViewModelProvider(this)[T::class.java] }

inline fun <reified T> Activity.bundle(key: String, defaultValue: T): Lazy<T> =
    lazyValue { intent.extras?.getCompatible(key, defaultValue) ?: defaultValue }

inline fun <reified T> Activity.bundle(key: String): Lazy<T> =
    lazyValue { requireNotNull(intent.extras?.getCompatible<T>(key)) }

inline fun <reified T> Activity.bundleOrNull(key: String): Lazy<T?> =
    lazyValue { intent.extras?.getOrNullCompatible<T>(key) }

inline fun <reified T : Fragment> AppCompatActivity.findFragmentByTag(tag: String): T? {
    return supportFragmentManager.findFragmentByTag(tag) as? T
}

inline fun <reified T : Fragment> AppCompatActivity.findFragmentByTag(): T? {
    return findFragmentByTag(T::class.java.simpleName) as? T
}

inline fun <reified T : Fragment> AppCompatActivity.findFragmentByTag(
    tag: String,
    ifNone: (String) -> T
): T = findFragmentByTag(tag) ?: ifNone(tag)

fun Activity?.isAlive(): Boolean {
    return this != null && !isFinishing && !isDestroyed
}

fun AppCompatActivity.fragments(): MutableList<Fragment> =
    supportFragmentManager.fragments

fun AppCompatActivity.findFragmentByTag(tag: String, of: (fragment: Fragment?) -> Unit) {
    of.invoke(supportFragmentManager.findFragmentByTag(tag))
}

fun AppCompatActivity.findFragmentById(@IdRes id: Int, of: (fragment: Fragment?) -> Unit) {
    of.invoke(supportFragmentManager.findFragmentById(id))
}

fun AppCompatActivity.beginTransaction() = supportFragmentManager.beginTransaction()

fun AppCompatActivity.showRunOnCommit(
    fragment: Fragment,
    runnable: Runnable = Runnable { },
): FragmentTransaction = beginTransaction().show(fragment).runOnCommit(runnable)

fun AppCompatActivity.showFragment(
    fragment: Fragment,
    type: CommitType = CommitType.COMMIT_ALLOWING_STATE_LOSS,
) = beginTransaction().show(fragment).commit(type)

fun AppCompatActivity.hideFragment(
    fragment: Fragment,
    type: CommitType = CommitType.COMMIT_ALLOWING_STATE_LOSS,
) = beginTransaction().hide(fragment).commit(type)

fun AppCompatActivity.addFragment(
    id: Int,
    fragment: Fragment,
    type: CommitType = CommitType.COMMIT_ALLOWING_STATE_LOSS,
) = beginTransaction().add(id, fragment, fragment.javaClass.simpleName).commit(type)

fun AppCompatActivity.replaceFragment(
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