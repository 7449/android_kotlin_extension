package androidx.core.extension.widget

import android.content.Context
import android.widget.Toast

fun String.toastShort(context: Context): Unit =
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()

fun String.toastLong(context: Context): Unit =
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()