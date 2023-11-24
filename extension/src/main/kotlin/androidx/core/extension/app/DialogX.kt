package androidx.core.extension.app

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import androidx.core.extension.R
import androidx.core.extension.databinding.ExtLayoutDialogLoadingBinding
import androidx.core.extension.os.mainHandler
import androidx.core.os.postDelayed

private const val DEFAULT_LOADING_DIALOG_DELAYED = 1500L

fun Activity.showProgressDialog(
    isDelayed: Boolean,
    transparent: Boolean,
    action: (dialog: Dialog?) -> Unit
) {
    if (!isAlive()) return action.invoke(null)
    val dialog = Dialog(this, R.style.Theme_Sample_Dialog)
    val binding = ExtLayoutDialogLoadingBinding.inflate(LayoutInflater.from(this))
    dialog.setContentView(binding.root)
    dialog.setCancelable(false)
    dialog.window?.setGravity(Gravity.CENTER)
    if (transparent) {
        binding.root.setBackgroundColor(Color.TRANSPARENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    dialog.show()
    if (isDelayed) mainHandler.postDelayed(DEFAULT_LOADING_DIALOG_DELAYED) { action.invoke(dialog) }
    else action.invoke(dialog)
}