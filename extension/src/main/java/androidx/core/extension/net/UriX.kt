package androidx.core.extension.net

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.extension.content.findPathByUri

fun Uri.isFileExists(context: Context): Boolean {
    return runCatching {
        context.contentResolver.openAssetFileDescriptor(this, "r")?.close()
    }.isSuccess
}

fun Uri.delete(context: Context) {
    runCatching {
        context.contentResolver.delete(this, null, null)
    }
        .onSuccess { Log.i("UriUtils", "delete uri success:$this") }
        .onFailure { Log.e("UriUtils", "delete uri failure:$this") }
}

fun Uri.filePath(context: Context): String {
    return when (scheme) {
        ContentResolver.SCHEME_CONTENT -> context.findPathByUri(this).orEmpty()
        ContentResolver.SCHEME_FILE -> path.orEmpty()
        else -> throw RuntimeException("unsupported uri")
    }
}

fun Uri?.orEmpty(): Uri = this ?: Uri.EMPTY

fun String.getQueryParameter(key: String, defaultValue: String = ""): String =
    Uri.parse(this).getQueryParameter(key) ?: defaultValue

fun String.getBooleanQueryParameter(key: String, defaultValue: Boolean = false): Boolean =
    Uri.parse(this).getBooleanQueryParameter(key, defaultValue)

fun String.getListQueryParameter(vararg key: String): ArrayList<String> {
    val parse = Uri.parse(this)
    val list = ArrayList<String>()
    key.forEach {
        list.add(parse.getQueryParameter(it).orEmpty())
    }
    return list
}