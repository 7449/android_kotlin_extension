package androidx.core.extension.content

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.KeyguardManager
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.hardware.display.DisplayManager
import android.net.ConnectivityManager
import android.net.Uri
import android.net.VpnService
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.PowerManager
import android.provider.MediaStore
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.extension.R
import androidx.core.extension.compatible.installTimeCompatible
import androidx.core.extension.version.hasQ
import androidx.core.os.bundleOf
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.Executor

val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

val Context.dataDirCompat: File?
    get() = ContextCompat.getDataDir(this)

val Context.obbDirsCompat: Array<File>
    get() = ContextCompat.getObbDirs(this)

val Context.externalCacheDirsCompat: Array<File>
    get() = ContextCompat.getExternalCacheDirs(this)

val Context.noBackupFilesDirCompat: File?
    get() = ContextCompat.getNoBackupFilesDir(this)

val Context.codeCacheDirCompat: File
    get() = ContextCompat.getCodeCacheDir(this)

val Context.isDeviceProtectedStorageCompat: Boolean
    get() = ContextCompat.isDeviceProtectedStorage(this)

val Context.mainExecutorCompat: Executor
    get() = ContextCompat.getMainExecutor(this)

val Context.isLandscape: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

val Context.isVpnFirstConnect: Boolean
    get() = VpnService.prepare(this) != null

val Context.installTime: Long
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageManager.getPackageInfo(
            packageName,
            PackageManager.GET_SIGNING_CERTIFICATES
        ).lastUpdateTime
    } else {
        installTimeCompatible()
    }

val Context.installDay: Int
    get() = ((System.currentTimeMillis() - installTime) / (1000 * 60 * 60 * 24)).toInt()

val Context.hasSystemFeature: Boolean
    get() = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)

val Context.statusBarHeight: Int
    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    get() {
        return runCatching {
            val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
            resources.getDimensionPixelSize(resourceId)
        }.getOrNull() ?: 0
    }

fun Context.showToastShortRes(res: Int) {
    showToastShort(getString(res))
}

fun Context.showToastShort(any: Any): Unit =
    Toast.makeText(this, any.toString(), Toast.LENGTH_SHORT).show()

fun Context.showToastLongRes(res: Int) {
    showToastLong(getString(res))
}

fun Context.showToastLong(any: Any): Unit =
    Toast.makeText(this, any.toString(), Toast.LENGTH_LONG).show()

fun Context.getExternalFilesDirsCompat(type: String?): Array<File> =
    ContextCompat.getExternalFilesDirs(this, type)

fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable? =
    ContextCompat.getDrawable(this, id)

fun Context.getColorStateListCompat(@ColorRes id: Int): ColorStateList? =
    ContextCompat.getColorStateList(this, id)

fun Context.getColorCompat(@ColorRes id: Int): Int =
    ContextCompat.getColor(this, id)

fun Context.checkPermissionCompat(permission: String): Int =
    ContextCompat.checkSelfPermission(this, permission)

fun Context.getBoolean(@BoolRes id: Int): Boolean =
    this.resources.getBoolean(id)

fun Context.getInteger(@IntegerRes id: Int): Int =
    this.resources.getInteger(id)

@RequiresApi(Build.VERSION_CODES.Q)
fun Context.getFloat(@DimenRes id: Int): Float =
    this.resources.getFloat(id)

fun Context.getTextArray(@ArrayRes id: Int): Array<CharSequence> =
    this.resources.getTextArray(id)

fun Context.getStringArray(@ArrayRes id: Int): Array<String> =
    this.resources.getStringArray(id)

fun Context.getIntArray(@ArrayRes id: Int): IntArray =
    this.resources.getIntArray(id)

fun Context.getDimension(@DimenRes id: Int): Float =
    this.resources.getDimension(id)

fun Context.getDimensionPixelOffset(@DimenRes id: Int): Int =
    this.resources.getDimensionPixelOffset(id)

fun Context.getDimensionPixelSize(@DimenRes id: Int): Int =
    this.resources.getDimensionPixelSize(id)

fun Context.getQuantityText(@PluralsRes id: Int, quantity: Int): CharSequence =
    this.resources.getQuantityText(id, quantity)

fun Context.getQuantityString(
    @PluralsRes id: Int,
    quantity: Int,
    vararg formatArgs: Any,
): CharSequence = this.resources.getQuantityString(id, quantity, *formatArgs)

fun Context.getQuantityString(@PluralsRes id: Int, quantity: Int): String =
    this.resources.getQuantityString(id, quantity)

@RequiresApi(Build.VERSION_CODES.O)
fun Context.getFont(@FontRes id: Int): Typeface =
    this.resources.getFont(id)

fun Context.getLaunchIntentForPackage(packageName: String): Intent? =
    packageManager.getLaunchIntentForPackage(packageName)

fun Context.moveToNextToId(uri: Uri): Boolean =
    contentResolver.moveToNextToId(uri)

fun Context.moveToNext(uri: Uri, name: String): Boolean =
    contentResolver.moveToNext(uri, name)

fun Context.findIdByUri(uri: Uri): Long =
    contentResolver.queryId(uri)

fun Context.findPathByUri(uri: Uri): String? =
    contentResolver.queryData(uri)

fun Context.enableComponent(componentName: ComponentName) {
    packageManager.setComponentEnabledSetting(
        componentName,
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP
    )
}

fun Context.disableComponent(componentName: ComponentName) {
    packageManager.setComponentEnabledSetting(
        componentName,
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        PackageManager.DONT_KILL_APP
    )
}

