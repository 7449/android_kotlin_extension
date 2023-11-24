package androidx.core.extension.util.tag

data class TagModel<T>(
    val parent: T,
    val text: String,
    val select: Boolean = false,
) : TagItem {
    override val tagText: String
        get() = text
    override val tagSelect: Boolean
        get() = select
}
