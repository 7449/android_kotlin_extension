package androidx.core.extension.database

import android.database.Cursor

val Cursor?.columnNameList: Array<String>
    get() = this?.columnNames ?: emptyArray()

fun Cursor?.move(offset: Int): Boolean =
    this?.move(offset) == true

fun Cursor?.moveToPosition(position: Int): Boolean =
    this?.moveToPosition(position) == true

//getColumnName

fun Cursor?.getColumnNameOrDefault(columnIndex: Int, defaultValue: String): String =
    if (this == null || isNull(columnIndex)) defaultValue else getColumnName(columnIndex)

fun Cursor?.getColumnName(columnIndex: Int, ifNone: () -> String): String =
    if (this == null || isNull(columnIndex)) ifNone.invoke() else getColumnName(columnIndex)

//getBlob

fun Cursor?.getBlobOrDefault(columnIndex: Int, defaultValue: ByteArray): ByteArray =
    if (this == null || isNull(columnIndex)) defaultValue else getBlob(columnIndex)

fun Cursor?.getBlob(columnIndex: Int, ifNone: () -> ByteArray): ByteArray =
    if (this == null || isNull(columnIndex)) ifNone.invoke() else getBlob(columnIndex)

//getString

fun Cursor?.getString(columnIndex: Int, defaultValue: String): String =
    if (this == null || isNull(columnIndex)) defaultValue else getString(columnIndex)

fun Cursor?.getString(columnIndex: Int, ifNone: () -> String): String =
    if (this == null || isNull(columnIndex)) ifNone.invoke() else getString(columnIndex)

//getShort

fun Cursor?.getShort(columnIndex: Int, defaultValue: Short): Short =
    if (this == null || isNull(columnIndex)) defaultValue else getShort(columnIndex)

fun Cursor?.getShort(columnIndex: Int, ifNone: () -> Short): Short =
    if (this == null || isNull(columnIndex)) ifNone.invoke() else getShort(columnIndex)

//getInt

fun Cursor?.getInt(columnIndex: Int, defaultValue: Int): Int =
    if (this == null || isNull(columnIndex)) defaultValue else getInt(columnIndex)

fun Cursor?.getInt(columnIndex: Int, ifNone: () -> Int): Int =
    if (this == null || isNull(columnIndex)) ifNone.invoke() else getInt(columnIndex)

//getLong

fun Cursor?.getLong(columnIndex: Int, defaultValue: Long): Long =
    if (this == null || isNull(columnIndex)) defaultValue else getLong(columnIndex)

fun Cursor?.getLong(columnIndex: Int, ifNone: () -> Long): Long =
    if (this == null || isNull(columnIndex)) ifNone.invoke() else getLong(columnIndex)

//getFloat

fun Cursor?.getFloat(columnIndex: Int, defaultValue: Float): Float =
    if (this == null || isNull(columnIndex)) defaultValue else getFloat(columnIndex)

fun Cursor?.getFloat(columnIndex: Int, ifNone: () -> Float): Float =
    if (this == null || isNull(columnIndex)) ifNone.invoke() else getFloat(columnIndex)

//getDouble

fun Cursor?.getDouble(columnIndex: Int, defaultValue: Double): Double =
    if (this == null || isNull(columnIndex)) defaultValue else getDouble(columnIndex)

fun Cursor?.getDouble(columnIndex: Int, ifNone: () -> Double): Double =
    if (this == null || isNull(columnIndex)) ifNone.invoke() else getDouble(columnIndex)

//getType

fun Cursor?.getType(columnIndex: Int, defaultValue: Int): Int =
    if (this == null || isNull(columnIndex)) defaultValue else getType(columnIndex)

fun Cursor?.getType(columnIndex: Int, ifNone: () -> Int): Int =
    if (this == null || isNull(columnIndex)) ifNone.invoke() else getType(columnIndex)