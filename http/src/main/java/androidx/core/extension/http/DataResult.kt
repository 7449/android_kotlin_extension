package androidx.core.extension.http

data class DataResult<T>(
    val code: Int,
    val message: String?,
    val data: T?,
) {

    companion object {
        fun <T> of(data: T?) = DataResult(code = 200, message = "", data = data)
    }

    val notNullData: T
        get() = data ?: throw KotlinNullPointerException("data == null")

}