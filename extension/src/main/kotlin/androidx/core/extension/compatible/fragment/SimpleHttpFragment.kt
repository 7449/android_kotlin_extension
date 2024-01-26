package androidx.core.extension.compatible.fragment

import android.os.Bundle
import android.view.View
import androidx.core.extension.http.simpleHttp
import androidx.fragment.app.Fragment

@Deprecated("Use Compose")
abstract class SimpleHttpFragment(layoutId: Int) : Fragment(layoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedBefore(savedInstanceState)
        if (immediatelyRequest) {
            request()
        }
    }

    open val immediatelyRequest: Boolean = true

    abstract fun onViewCreatedBefore(savedInstanceState: Bundle?)

    abstract suspend fun requestHttp()

    fun request() {
        simpleHttp { requestHttp() }
    }

}