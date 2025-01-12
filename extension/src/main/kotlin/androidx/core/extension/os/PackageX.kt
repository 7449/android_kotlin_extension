package androidx.core.extension.os

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build

val Context.isApkDebuggable: Boolean
    get() = isApkDebugAble(packageName)

val Context.versionName: String
    get() = appVersionName(packageName)

val Context.versionCode: Long
    get() = appVersionCode(packageName)

fun Context.appVersionName(packageName: String): String {
    return runCatching {
        packageManager.getPackageInfo(
            packageName,
            PackageManager.MATCH_DEFAULT_ONLY
        ).versionName
    }.getOrNull().orEmpty()
}

fun Context.queryAppIcon(packageName: String): Drawable =
    packageManager.getApplicationInfo(packageName, PackageManager.GET_ACTIVITIES)
        .loadIcon(packageManager)

fun Context.queryAppName(packageName: String): String {
    return runCatching {
        packageManager.getApplicationInfo(packageName, PackageManager.GET_ACTIVITIES)
            .loadLabel(packageManager)
            .toString()
    }.getOrElse { "" }
}

fun Context.isApkDebugAble(packageName: String): Boolean {
    return runCatching {
        packageManager.getPackageInfo(
            packageName,
            PackageManager.MATCH_DEFAULT_ONLY
        ).applicationInfo?.flags?.and(ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }.getOrElse { false }
}

@Suppress("DEPRECATION")
fun Context.appVersionCode(packageName: String): Long {
    return runCatching {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageManager.getPackageInfo(
                packageName,
                PackageManager.MATCH_DEFAULT_ONLY
            ).longVersionCode
        } else {
            packageManager.getPackageInfo(
                packageName,
                PackageManager.MATCH_DEFAULT_ONLY
            ).versionCode.toLong()
        }
    }.getOrElse { -1 }
}

val Context.installedPackageInfo: List<PackageInfo>
    get() = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES)

fun Context.isInstalledApp(packageName: String): Boolean =
    installedPackageInfo.find { packageName.equals(it.packageName, true) } != null

fun Context.isSystemApp(packageName: String): Boolean {
    return runCatching {
        packageManager.getApplicationInfo(
            packageName,
            PackageManager.MATCH_DEFAULT_ONLY
        ).flags and ApplicationInfo.FLAG_SYSTEM > 0
    }.getOrElse { false }
}