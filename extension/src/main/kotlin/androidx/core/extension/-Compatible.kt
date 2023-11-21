package androidx.core.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.text.Html
import android.view.WindowManager

internal const val TYPE_SYSTEM_ALERT_COMPATIBLE = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

inline fun <reified T> Bundle.getOrNullCompatible(key: String) =
    get(key) as T?

inline fun <reified T> Bundle.getCompatible(key: String) =
    checkNotNull(getOrNullCompatible<T>(key))

inline fun <reified T> Bundle.getCompatible(key: String, defaultValue: T) =
    getOrNullCompatible<T>(key) ?: defaultValue

inline fun <reified T : Parcelable> Bundle.getSparseParcelableArrayCompatible(key: String) =
    getSparseParcelableArray<T>(key)

inline fun <reified T : Parcelable> Bundle.getParcelableArrayListCompatible(key: String) =
    getParcelableArrayList<T>(key)

fun Bundle.getParcelableArrayCompatible(key: String): Array<Parcelable>? =
    getParcelableArray(key)

fun <T : Parcelable> Bundle.getParcelableCompatible(key: String) =
    getParcelable<T>(key)

fun Bundle.getSerializableCompatible(key: String) =
    getSerializable(key)

fun Intent.getSerializableCompatible(key: String) =
    getSerializableExtra(key)

fun <T : Parcelable> Intent.getParcelableCompatible(key: String) =
    getParcelableExtra<T>(key)

fun Intent.getParcelableArrayCompatible(key: String): Array<Parcelable>? =
    getParcelableArrayExtra(key)

fun <T : Parcelable> Intent.getParcelableArrayListCompatible(key: String) =
    getParcelableArrayListExtra<T>(key)

internal fun Activity.hideStatusBarCompatible() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

internal fun Activity.showStatusBarCompatible() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
}

internal fun Context.appVersionCodeCompatible(): Int {
    return runCatching {
        packageManager.getPackageInfo(
            packageName, PackageManager.MATCH_DEFAULT_ONLY
        ).versionCode
    }.getOrElse { -1 }
}

internal fun Context.installTimeCompatible(): Long {
    return packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).lastUpdateTime
}

internal fun String.fromHtmlCompatible(): String {
    return Html.fromHtml(this).toString()
}