package androidx.core.extension.util.tag

import java.io.Serializable

interface TagItem : Serializable {
    val tagText: String
    val tagSelect: Boolean get() = false
}