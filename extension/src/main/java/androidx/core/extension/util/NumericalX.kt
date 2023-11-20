package androidx.core.extension.util

import java.math.BigDecimal
import java.math.BigInteger

fun String.toByte(defaultValue: Byte = 0): Byte =
    toByteOrNull() ?: defaultValue

fun String.toShort(defaultValue: Short = 0): Short =
    toShortOrNull() ?: defaultValue

fun String.toInt(defaultValue: Int = 0): Int =
    toIntOrNull() ?: defaultValue

fun String.toLong(defaultValue: Long = 0): Long =
    toLongOrNull() ?: defaultValue

fun String.toFloat(defaultValue: Float = 0f): Float =
    toFloatOrNull() ?: defaultValue

fun String.toDouble(defaultValue: Double = 0.toDouble()): Double =
    toDoubleOrNull() ?: defaultValue

fun String.toBigInteger(defaultValue: BigInteger = BigInteger.ZERO): BigInteger =
    toBigIntegerOrNull() ?: defaultValue

fun String.toBigDecimal(defaultValue: BigDecimal = BigDecimal.ZERO): BigDecimal =
    toBigDecimalOrNull() ?: defaultValue

fun String.toByteOrDefault(action: () -> Byte): Byte =
    toByteOrNull() ?: action.invoke()

fun String.toShortOrDefault(action: () -> Short): Short =
    toShortOrNull() ?: action.invoke()

fun String.toIntOrDefault(action: () -> Int): Int =
    toIntOrNull() ?: action.invoke()

fun String.toLongOrDefault(action: () -> Long): Long =
    toLongOrNull() ?: action.invoke()

fun String.toFloatOrDefault(action: () -> Float): Float =
    toFloatOrNull() ?: action.invoke()

fun String.toDoubleOrDefault(action: () -> Double): Double =
    toDoubleOrNull() ?: action.invoke()

fun String.toBigIntegerOrDefault(action: () -> BigInteger): BigInteger =
    toBigIntegerOrNull() ?: action.invoke()

fun String.toBigDecimalOrDefault(action: () -> BigDecimal): BigDecimal =
    toBigDecimalOrNull() ?: action.invoke()