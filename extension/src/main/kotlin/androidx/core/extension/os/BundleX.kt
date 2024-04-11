package androidx.core.extension.os

import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import androidx.annotation.RequiresApi
import androidx.core.extension.getParcelableArrayCompatible
import androidx.core.extension.getParcelableArrayListCompatible
import androidx.core.extension.getParcelableCompatible
import androidx.core.extension.getSerializableCompatible
import androidx.core.extension.getSparseParcelableArrayCompatible
import java.io.Serializable

fun Bundle?.orEmpty(): Bundle = this ?: Bundle.EMPTY

//getSerializable

inline fun <reified T : Serializable> Bundle.serializable(
    key: String,
): T = getSerializable<T>(key) { throw NullPointerException() }

inline fun <reified T : Serializable> Bundle.getSerializable(
    key: String, defaultValue: T,
): T = getSerializable<T>(key) { defaultValue }

inline fun <reified T : Serializable> Bundle.getSerializable(
    key: String, ifNone: () -> T,
): T = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getSerializable(key, T::class.java)
} else {
    getSerializableCompatible(key) as T?
} ?: ifNone.invoke()

//getParcelable

inline fun <reified T : Parcelable> Bundle.parcelable(
    key: String,
): T = getParcelable<T>(key) { throw NullPointerException() }

inline fun <reified T : Parcelable> Bundle.getParcelable(
    key: String, defaultValue: T,
): T = getParcelable<T>(key) { defaultValue }

inline fun <reified T : Parcelable> Bundle.getParcelable(
    key: String, ifNone: () -> T,
): T = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getParcelable(key, T::class.java)
} else {
    getParcelableCompatible(key)
} ?: ifNone.invoke()

//getParcelableArray

inline fun <reified T : Parcelable> Bundle.parcelableArray(
    key: String,
): Array<T> = getParcelableArray(key) { throw NullPointerException() }

inline fun <reified T : Parcelable> Bundle.getParcelableArray(
    key: String, defaultValue: Array<T>,
): Array<T> = getParcelableArray(key) { defaultValue }

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Parcelable> Bundle.getParcelableArray(
    key: String, ifNone: () -> Array<T>,
): Array<T> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getParcelableArray(key, T::class.java)
} else {
    getParcelableArrayCompatible(key) as Array<T>?
} ?: ifNone.invoke()

//getParcelableArrayList

inline fun <reified T : Parcelable> Bundle.parcelableArrayList(
    key: String,
): ArrayList<T> = getParcelableArrayList(key) { throw NullPointerException() }

inline fun <reified T : Parcelable> Bundle.getParcelableArrayList(
    key: String, defaultValue: ArrayList<T>,
): ArrayList<T> = getParcelableArrayList(key) { defaultValue }

inline fun <reified T : Parcelable> Bundle.getParcelableArrayList(
    key: String, ifNone: () -> ArrayList<T>,
): ArrayList<T> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getParcelableArrayList(key, T::class.java)
} else {
    getParcelableArrayListCompatible(key)
} ?: ifNone.invoke()

//getSparseParcelableArray

inline fun <reified T : Parcelable> Bundle.sparseParcelableArray(
    key: String,
): SparseArray<T> = getSparseParcelableArray(key) { throw NullPointerException() }

inline fun <reified T : Parcelable> Bundle.getSparseParcelableArray(
    key: String, defaultValue: SparseArray<T>,
): SparseArray<T> = getSparseParcelableArray(key) { defaultValue }

inline fun <reified T : Parcelable> Bundle.getSparseParcelableArray(
    key: String, ifNone: () -> SparseArray<T>,
): SparseArray<T> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getSparseParcelableArray(key, T::class.java)
} else {
    getSparseParcelableArrayCompatible(key)
} ?: ifNone.invoke()

//getBinder

inline fun <reified T : IBinder> Bundle.binder(
    key: String,
): T = getBinder(key) as T

inline fun <reified T : IBinder> Bundle.getBinder(
    key: String, defaultValue: T,
): T = getBinder(key) as T? ?: defaultValue

inline fun <reified T : IBinder> Bundle.getBinder(
    key: String, ifNone: () -> T,
): T = getBinder(key) as T? ?: ifNone.invoke()

