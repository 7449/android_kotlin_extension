package androidx.core.extension.widget

import android.content.Context
import android.widget.Toast

fun String.toastShort(context: Context): Unit =
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()

fun String.toastLong(context: Context): Unit =
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()

fun Context.toastShortRes(res: Int) = toastShort(getString(res))

fun Context.toastShort(any: Any): Unit =
    Toast.makeText(this, any.toString(), Toast.LENGTH_SHORT).show()

fun Context.toastLongRes(res: Int) = toastLong(getString(res))

fun Context.toastLong(any: Any): Unit =
    Toast.makeText(this, any.toString(), Toast.LENGTH_LONG).show()