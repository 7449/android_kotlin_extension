package androidx.core.extension.widget

import android.annotation.SuppressLint
import android.os.Build
import android.text.InputFilter
import android.text.Spanned
import android.widget.EditText

fun EditText.closeEmojiInput() = also {
    addInputFilter(SimpleEmojiInputFilter())
}

fun EditText.addInputFilter(inputFilter: InputFilter) {
    filters = filters.plus(inputFilter)
}

private class SimpleEmojiInputFilter : InputFilter {
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

@SuppressLint("ObsoleteSdkInt")
fun EditText.disableShowSoftInput() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        showSoftInputOnFocus = false
        return
    }
    runCatching {
        val method = EditText::class.java.getMethod(
            "setShowSoftInputOnFocus",
            Boolean::class.javaPrimitiveType
        )
        method.isAccessible = true
        method.invoke(this, false)
    }
    runCatching {
        val method = EditText::class.java.getMethod(
            "setSoftInputShownOnFocus",
            Boolean::class.javaPrimitiveType
        )
        method.isAccessible = true
        method.invoke(this, false)
    }
}