//getSize

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Bundle.size(
    key: String,
): Size = checkNotNull(getSize(key))

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Bundle.getSize(
    key: String, defaultValue: Size,
): Size = getSize(key) ?: defaultValue

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Bundle.getSize(
    key: String, ifNone: () -> Size,
): Size = getSize(key) ?: ifNone.invoke()

//getSizeF

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Bundle.sizeF(
    key: String,
): SizeF = checkNotNull(getSizeF(key))

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Bundle.getSizeF(
    key: String, defaultValue: SizeF,
): SizeF = getSizeF(key) ?: defaultValue

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Bundle.getSizeF(
    key: String, ifNone: () -> SizeF,
): SizeF = getSizeF(key) ?: ifNone.invoke()

//getBundle

fun Bundle.bundle(
    key: String,
): Bundle = checkNotNull(getBundle(key))

fun Bundle.getBundle(
    key: String, defaultValue: Bundle,
): Bundle = getBundle(key) ?: defaultValue

fun Bundle.getBundle(
    key: String, ifNone: () -> Bundle,
): Bundle = getBundle(key) ?: ifNone.invoke()

//getBoolean

fun Bundle.getBoolean(
    key: String, ifNone: () -> Boolean,
): Boolean = getBoolean(key, ifNone.invoke())

//getByte

fun Bundle.getByte(
    key: String, ifNone: () -> Byte,
): Byte = getByte(key, ifNone.invoke())

//getChar

fun Bundle.getChar(
    key: String, ifNone: () -> Char,
): Char = getChar(key, ifNone.invoke())

//getShort

fun Bundle.getShort(
    key: String, ifNone: () -> Short,
): Short = getShort(key, ifNone.invoke())

//getInt

fun Bundle.getInt(
    key: String, ifNone: () -> Int,
): Int = getInt(key, ifNone.invoke())

//getLong

fun Bundle.getLong(
    key: String, ifNone: () -> Long,
): Long = getLong(key, ifNone.invoke())

//getFloat

fun Bundle.getFloat(
    key: String, ifNone: () -> Float,
): Float = getFloat(key, ifNone.invoke())

//getDouble

fun Bundle.getDouble(
    key: String, ifNone: () -> Double,
): Double = getDouble(key, ifNone.invoke())

//getString

fun Bundle.string(
    key: String,
): String = checkNotNull(getString(key))

fun Bundle.getString(
    key: String, ifNone: () -> String,
): String = getString(key) ?: ifNone.invoke()

//getCharSequence

fun Bundle.charSequence(
    key: String,
): CharSequence = checkNotNull(getCharSequence(key))

fun Bundle.getCharSequence(
    key: String, ifNone: () -> CharSequence,
): CharSequence = getCharSequence(key) ?: ifNone.invoke()

//getIntegerArrayList

fun Bundle.integerArrayList(
    key: String,
): ArrayList<Int> = checkNotNull(getIntegerArrayList(key))

fun Bundle.getIntegerArrayList(
    key: String, defaultValue: ArrayList<Int>,
): ArrayList<Int> = getIntegerArrayList(key) ?: defaultValue

fun Bundle.getIntegerArrayList(
    key: String, ifNone: () -> ArrayList<Int>,
): ArrayList<Int> = getIntegerArrayList(key) ?: ifNone.invoke()

//getStringArrayList

fun Bundle.stringArrayList(
    key: String,
): ArrayList<String> = checkNotNull(getStringArrayList(key))

fun Bundle.getStringArrayList(
    key: String, defaultValue: ArrayList<String>,
): ArrayList<String> = getStringArrayList(key) ?: defaultValue

fun Bundle.getStringArrayList(
    key: String, ifNone: () -> ArrayList<String>,
): ArrayList<String> = getStringArrayList(key) ?: ifNone.invoke()

//getCharSequenceArrayList

fun Bundle.charSequenceArrayList(
    key: String,
): ArrayList<CharSequence> = checkNotNull(getCharSequenceArrayList(key))

fun Bundle.getCharSequenceArrayList(
    key: String, defaultValue: ArrayList<CharSequence>,
): ArrayList<CharSequence> = getCharSequenceArrayList(key) ?: defaultValue

fun Bundle.getCharSequenceArrayList(
    key: String, ifNone: () -> ArrayList<CharSequence>,
): ArrayList<CharSequence> = getCharSequenceArrayList(key) ?: ifNone.invoke()

//getBooleanArray

