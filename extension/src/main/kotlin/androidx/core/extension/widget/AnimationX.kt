package androidx.core.extension.widget

import android.view.animation.Animation

fun Animation.doOnAnimationStart(action: (animation: Animation) -> Unit): Animation =
    setAnimationListener(onAnimationStart = action)

fun Animation.doOnAnimationEnd(action: (animation: Animation) -> Unit): Animation =
    setAnimationListener(onAnimationEnd = action)

fun Animation.doOnAnimationRepeat(action: (animation: Animation) -> Unit): Animation =
    setAnimationListener(onAnimationRepeat = action)

fun Animation.setAnimationListener(
    onAnimationRepeat: (animation: Animation) -> Unit = {},
    onAnimationEnd: (animation: Animation) -> Unit = {},
    onAnimationStart: (animation: Animation) -> Unit = {},
): Animation {
    val listener = object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation) = onAnimationRepeat.invoke(animation)
        override fun onAnimationEnd(animation: Animation) = onAnimationEnd.invoke(animation)
        override fun onAnimationStart(animation: Animation) = onAnimationStart.invoke(animation)
    }
    setAnimationListener(listener)
    return this
}