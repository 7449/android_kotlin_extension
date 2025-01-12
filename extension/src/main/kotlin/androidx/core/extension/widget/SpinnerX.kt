package androidx.core.extension.widget

import android.widget.PopupWindow
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.ListPopupWindow

fun Spinner.avoidDropdownFocus() {
    runCatching {
        val isAppCompat = this is AppCompatSpinner
        val spinnerClass =
            if (isAppCompat) AppCompatSpinner::class.java else Spinner::class.java
        val popupWindowClass =
            if (isAppCompat) ListPopupWindow::class.java else android.widget.ListPopupWindow::class.java
        val listPopup = spinnerClass
            .getDeclaredField("mPopup")
            .apply { isAccessible = true }
            .get(this)
        if (popupWindowClass.isInstance(listPopup)) {
            val popup = popupWindowClass
                .getDeclaredField("mPopup")
                .apply { isAccessible = true }
                .get(listPopup)
            if (popup is PopupWindow) {
                popup.isFocusable = false
            }
        }
    }
}