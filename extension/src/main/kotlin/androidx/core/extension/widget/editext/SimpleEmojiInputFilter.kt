package androidx.core.extension.widget.editext

import android.text.InputFilter
import android.text.Spanned

internal class SimpleEmojiInputFilter : InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int, end: Int,
        dest: Spanned,
        dstart: Int, dend: Int,
    ): CharSequence? {
        for (i in start until end) {
            val type = Character.getType(source[i])
            if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                return ""
            }
        }
        return null
    }
}