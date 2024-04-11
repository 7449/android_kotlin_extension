package androidx.core.extension.widget.seekbar

import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import kotlin.math.roundToInt

class FloatSeekBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : AppCompatSeekBar(context, attrs, defStyleAttr), SeekBar.OnSeekBarChangeListener {

    private var listener: OnFloatSeekBarChangeListener? = null

    init {
        setOnSeekBarChangeListener(this)
    }

    private var maxProgress = 100f
    private var minProgress = 0f
    private var progress = 0f

    fun setMin(min: Float) {
        minProgress = min
        val middle = getMiddle(maxProgress, min)
        super.setMax(middle)
    }

    fun setFloatProgress(progress: Float) {
        this.progress = progress
        val i = ((progress - minProgress) * 10).roundToInt()
        super.setProgress(i)
    }

    fun setMax(value: Float) {
        maxProgress = value
        super.setMax(getMiddle(maxProgress, minProgress))
    }

    fun setOnFloatSeekBarChangeListener(floatListener: OnFloatSeekBarChangeListener?) {
        this.listener = floatListener
        super.setMax(getMiddle(maxProgress, minProgress))
    }

    private fun getMiddle(floatMaxValue: Float, min: Float): Int {
        val v = floatMaxValue - min
        return (v * 10).roundToInt()
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if (listener != null) {
            this.progress = minProgress + progress / 10.0f
            listener?.onProgressChanged(this, this.progress, fromUser)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        listener?.onStartTrackingTouch(this)
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        listener?.onStopTrackingTouch(this)
    }

    interface OnFloatSeekBarChangeListener {
        fun onProgressChanged(seekBar: FloatSeekBar, progress: Float, fromUser: Boolean)
        fun onStartTrackingTouch(seekBar: FloatSeekBar)
        fun onStopTrackingTouch(seekBar: FloatSeekBar)
    }

}