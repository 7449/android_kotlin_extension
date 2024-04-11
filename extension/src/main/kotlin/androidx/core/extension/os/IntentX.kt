package androidx.core.extension.os

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.core.extension.getParcelableArrayCompatible
import androidx.core.extension.getParcelableArrayListCompatible
import androidx.core.extension.getParcelableCompatible
import androidx.core.extension.getSerializableCompatible
import java.io.Serializable

fun Intent?.orEmpty(): Intent = this ?: Intent()

//getParcelableArrayExtra

inline fun <reified T : Parcelable> Intent.getParcelableArray(
    key: String,
): Array<T> = getParcelableArray(key) { throw NullPointerException() }

inline fun <reified T : Parcelable> Intent.getParcelableArray(
    key: String, defaultValue: Array<T>,
): Array<T> = getParcelableArray(key) { defaultValue }

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Parcelable> Intent.getParcelableArray(
    key: String, ifNone: () -> Array<T>,
): Array<T> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getParcelableArrayExtra(key, T::class.java)
} else {
    getParcelableArrayCompatible(key) as Array<T>?
} ?: ifNone.invoke()

//getParcelableArrayListExtra

inline fun <reified T : Parcelable> Intent.getParcelableArrayList(
    key: String,
): ArrayList<T> = getParcelableArrayList(key) { throw NullPointerException() }

inline fun <reified T : Parcelable> Intent.getParcelableArrayList(
    key: String, defaultValue: ArrayList<T>,
): ArrayList<T> = getParcelableArrayList(key) { defaultValue }

inline fun <reified T : Parcelable> Intent.getParcelableArrayList(
    key: String, ifNone: () -> ArrayList<T>,
): ArrayList<T> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getParcelableArrayListExtra(key, T::class.java)
} else {
    getParcelableArrayListCompatible(key)
} ?: ifNone.invoke()

//getParcelableExtra

inline fun <reified T : Parcelable> Intent.getParcelable(
    key: String,
): T = getParcelable<T>(key) { throw NullPointerException() }

inline fun <reified T : Parcelable> Intent.getParcelable(
    key: String, defaultValue: T,
): T = getParcelable<T>(key) { defaultValue }

inline fun <reified T : Parcelable> Intent.getParcelable(
    key: String, ifNone: () -> T,
): T = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getParcelableExtra(key, T::class.java)
} else {
    getParcelableCompatible(key)
} ?: ifNone.invoke()

//getSerializableExtra

inline fun <reified T : Serializable> Intent.getSerializable(
    key: String,
): T = getSerializable<T>(key) { throw NullPointerException() }

inline fun <reified T : Serializable> Intent.getSerializable(
    key: String, defaultValue: T,
): T = getSerializable<T>(key) { defaultValue }

inline fun <reified T : Serializable> Intent.getSerializable(
    key: String, ifNone: () -> T,
): T = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getSerializableExtra(key, T::class.java)
} else {
    getSerializableCompatible(key) as T?
} ?: ifNone.invoke()

//getBooleanExtra

fun Intent.getBoolean(
    key: String,
): Boolean = getBoolean(key) { throw NullPointerException() }

fun Intent.getBoolean(
    key: String, ifNone: () -> Boolean,
): Boolean = getBooleanExtra(key, ifNone.invoke())

//getByteExtra

fun Intent.getByte(
    key: String,
): Byte = getByte(key) { throw NullPointerException() }

fun Intent.getByte(
    key: String, ifNone: () -> Byte,
): Byte = getByteExtra(key, ifNone.invoke())

//getShortExtra

fun Intent.getShort(
    key: String,
): Short = getShort(key) { throw NullPointerException() }

fun Intent.getShort(
    key: String, ifNone: () -> Short,
): Short = getShortExtra(key, ifNone.invoke())

//getCharExtra

fun Intent.getChar(
    key: String,
): Char = getChar(key) { throw NullPointerException() }

fun Intent.getChar(
    key: String, ifNone: () -> Char,
): Char = getCharExtra(key, ifNone.invoke())

//getIntExtra

fun Intent.getInt(
    key: String,
): Int = getInt(key) { throw NullPointerException() }

fun Intent.getInt(
    key: String, ifNone: () -> Int,
): Int = getIntExtra(key, ifNone.invoke())

