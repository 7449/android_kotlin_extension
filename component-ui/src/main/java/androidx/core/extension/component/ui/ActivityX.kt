package androidx.core.extension.component.ui

import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> ComponentActivity.viewModels() =
    lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ViewModelProvider(this)[T::class.java] }

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
