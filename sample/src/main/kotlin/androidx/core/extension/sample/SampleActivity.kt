package androidx.core.extension.sample

import android.app.Activity
import android.os.Bundle
import androidx.core.extension.content.showToastShort

class SampleActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showToastShort("ExtSample")
    }

}