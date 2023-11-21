package androidx.core.extension.widget

import androidx.viewpager.widget.ViewPager

fun ViewPager.doOnPageScrolled(action: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit): ViewPager.OnPageChangeListener =
    addOnPageChangeListener(onPageScrolled = action)

fun ViewPager.doOnPageSelected(action: (position: Int) -> Unit): ViewPager.OnPageChangeListener =
    addOnPageChangeListener(onPageSelected = action)

fun ViewPager.doOnPageScrollStateChanged(action: (state: Int) -> Unit): ViewPager.OnPageChangeListener =
    addOnPageChangeListener(onPageScrollStateChanged = action)

fun ViewPager.addOnPageChangeListener(
    onPageScrolled: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit = { _: Int, _: Float, _: Int -> },
    onPageSelected: (position: Int) -> Unit = { _: Int -> },
    onPageScrollStateChanged: (state: Int) -> Unit = { _: Int -> },
): ViewPager.OnPageChangeListener {
    val listener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int,
        ): Unit = onPageScrolled(position, positionOffset, positionOffsetPixels)

        override fun onPageSelected(position: Int): Unit = onPageSelected(position)
        override fun onPageScrollStateChanged(state: Int): Unit = onPageScrollStateChanged(state)
    }
    addOnPageChangeListener(listener)
    return listener
}