//getLongExtra

fun Intent.getLong(
    key: String,
): Long = getLong(key) { throw NullPointerException() }

fun Intent.getLong(
    key: String, ifNone: () -> Long,
): Long = getLongExtra(key, ifNone.invoke())

//getFloatExtra

fun Intent.getFloat(
    key: String,
): Float = getFloat(key) { throw NullPointerException() }

fun Intent.getFloat(
    key: String, ifNone: () -> Float,
): Float = getFloatExtra(key, ifNone.invoke())

//getDoubleExtra

fun Intent.getDouble(
    key: String,
): Double = getDouble(key) { throw NullPointerException() }

fun Intent.getDouble(
    key: String, ifNone: () -> Double,
): Double = getDoubleExtra(key, ifNone.invoke())

//getStringExtra

fun Intent.getString(
    key: String,
): String = checkNotNull(getStringExtra(key))

fun Intent.getString(
    key: String, defaultValue: String,
): String = getStringExtra(key) ?: defaultValue

fun Intent.getString(
    key: String, ifNone: () -> String,
): String = getStringExtra(key) ?: ifNone.invoke()

//getCharSequenceExtra

fun Intent.getCharSequence(
    key: String,
): CharSequence = checkNotNull(getCharSequenceExtra(key))

fun Intent.getCharSequence(
    key: String, defaultValue: CharSequence,
): CharSequence = getCharSequenceExtra(key) ?: defaultValue

fun Intent.getCharSequence(
    key: String, ifNone: () -> CharSequence,
): CharSequence = getCharSequenceExtra(key) ?: ifNone.invoke()

//getIntegerArrayListExtra

fun Intent.getIntArrayList(
    key: String,
): ArrayList<Int> = checkNotNull(getIntegerArrayListExtra(key))

fun Intent.getIntArrayList(
    key: String, defaultValue: ArrayList<Int>,
): ArrayList<Int> = getIntegerArrayListExtra(key) ?: defaultValue

fun Intent.getIntArrayList(
    key: String, ifNone: () -> ArrayList<Int>,
): ArrayList<Int> = getIntegerArrayListExtra(key) ?: ifNone.invoke()

//getStringArrayListExtra

fun Intent.getStringArrayList(
    key: String,
): ArrayList<String> = checkNotNull(getStringArrayListExtra(key))

fun Intent.getStringArrayList(
    key: String, defaultValue: ArrayList<String>,
): ArrayList<String> = getStringArrayListExtra(key) ?: defaultValue

fun Intent.getStringArrayList(
    key: String, ifNone: () -> ArrayList<String>,
): ArrayList<String> = getStringArrayListExtra(key) ?: ifNone.invoke()

//getCharSequenceArrayListExtra

fun Intent.getCharSequenceArrayList(
    key: String,
): ArrayList<CharSequence> = checkNotNull(getCharSequenceArrayListExtra(key))

fun Intent.getCharSequenceArrayList(
    key: String, defaultValue: ArrayList<CharSequence>,
): ArrayList<CharSequence> = getCharSequenceArrayListExtra(key) ?: defaultValue

fun Intent.getCharSequenceArrayList(
    key: String, ifNone: () -> ArrayList<CharSequence>,
): ArrayList<CharSequence> = getCharSequenceArrayListExtra(key) ?: ifNone.invoke()

//getBooleanArrayExtra

fun Intent.getBooleanArray(
    key: String,
): BooleanArray = checkNotNull(getBooleanArrayExtra(key))

fun Intent.getBooleanArray(
    key: String, defaultValue: BooleanArray,
): BooleanArray = getBooleanArrayExtra(key) ?: defaultValue

fun Intent.getBooleanArray(
    key: String, ifNone: () -> BooleanArray,
): BooleanArray = getBooleanArrayExtra(key) ?: ifNone.invoke()

//getByteArrayExtra

fun Intent.getByteArray(
    key: String,
): ByteArray = checkNotNull(getByteArrayExtra(key))

fun Intent.getByteArray(
    key: String, defaultValue: ByteArray,
): ByteArray = getByteArrayExtra(key) ?: defaultValue

fun Intent.getByteArray(
    key: String, ifNone: () -> ByteArray,
): ByteArray = getByteArrayExtra(key) ?: ifNone.invoke()

