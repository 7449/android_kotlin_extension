package androidx.core.extension.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.extension.R
import androidx.core.extension.databinding.ExtLayoutTabPagerBinding
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator

/**
 * class SampleFragment : TabFragment<SampleModel>() {
 *
 *     override fun createFragments(item: SampleModel): Fragment {
 *         return Fragment()
 *     }
 *
 *     override fun getItemTitle(item: SampleModel): String {
 *         return item.title
 *     }
 *
 *     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *         super.onViewCreated(view, savedInstanceState)
 *         TODO
 *     }
 *
 * }
 */
abstract class TabFragment<T> : Fragment(R.layout.ext_layout_tab_pager) {

    private val items = arrayListOf<T>()
    private val viewBinding by viewBinding(ExtLayoutTabPagerBinding::bind)
    private var mediator: TabLayoutMediator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.viewPager.adapter = TabAdapter(this)
        mediator = TabLayoutMediator(
            viewBinding.tabLayout,
            viewBinding.viewPager
        ) { tab, position -> tab.text = getItemTitle(items[position]) }
        mediator?.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediator?.detach()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun registerItem(item: List<T>) {
        items.clear()
        items.addAll(item)
        viewBinding.viewPager.adapter?.notifyDataSetChanged()
    }

    protected abstract fun createFragments(item: T): Fragment

    protected abstract fun getItemTitle(item: T): String

    private inner class TabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return items.size
        }

        override fun createFragment(position: Int): Fragment {
            return createFragments(items[position])
        }

    }

}