package androidx.core.extension.content

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.core.extension.database.getLong
import androidx.core.extension.database.getString

fun ContentResolver.query(uri: Uri, vararg name: String): Cursor? =
    query(uri, name, null, null, null)

fun ContentResolver.moveToNextToId(uri: Uri): Boolean =
    moveToNext(uri, MediaStore.Files.FileColumns._ID)

fun ContentResolver.moveToNext(uri: Uri, name: String): Boolean =
    query(uri, name).use { it?.moveToNext() ?: false }

fun ContentResolver.queryData(uri: Uri): String? =
    query(uri, MediaStore.MediaColumns.DATA).use {
        val cursor = it ?: return null
        while (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA), "")
        }
        return null
    }

fun ContentResolver.queryId(uri: Uri): Long {
    val split = uri.toString().split("/")
    var id = -1L
    runCatching {
        id = split[split.size - 1].toLong()
    }.onFailure {
        query(uri, MediaStore.Files.FileColumns._ID).use {
            val cursor = it ?: return@use
            while (cursor.moveToNext()) {
                id = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID), -1)
            }
        }
    }
    return id
}