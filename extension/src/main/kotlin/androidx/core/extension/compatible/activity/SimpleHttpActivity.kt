package androidx.core.extension.compatible.activity

import android.os.Bundle
import androidx.core.extension.net.simpleHttp

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
        simpleHttp { requestHttp() }
    }

}