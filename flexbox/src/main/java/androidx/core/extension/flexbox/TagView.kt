package androidx.core.extension.flexbox

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout

class TagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FlexboxLayout(context, attrs, defStyleAttr) {

    init {
        flexWrap = FlexWrap.WRAP
    }

    fun bindString(items: List<String>, textSize: Float = 10f, onClick: (text: String) -> Unit) {
        removeAllViews()
        items.forEach { value ->
            val flowText = TagItemView(context)
            flowText.text = value
            flowText.select(false)
            flowText.textSize = textSize
            flowText.setOnClickListener { onClick.invoke(value) }
            addView(flowText)
        }
    }

    fun <T : TagItem> bind(items: List<T>, textSize: Float = 10f, onClick: (text: T) -> Unit) {
        removeAllViews()
        items.forEach { value ->
            val flowText = TagItemView(context)
            flowText.text = value.tagText
            flowText.select(value.tagSelect)
            flowText.textSize = textSize
            flowText.setOnClickListener { onClick.invoke(value) }
            addView(flowText)
        }
    }

    private class TagItemView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : TextView(context, attrs, defStyleAttr) {

        init {
            setPadding(16, 10, 16, 10)
            val params = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(5, 5, 5, 5)
            gravity = Gravity.CENTER
            layoutParams = params
            setTextColor(Color.WHITE)
            setBackgroundResource(R.drawable.flexbox_shape_color_accent_25_bg)
        }

        fun select(select: Boolean) {
            if (select) {
                setTextColor(resources.getColor(android.R.color.black))
            } else {
                setTextColor(resources.getColor(android.R.color.white))
            }
        }

    }

}