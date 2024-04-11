package androidx.core.extension.widget

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.TextView

fun TextView.doOnEditorGoActionListener(action: (view: TextView, str: String) -> Unit) {
    doOnEditorActionListener(EditorInfo.IME_ACTION_GO, action)
}

fun TextView.doOnEditorSearchActionListener(action: (view: TextView, str: String) -> Unit) {
    doOnEditorActionListener(EditorInfo.IME_ACTION_SEARCH, action)
}

fun TextView.doOnEditorNextActionListener(action: (view: TextView, str: String) -> Unit) {
    doOnEditorActionListener(EditorInfo.IME_ACTION_NEXT, action)
}

fun TextView.doOnEditorSendActionListener(action: (view: TextView, str: String) -> Unit) {
    doOnEditorActionListener(EditorInfo.IME_ACTION_SEND, action)
}

fun TextView.doOnEditorActionListener(
    actionId: Int,
    action: (view: TextView, str: String) -> Unit,
) {
    setOnEditorActionListener { v, id, _ ->
        if (id == actionId) {
            action.invoke(v, v.text.toString())
        }
        return@setOnEditorActionListener id == actionId
    }
}

fun TextView.doOnAfterTextChanged(action: (editable: Editable) -> Unit): TextWatcher =
    addTextChangedListener(afterTextChanged = action)

fun TextView.doOnBeforeTextChanged(action: (charSequence: CharSequence, start: Int, count: Int, after: Int) -> Unit): TextWatcher =
    addTextChangedListener(beforeTextChanged = action)

fun TextView.doOnTextChanged(action: (charSequence: CharSequence, start: Int, before: Int, count: Int) -> Unit): TextWatcher =
    addTextChangedListener(onTextChanged = action)

fun TextView.addTextChangedListener(
    afterTextChanged: (editable: Editable) -> Unit = { _: Editable -> },
    beforeTextChanged: (charSequence: CharSequence, start: Int, count: Int, after: Int) -> Unit = { _: CharSequence, _: Int, _: Int, _: Int -> },
    onTextChanged: (charSequence: CharSequence, start: Int, before: Int, count: Int) -> Unit = { _: CharSequence, _: Int, _: Int, _: Int -> },
): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) = afterTextChanged.invoke(s)
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) =
            beforeTextChanged.invoke(s, start, count, after)

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) =
            onTextChanged.invoke(s, start, before, count)
    }
    addTextChangedListener(textWatcher)
    return textWatcher
}

