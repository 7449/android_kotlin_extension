package androidx.core.extension.http

data class DataResult<T>(
    val code: Int,
    val message: String?,
    val data: T?,
) {

    val notNullData: T
        get() = data ?: throw KotlinNullPointerException("data == null")

}