//getShortArrayExtra

fun Intent.getShortArray(
    key: String,
): ShortArray = checkNotNull(getShortArrayExtra(key))

fun Intent.getShortArray(
    key: String, defaultValue: ShortArray,
): ShortArray = getShortArrayExtra(key) ?: defaultValue

fun Intent.getShortArray(
    key: String, ifNone: () -> ShortArray,
): ShortArray = getShortArrayExtra(key) ?: ifNone.invoke()

//getCharArrayExtra

fun Intent.getCharArray(
    key: String,
): CharArray = checkNotNull(getCharArrayExtra(key))

fun Intent.getCharArray(
    key: String, defaultValue: CharArray,
): CharArray = getCharArrayExtra(key) ?: defaultValue

fun Intent.getCharArray(
    key: String, ifNone: () -> CharArray,
): CharArray = getCharArrayExtra(key) ?: ifNone.invoke()

//getIntArrayExtra

fun Intent.getIntArray(
    key: String,
): IntArray = checkNotNull(getIntArrayExtra(key))

fun Intent.getIntArray(
    key: String, defaultValue: IntArray,
): IntArray = getIntArrayExtra(key) ?: defaultValue

fun Intent.getIntArray(
    key: String, ifNone: () -> IntArray,
): IntArray = getIntArrayExtra(key) ?: ifNone.invoke()

//getLongArrayExtra

fun Intent.getLongArray(
    key: String,
): LongArray = checkNotNull(getLongArrayExtra(key))

fun Intent.getLongArray(
    key: String, defaultValue: LongArray,
): LongArray = getLongArrayExtra(key) ?: defaultValue

fun Intent.getLongArray(
    key: String, ifNone: () -> LongArray,
): LongArray = getLongArrayExtra(key) ?: ifNone.invoke()

//getFloatArrayExtra

fun Intent.getFloatArray(
    key: String,
): FloatArray = checkNotNull(getFloatArrayExtra(key))

fun Intent.getFloatArray(
    key: String, defaultValue: FloatArray,
): FloatArray = getFloatArrayExtra(key) ?: defaultValue

fun Intent.getFloatArray(
    key: String, ifNone: () -> FloatArray,
): FloatArray = getFloatArrayExtra(key) ?: ifNone.invoke()

//getDoubleArrayExtra

fun Intent.getDoubleArray(
    key: String,
): DoubleArray = checkNotNull(getDoubleArrayExtra(key))

fun Intent.getDoubleArray(
    key: String, defaultValue: DoubleArray,
): DoubleArray = getDoubleArrayExtra(key) ?: defaultValue

fun Intent.getDoubleArray(
    key: String, ifNone: () -> DoubleArray,
): DoubleArray = getDoubleArrayExtra(key) ?: ifNone.invoke()

//getStringArrayExtra

fun Intent.getStringArray(
    key: String,
): Array<String> = checkNotNull(getStringArrayExtra(key))

fun Intent.getStringArray(
    key: String, defaultValue: Array<String>,
): Array<String> = getStringArrayExtra(key) ?: defaultValue

fun Intent.getStringArray(
    key: String, ifNone: () -> Array<String>,
): Array<String> = getStringArrayExtra(key) ?: ifNone.invoke()

//getCharSequenceArrayExtra

fun Intent.getCharSequenceArray(
    key: String,
): Array<CharSequence> = checkNotNull(getCharSequenceArrayExtra(key))

fun Intent.getCharSequenceArray(
    key: String, defaultValue: Array<CharSequence>,
): Array<CharSequence> = getCharSequenceArrayExtra(key) ?: defaultValue

fun Intent.getCharSequenceArray(
    key: String, ifNone: () -> Array<CharSequence>,
): Array<CharSequence> = getCharSequenceArrayExtra(key) ?: ifNone.invoke()

//getBundleExtra

fun Intent.getBundle(
    key: String,
): Bundle = checkNotNull(getBundleExtra(key))

fun Intent.getBundle(
    key: String, defaultValue: Bundle,
): Bundle = getBundleExtra(key) ?: defaultValue

fun Intent.getBundle(
    key: String, ifNone: () -> Bundle,
): Bundle = getBundleExtra(key) ?: ifNone.invoke()