package androidx.core.extension.version

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

fun Int.minVersion(): Boolean = Build.VERSION.SDK_INT >= this

fun Int.maxVersion(): Boolean = Build.VERSION.SDK_INT <= this

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.LOLLIPOP_MR1)
fun hasLMr1(): Boolean = Build.VERSION.SDK_INT >= 22

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
fun hasM(): Boolean = Build.VERSION.SDK_INT >= 23

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
fun hasN(): Boolean = Build.VERSION.SDK_INT >= 24

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N_MR1)
fun hasNMr1(): Boolean = Build.VERSION.SDK_INT >= 25

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
fun hasO(): Boolean = Build.VERSION.SDK_INT >= 26

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O_MR1)
fun hasOMr1(): Boolean = Build.VERSION.SDK_INT >= 27

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
fun hasP(): Boolean = Build.VERSION.SDK_INT >= 28

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
fun hasQ(): Boolean = Build.VERSION.SDK_INT >= 29

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
fun hasR(): Boolean = Build.VERSION.SDK_INT >= 30