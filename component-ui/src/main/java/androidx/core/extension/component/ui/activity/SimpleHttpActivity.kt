package androidx.core.extension.component.ui.activity

import android.os.Bundle
import androidx.core.extension.app.lifecycle.simpleLaunch

@Deprecated("Use Compose")
abstract class SimpleHttpActivity(layoutId: Int) : SimpleKernelActivity(layoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateBefore(savedInstanceState)
        if (immediatelyRequest) {
            request()
        }
    }

    open val immediatelyRequest: Boolean = true

    abstract fun onCreateBefore(savedInstanceState: Bundle?)

    abstract suspend fun requestHttp()

    fun request() {
        simpleLaunch { requestHttp() }
    }

}