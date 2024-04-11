package androidx.core.extension.component.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.extension.content.getColorValue
import androidx.core.extension.content.getDimensionValue
import androidx.core.extension.widget.gone
import androidx.core.extension.widget.linearParams
import androidx.core.extension.widget.matchLinearParams
import androidx.core.extension.widget.visible
import androidx.viewbinding.ViewBinding

@Deprecated("Use Compose")
abstract class SimpleKernelActivity(private val layoutId: Int) : AppCompatActivity() {

    fun <T : ViewBinding> viewBinding(view: (View) -> T) = lazy { view.invoke(rootView) }

    private val toolbar by lazy { Toolbar(this).apply { gone() } }
    private val rootView by lazy { View.inflate(this, layoutId, null) }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateContainerView()
    }

    override fun setTitle(titleId: Int) {
        title = getString(titleId)
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home && toolbarHomeAsUp) {
            onClickMenuHomeAsUp()
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun updateContainerView() {
        val contentView = findViewById<FrameLayout>(android.R.id.content)
        contentView.removeAllViews()
        val parentView = LinearLayout(contentView.context)
        parentView.orientation = LinearLayout.VERTICAL
        val actionBarSize = getDimensionValue(android.R.attr.actionBarSize)
        if (showToolbar) {
            parentView.addView(updateToolbar(), linearParams(height = actionBarSize))
        }
        parentView.addView(rootView, matchLinearParams.apply { weight = 1f })
        contentView.addView(parentView, matchLinearParams)
    }

    private fun updateToolbar(): Toolbar {
        toolbar.visible()
        toolbar.popupTheme = androidx.appcompat.R.style.ThemeOverlay_AppCompat_Light
        toolbar.context.setTheme(androidx.appcompat.R.style.ThemeOverlay_AppCompat_Dark_ActionBar)
        toolbar.elevation = 0f
        toolbarNavigationIcon?.let { toolbar.setNavigationIcon(it) }
        toolbar.setBackgroundColor(getColorValue(android.R.attr.colorPrimary))
        setSupportActionBar(toolbar)
        if (toolbarHomeAsUp) {
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setOnMenuItemClickListener {
            onClickMenuItem(it)
            return@setOnMenuItemClickListener true
        }
        return toolbar
    }

    protected open fun onClickMenuHomeAsUp() {
        finish()
    }

    protected open fun onClickMenuItem(item: MenuItem) {
    }

    open val showToolbar: Boolean get() = true
    open val toolbarHomeAsUp: Boolean get() = true
    open val toolbarNavigationIcon: Int? get() = null

}