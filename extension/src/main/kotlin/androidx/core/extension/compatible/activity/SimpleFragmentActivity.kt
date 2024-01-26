package androidx.core.extension.compatible.activity

import androidx.core.extension.R
import androidx.core.extension.databinding.ExtLayoutContainerBinding

@Deprecated("Use Compose")
abstract class SimpleFragmentActivity : SimpleHttpActivity(R.layout.ext_layout_container) {

    private val viewBinding by viewBinding(ExtLayoutContainerBinding::bind)

    protected val fragment get() = viewBinding.container

    protected val fragmentId get() = fragment.id

    override val immediatelyRequest get() = false

    override suspend fun requestHttp() {
    }

}