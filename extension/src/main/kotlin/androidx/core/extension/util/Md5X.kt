package androidx.core.extension.util

import java.security.MessageDigest
import kotlin.experimental.and

fun String.encodeMD5(): String {
    return runCatching {
        val digest: MessageDigest = MessageDigest.getInstance("MD5")
        digest.update(toByteArray())
        digest.digest().toHexString()
    }.getOrElse { "" }
}

fun ByteArray.toHexString(): String {
    val expectedStringLen: Int = size * 2
    val sb = StringBuilder(expectedStringLen)
    for (i: Int in indices) {
        var hexStr: String = (this[i] and 0x00FF.toByte()).toString(16)
        if (hexStr.length == 1) {
            hexStr = "0$hexStr"
        }
        sb.append(hexStr)
    }
    return sb.toString()
}