fun Context.pendingIntent(clazz: Class<*>, requestCode: Int = 1): PendingIntent? {
    val intent = Intent(this, clazz)
    val flags = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        else -> PendingIntent.FLAG_UPDATE_CURRENT
    }
    return PendingIntent.getBroadcast(this, requestCode, intent, flags)
}

fun Context.getAssetsString(fileName: String): String {
    val stringBuilder = StringBuilder()
    BufferedReader(InputStreamReader(assets.open(fileName))).use { it ->
        it.lineSequence().forEach { stringBuilder.append(it) }
    }
    return stringBuilder.toString()
}

fun Context.insertImageUri(contentValues: ContentValues): Uri? =
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    } else {
        null
    }

fun Context.insertImageUri(
    file: File,
    relativePath: String = Environment.DIRECTORY_DCIM,
): Uri? = insertImageUri(ContentValues().apply {
    if (hasQ()) {
        put(MediaStore.MediaColumns.DISPLAY_NAME, file.name)
        put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
    } else {
        put(MediaStore.MediaColumns.DATA, file.path)
    }
})

fun Context.insertVideoUri(contentValues: ContentValues): Uri? =
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)
    } else {
        null
    }

fun Context.insertVideoUri(
    file: File,
    relativePath: String = Environment.DIRECTORY_DCIM,
): Uri? = insertVideoUri(ContentValues().apply {
    if (hasQ()) {
        put(MediaStore.MediaColumns.DISPLAY_NAME, file.name)
        put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
    } else {
        put(MediaStore.MediaColumns.DATA, file.path)
    }
})

fun Context.shareTextPlain(title: String, message: String?) {
    val textIntent = Intent(Intent.ACTION_SEND)
    textIntent.type = "text/plain"
    textIntent.putExtra(Intent.EXTRA_TEXT, message)
    startActivity(Intent.createChooser(textIntent, title))
}

fun Context.copyThenPost(textCopied: String) {
    val appName = packageManager.getApplicationLabel(applicationInfo).toString()
    val clipboardManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getSystemService(ClipboardManager::class.java)
    } else {
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }
    clipboardManager.setPrimaryClip(ClipData.newPlainText(appName, textCopied))
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
        Toast.makeText(this, getString(R.string.extension_str_copy), Toast.LENGTH_SHORT).show()
}

fun Context.openVideo(uri: Uri, error: () -> Unit) {
    runCatching {
        val video = Intent(Intent.ACTION_VIEW)
        video.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        video.setDataAndType(uri, "video/*")
        startActivity(video)
    }.onFailure { error.invoke() }
}

fun Context.openUri(uri: String) {
    openUri(Uri.parse(uri))
}

fun Context.openUri(uri: Uri) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }.onFailure {
        Toast.makeText(this, getString(R.string.extension_str_not_find_app), Toast.LENGTH_LONG)
            .show()
    }
}

fun Context.openAppStore(packageName: String, error: () -> Unit) {
    runCatching {
        val market = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        market.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(market)
    }.onFailure { error.invoke() }
}

fun Context.isInstallApp(packageName: String): Boolean {
    return runCatching { applicationInfo(packageName)?.enabled }.getOrNull() ?: false
}

fun Context.getDimensionValue(vararg attrs: Int): Int {
    val typedArray = obtainStyledAttributes(attrs)
    val value = typedArray.getDimensionPixelSize(0, -1)
    typedArray.recycle()
    return value
}

fun Context.getColorValue(vararg attrs: Int): Int {
    val typedArray = obtainStyledAttributes(attrs)
    val value = typedArray.getColor(0, -1)
    typedArray.recycle()
    return value
}

fun Context.packageInfo() =
    runCatching {
        packageManager.getPackageInfo(packageName, PackageManager.MATCH_DEFAULT_ONLY)
    }.getOrNull()

fun Context.applicationInfo(pkg: String) =
    runCatching {
        packageManager.getApplicationInfo(pkg, PackageManager.MATCH_DEFAULT_ONLY)
    }.getOrNull()

fun Context.activityInfo(name: ComponentName) =
    runCatching {
        packageManager.getActivityInfo(name, PackageManager.MATCH_DEFAULT_ONLY)
    }.getOrNull()

fun Context.netManager() =
    service<ConnectivityManager>(Context.CONNECTIVITY_SERVICE)

fun Context.keyguardManager() =
    service<KeyguardManager>(Context.KEYGUARD_SERVICE)

fun Context.powerManager() =
    service<PowerManager>(Context.POWER_SERVICE)

fun Context.phoneManager() =
    service<TelephonyManager>(Context.TELEPHONY_SERVICE)

fun Context.activityManager() =
    service<ActivityManager>(Context.ACTIVITY_SERVICE)

fun Context.displayManager() =
    service<DisplayManager>(Context.DISPLAY_SERVICE)

inline fun <reified T> Context.service(name: String): T {
    val service = getSystemService(name)
    if (service is T) {
        return service
    } else {
        throw NullPointerException(name)
    }
}

fun Context.act(): Activity {
    return findActivity() as Activity
}

fun Context?.findActivity(): Activity? {
    if (this == null) return null
    if (this is Activity) return this
    if (this is ContextWrapper) return baseContext.findActivity()
    return null
}

inline fun <reified T : Activity> Context.startActivity(bundle: Bundle = bundleOf()) {
    startActivity(Intent(this, T::class.java).apply {
        putExtras(bundle)
        if (this@startActivity !is Activity) {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    })
}