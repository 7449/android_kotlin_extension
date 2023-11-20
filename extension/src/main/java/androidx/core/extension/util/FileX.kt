package androidx.core.extension.util

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.extension.content.insertImageUri
import androidx.core.extension.content.insertVideoUri
import java.io.File
import java.io.InputStream
import java.io.OutputStream

fun String.mkdirs(child: String): File {
    val pathFile = File(this, child)
    if (!pathFile.exists()) {
        pathFile.mkdirs()
    }
    return pathFile
}

fun File.createParent(): File {
    val parentFile = parentFile
    if (parentFile != null && !parentFile.exists()) {
        parentFile.mkdirs()
    }
    return this
}

fun Context.lowerVersionFile(
    fileName: String,
    relativePath: String = Environment.DIRECTORY_DCIM,
): File = File(
    if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
        Environment.getExternalStoragePublicDirectory(relativePath).path
    } else {
        externalCacheDir?.path ?: cacheDir.path
    }, fileName
)

@RequiresApi(api = Build.VERSION_CODES.Q)
fun Context.copyImage(
    inputUri: Uri,
    displayName: String,
    relativePath: String = Environment.DIRECTORY_DCIM,
): Uri? {
    val contentValues = ContentValues()
    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
    val outPutUri = insertImageUri(contentValues) ?: return null
    return copyFile(inputUri, outPutUri)
}

@RequiresApi(api = Build.VERSION_CODES.Q)
fun Context.copyVideo(
    inputUri: Uri,
    displayName: String,
    relativePath: String = Environment.DIRECTORY_DCIM,
): Uri? {
    val contentValues = ContentValues()
    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
    val outPutUri = insertVideoUri(contentValues) ?: return null
    return copyFile(inputUri, outPutUri)
}

fun Context.copyFile(inputUri: Uri, outPutUri: Uri): Uri? {
    val outStream: OutputStream = contentResolver.openOutputStream(outPutUri) ?: return null
    val inStream: InputStream = contentResolver.openInputStream(inputUri) ?: return null
    outStream.use { out -> inStream.use { input -> input.copyTo(out) } }
    return outPutUri
}