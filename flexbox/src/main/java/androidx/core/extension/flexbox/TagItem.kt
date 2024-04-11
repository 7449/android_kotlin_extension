package androidx.core.extension.flexbox

import java.io.Serializable

interface TagItem : Serializable {
    val tagText: String
    val tagSelect: Boolean get() = false
}