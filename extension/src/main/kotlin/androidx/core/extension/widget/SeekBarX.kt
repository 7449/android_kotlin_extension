package androidx.core.extension.widget

import android.widget.SeekBar

fun SeekBar.doOnProgressChanged(action: (seekBar: SeekBar, progress: Int, fromUser: Boolean) -> Unit): SeekBar =
    setOnSeekBarChangeListener(onProgressChanged = action)

fun SeekBar.doOnStartTrackingTouch(action: (seekBar: SeekBar) -> Unit): SeekBar =
    setOnSeekBarChangeListener(onStartTrackingTouch = action)

fun SeekBar.doOnStopTrackingTouch(action: (seekBar: SeekBar) -> Unit): SeekBar =
    setOnSeekBarChangeListener(onStopTrackingTouch = action)

fun SeekBar.setOnSeekBarChangeListener(
    onProgressChanged: (seekBar: SeekBar, progress: Int, fromUser: Boolean) -> Unit = { _, _, _ -> },
    onStartTrackingTouch: (seekBar: SeekBar) -> Unit = { _ -> },
    onStopTrackingTouch: (seekBar: SeekBar) -> Unit = { _ -> },
): SeekBar {
    val listener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) =
            onProgressChanged.invoke(seekBar, progress, fromUser)

        override fun onStartTrackingTouch(seekBar: SeekBar) =
            onStartTrackingTouch.invoke(seekBar)

        override fun onStopTrackingTouch(seekBar: SeekBar) =
            onStopTrackingTouch.invoke(seekBar)
    }
    setOnSeekBarChangeListener(listener)
    return this
}