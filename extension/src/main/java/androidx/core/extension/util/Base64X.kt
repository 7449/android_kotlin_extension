package androidx.core.extension.util

import android.util.Base64

val ByteArray.encodeBase64: ByteArray
    get() = Base64.encode(this, Base64.DEFAULT)

val String.encodeBase64: String
    get() = Base64.encodeToString(toByteArray(), Base64.DEFAULT)

val ByteArray.decodeBase64: ByteArray
    get() = Base64.decode(this, Base64.DEFAULT)

val String.decodeBase64: String
    get() = String(Base64.decode(this, Base64.DEFAULT))