fun Bundle.booleanArray(
    key: String,
): BooleanArray = checkNotNull(getBooleanArray(key))

fun Bundle.getBooleanArray(
    key: String, defaultValue: BooleanArray,
): BooleanArray = getBooleanArray(key) ?: defaultValue

fun Bundle.getBooleanArray(
    key: String, ifNone: () -> BooleanArray,
): BooleanArray = getBooleanArray(key) ?: ifNone.invoke()

//getByteArray

fun Bundle.byteArray(
    key: String,
): ByteArray = checkNotNull(getByteArray(key))

fun Bundle.getByteArray(
    key: String, defaultValue: ByteArray,
): ByteArray = getByteArray(key) ?: defaultValue

fun Bundle.getByteArray(
    key: String, ifNone: () -> ByteArray,
): ByteArray = getByteArray(key) ?: ifNone.invoke()

//getShortArray

fun Bundle.shortArray(
    key: String,
): ShortArray = checkNotNull(getShortArray(key))

fun Bundle.getShortArray(
    key: String, defaultValue: ShortArray,
): ShortArray = getShortArray(key) ?: defaultValue

fun Bundle.getShortArray(
    key: String, ifNone: () -> ShortArray,
): ShortArray = getShortArray(key) ?: ifNone.invoke()

//getCharArray

fun Bundle.charArray(
    key: String,
): CharArray = checkNotNull(getCharArray(key))

fun Bundle.getCharArray(
    key: String, defaultValue: CharArray,
): CharArray = getCharArray(key) ?: defaultValue

fun Bundle.getCharArray(
    key: String, ifNone: () -> CharArray,
): CharArray = getCharArray(key) ?: ifNone.invoke()

//getIntArray

fun Bundle.intArray(
    key: String,
): IntArray = checkNotNull(getIntArray(key))

fun Bundle.getIntArray(
    key: String, defaultValue: IntArray,
): IntArray = getIntArray(key) ?: defaultValue

fun Bundle.getIntArray(
    key: String, ifNone: () -> IntArray,
): IntArray = getIntArray(key) ?: ifNone.invoke()

//getLongArray

fun Bundle.longArray(
    key: String,
): LongArray = checkNotNull(getLongArray(key))

fun Bundle.getLongArray(
    key: String, defaultValue: LongArray,
): LongArray = getLongArray(key) ?: defaultValue

fun Bundle.getLongArray(
    key: String, ifNone: () -> LongArray,
): LongArray = getLongArray(key) ?: ifNone.invoke()

//getFloatArray

fun Bundle.floatArray(
    key: String,
): FloatArray = checkNotNull(getFloatArray(key))

fun Bundle.getFloatArray(
    key: String, defaultValue: FloatArray,
): FloatArray = getFloatArray(key) ?: defaultValue

fun Bundle.getFloatArray(
    key: String, ifNone: () -> FloatArray,
): FloatArray = getFloatArray(key) ?: ifNone.invoke()

//getDoubleArray

fun Bundle.doubleArray(
    key: String,
): DoubleArray = checkNotNull(getDoubleArray(key))

fun Bundle.getDoubleArray(
    key: String, defaultValue: DoubleArray,
): DoubleArray = getDoubleArray(key) ?: defaultValue

fun Bundle.getDoubleArray(
    key: String, ifNone: () -> DoubleArray,
): DoubleArray = getDoubleArray(key) ?: ifNone.invoke()

//getStringArray

fun Bundle.stringArray(
    key: String,
): Array<String> = checkNotNull(getStringArray(key))

fun Bundle.getStringArray(
    key: String, defaultValue: Array<String>,
): Array<String> = getStringArray(key) ?: defaultValue

fun Bundle.getStringArray(
    key: String, ifNone: () -> Array<String>,
): Array<String> = getStringArray(key) ?: ifNone.invoke()

//getCharSequenceArray

fun Bundle.charSequenceArray(
    key: String,
): Array<CharSequence> = checkNotNull(getCharSequenceArray(key))

fun Bundle.getCharSequenceArray(
    key: String, defaultValue: Array<CharSequence>,
): Array<CharSequence> = getCharSequenceArray(key) ?: defaultValue

fun Bundle.getCharSequenceArray(
    key: String,
    ifNone: () -> Array<CharSequence>,
): Array<CharSequence> = getCharSequenceArray(key) ?: ifNone.invoke()