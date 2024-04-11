package androidx.core.extension.component.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.extension.compatible.getCompatible
import androidx.core.extension.compatible.getOrNullCompatible
import androidx.core.extension.component.ui.fragment.FragmentViewBindingDelegate
import androidx.core.extension.content.startActivity
import androidx.core.extension.util.lazyValue
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

inline fun <reified T : ViewModel> Fragment.viewModels() =
    lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ViewModelProvider(this)[T::class.java] }

inline fun <reified T> Fragment.bundle(key: String, defaultValue: T): Lazy<T> =
    lazyValue { arguments?.getCompatible(key, defaultValue) ?: defaultValue }

inline fun <reified T> Fragment.activityBundle(key: String, defaultValue: T): Lazy<T> =
    lazyValue { activity?.intent?.extras?.getCompatible(key, defaultValue) ?: defaultValue }

inline fun <reified T> Fragment.bundle(key: String): Lazy<T> =
    lazyValue { requireNotNull(arguments?.getCompatible<T>(key)) }

inline fun <reified T> Fragment.activityBundle(key: String): Lazy<T> =
    lazyValue { requireNotNull(activity?.intent?.extras?.getCompatible(key)) }

inline fun <reified T> Fragment.bundleOrNull(key: String): Lazy<T?> =
    lazyValue { arguments?.getOrNullCompatible<T>(key) }

inline fun <reified T> Fragment.activityBundleOrNull(key: String): Lazy<T?> =
    lazyValue { activity?.intent?.extras?.getOrNullCompatible<T>(key) }

fun Fragment.requireRunOnUiThread(action: () -> Unit) =
    requireActivity().runOnUiThread { action.invoke() }

fun Fragment.runOnUiThread(action: () -> Unit) =
    activity?.runOnUiThread { action.invoke() }