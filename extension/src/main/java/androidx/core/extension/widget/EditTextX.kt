package androidx.core.extension.widget

import android.os.Build
import android.text.InputFilter
import android.widget.EditText
import androidx.core.extension.widget.editext.SimpleEmojiInputFilter

fun EditText.noEmojiInput() = also {
    addInputFilter(SimpleEmojiInputFilter())
}

fun EditText.addInputFilter(inputFilter: InputFilter) {
    filters = filters.plus(inputFilter)
}

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