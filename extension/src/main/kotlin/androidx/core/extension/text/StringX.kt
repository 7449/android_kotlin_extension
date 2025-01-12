package androidx.core.extension.text

import android.os.Build
import android.text.Html
import java.util.regex.Pattern

const val utf8Coding: String = "utf-8"

const val textHtmlMimeType: String = "text/html"

val String.int: Int
    get() = Pattern.compile("[^0-9]").matcher(this).replaceAll("").trim { it <= ' ' }.toInt()

val String.convertToSnakeCase: String
    get() = replace(Regex("([a-z])([A-Z])"), "$1_$2").lowercase()

fun String.fromHtml(flag: Int? = null): String {
    @Suppress("DEPRECATION")
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        Html.fromHtml(this, flag ?: Html.FROM_HTML_MODE_LEGACY).toString()
    else Html.fromHtml(this).toString()
}