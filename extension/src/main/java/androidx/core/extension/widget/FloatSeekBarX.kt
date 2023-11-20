package androidx.core.extension.widget

import android.widget.SeekBar
import androidx.core.extension.widget.seekbar.FloatSeekBar

fun FloatSeekBar.doOnProgressChanged(action: (seekBar: FloatSeekBar, progress: Float, fromUser: Boolean) -> Unit): SeekBar =
    setOnSeekBarChangeListener(onProgressChanged = action)

fun FloatSeekBar.doOnStartTrackingTouch(action: (seekBar: FloatSeekBar) -> Unit): SeekBar =
    setOnSeekBarChangeListener(onStartTrackingTouch = action)

fun FloatSeekBar.doOnStopTrackingTouch(action: (seekBar: FloatSeekBar) -> Unit): SeekBar =
    setOnSeekBarChangeListener(onStopTrackingTouch = action)

fun FloatSeekBar.setOnSeekBarChangeListener(
    onProgressChanged: (seekBar: FloatSeekBar, progress: Float, fromUser: Boolean) -> Unit = { _, _, _ -> },
    onStartTrackingTouch: (seekBar: FloatSeekBar) -> Unit = { _ -> },
    onStopTrackingTouch: (seekBar: FloatSeekBar) -> Unit = { _ -> },
): FloatSeekBar {
    val listener = object : FloatSeekBar.OnFloatSeekBarChangeListener {
        override fun onProgressChanged(
            seekBar: FloatSeekBar,
            progress: Float,
            fromUser: Boolean
        ) = onProgressChanged.invoke(seekBar, progress, fromUser)

        override fun onStartTrackingTouch(seekBar: FloatSeekBar) =
            onStartTrackingTouch.invoke(seekBar)

        override fun onStopTrackingTouch(seekBar: FloatSeekBar) =
            onStopTrackingTouch.invoke(seekBar)
    }
    setOnFloatSeekBarChangeListener(listener)
    return this
}