package androidx.core.extension.util

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

const val DEFAULT_KEY = "abcdefgabcdefgab"

private const val AES = "AES"
private val keyIv = "default_AES256VI".toByteArray()
private const val CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding"

fun String.encodeAES(key: String): String {
    return runCatching {
        val secretKey = SecretKeySpec(key.sha().toByteArray(), 0, 32, AES)
        val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(keyIv))
        val byteEncode = toByteArray(StandardCharsets.UTF_8)
        val byteAES = cipher.doFinal(byteEncode)
        Base64.encodeToString(byteAES, Base64.NO_WRAP)
    }.onFailure { }.getOrNull().orEmpty()
}

fun String.decodeAES(key: String): String {
    return runCatching {
        val secretKey = SecretKeySpec(key.sha().toByteArray(), 0, 32, AES)
        val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(keyIv))
        val byteContent = Base64.decode(this, Base64.NO_WRAP)
        val byteDecode = cipher.doFinal(byteContent)
        String(byteDecode, StandardCharsets.UTF_8)
    }.onFailure { }.getOrNull().orEmpty()
}

fun String.sha(): String {
    return runCatching {
        toHex(MessageDigest.getInstance("SHA-256").digest(toByteArray()))
    }.getOrNull().orEmpty()
}

private fun toHex(byteArray: ByteArray): String {
    val result = with(StringBuilder()) {
        byteArray.forEach {
            val hexStr = Integer.toHexString(it.toInt() and (0xFF))
            if (hexStr.length == 1) append("0")
            append(hexStr)
        }
        toString()
    }
    return result
}