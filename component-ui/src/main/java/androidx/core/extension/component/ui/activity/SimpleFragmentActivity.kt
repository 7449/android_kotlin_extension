package androidx.core.extension.component.ui.activity

import androidx.core.extension.component.ui.R
import androidx.core.extension.component.ui.databinding.ComponentUiLayoutContainerBinding

@Deprecated("Use Compose")
abstract class SimpleFragmentActivity : SimpleHttpActivity(R.layout.component_ui_layout_container) {

    private val viewBinding by viewBinding(ComponentUiLayoutContainerBinding::bind)

    protected val fragment get() = viewBinding.container

    protected val fragmentId get() = fragment.id

    override val immediatelyRequest get() = false

    override suspend fun requestHttp() {
    }

}