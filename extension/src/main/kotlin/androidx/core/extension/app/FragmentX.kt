package androidx.core.extension.app

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.extension.app.lifecycle.FragmentViewBindingDelegate
import androidx.core.extension.content.startActivity
import androidx.core.extension.os.getOrNull
import androidx.core.extension.util.lazyValue
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

inline fun <reified T : Fragment> newFragment(bundle: Bundle = bundleOf()): T {
    return T::class.java.getDeclaredConstructor().newInstance().apply {
        arguments = bundle
    }
}

inline fun <reified T : Activity> Fragment.startActivity(bundle: Bundle = bundleOf()) {
    activity?.startActivity<T>(bundle)
}

inline fun <reified T : ViewBinding> Fragment.viewBinding(noinline factory: (View) -> T) =
    FragmentViewBindingDelegate(this, factory)

inline fun <reified T> Fragment.bundle(key: String, defaultValue: T): Lazy<T> =
    lazyValue { arguments?.getOrNull(key, defaultValue) ?: defaultValue }

inline fun <reified T> Fragment.activityBundle(key: String, defaultValue: T): Lazy<T> =
    lazyValue { activity?.intent?.extras?.getOrNull(key, defaultValue) ?: defaultValue }

inline fun <reified T> Fragment.bundle(key: String): Lazy<T> =
    lazyValue { requireNotNull(arguments?.getOrNull<T>(key)) }

inline fun <reified T> Fragment.activityBundle(key: String): Lazy<T> =
    lazyValue { requireNotNull(activity?.intent?.extras?.getOrNull(key)) }

inline fun <reified T> Fragment.bundleOrNull(key: String): Lazy<T?> =
    lazyValue { arguments?.getOrNull<T>(key) }

inline fun <reified T> Fragment.activityBundleOrNull(key: String): Lazy<T?> =
    lazyValue { activity?.intent?.extras?.getOrNull<T>(key) }

fun Fragment.requireRunOnUiThread(action: () -> Unit) =
    requireActivity().runOnUiThread { action.invoke() }

fun Fragment.runOnUiThread(action: () -> Unit) =
    activity?.runOnUiThread { action.invoke() }