package androidx.core.extension.util

import android.annotation.SuppressLint
import android.os.Environment

private const val DEFAULT_SIZE_ZERO = 0
private const val DEFAULT_SIZE_1024 = 1024
private const val DEFAULT_SIZE_1024_SQUARE = DEFAULT_SIZE_1024 * DEFAULT_SIZE_1024
private const val DEFAULT_SIZE_1024_CUBIC = DEFAULT_SIZE_1024_SQUARE * DEFAULT_SIZE_1024

@SuppressLint("UsableSpace")
fun storageSize(): Long {
    return runCatching { Environment.getExternalStorageDirectory().usableSpace }.getOrNull() ?: 0
}

fun Long.formatFileSize(): String {
    return if (this in DEFAULT_SIZE_1024..<DEFAULT_SIZE_1024_SQUARE) String.format(
        "%.2f", this.toDouble() / DEFAULT_SIZE_1024
    ) + "KB"
    else if (this in DEFAULT_SIZE_1024_SQUARE..<DEFAULT_SIZE_1024_CUBIC) String.format(
        "%.2f", this.toDouble() / (DEFAULT_SIZE_1024_SQUARE)
    ) + "MB"
    else if (this >= DEFAULT_SIZE_1024_CUBIC) String.format(
        "%.2f", this.toDouble() / (DEFAULT_SIZE_1024_CUBIC)
    ) + "GB"
    else if (this <= DEFAULT_SIZE_ZERO) ""
    else this.toString() + "B"
}


