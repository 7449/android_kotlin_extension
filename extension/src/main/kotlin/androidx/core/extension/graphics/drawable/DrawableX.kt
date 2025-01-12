package androidx.core.extension.graphics.drawable

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

val Drawable.canApplyThemeCompat: Boolean
    get() = DrawableCompat.canApplyTheme(this)

val Drawable.colorFilterCompat: ColorFilter?
    get() = DrawableCompat.getColorFilter(this)

val Drawable.layoutDirectionCompat: Int
    get() = DrawableCompat.getLayoutDirection(this)

fun Drawable.setHotspotCompat(x: Float, y: Float) =
    DrawableCompat.setHotspot(this, x, y)

fun Drawable.setHotspotBoundsCompat(left: Int, top: Int, right: Int, bottom: Int) =
    DrawableCompat.setHotspotBounds(this, left, top, right, bottom)

fun Drawable.setTintCompat(@ColorInt tint: Int) =
    DrawableCompat.setTint(this, tint)

fun Drawable.setTintListCompat(tint: ColorStateList?) =
    DrawableCompat.setTintList(this, tint)

fun Drawable.setTintModeCompat(tint: PorterDuff.Mode) =
    DrawableCompat.setTintMode(this, tint)

fun Drawable.applyThemeCompat(theme: Resources.Theme) =
    DrawableCompat.applyTheme(this, theme)

fun Drawable.cleanColorFilterCompat() =
    DrawableCompat.clearColorFilter(this)

fun Drawable.wrapCompat(): Drawable =
    DrawableCompat.wrap(this)

fun <T : Drawable> Drawable.unwrapCompat(): T =
    DrawableCompat.unwrap(this)

fun Drawable.setLayoutDirectionCompat(layoutDirection: Int) =
    DrawableCompat.setLayoutDirection(this, layoutDirection)

fun Drawable.minimumWidthAndHeightDrawable(@ColorInt color: Int): Drawable {
    this.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    this.setBounds(0, 0, this.minimumWidth, this.minimumHeight)
    return this
}

fun GradientDrawable.cornerRadius(radius: Float) = also { cornerRadius = radius }

fun GradientDrawable.color(color: Int) = also { setColor(color) }

fun StateListDrawable.addEnabledState(radius: Float, color: Int) = also {
    addState(
        intArrayOf(android.R.attr.state_enabled),
        GradientDrawable().cornerRadius(radius).color(color)
    )
}

fun StateListDrawable.addNormalState(radius: Float, color: Int) = also {
    addState(
        intArrayOf(-android.R.attr.state_enabled),
        GradientDrawable().cornerRadius(radius).color(color)
    )
}
