package androidx.core.extension.wrapper.http

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class DataResult<T>(
    val code: Int,
    val message: String?,
    val data: T?,
) {

    companion object {
        fun <T> of(data: T?, code: Int = 200) = DataResult(code = code, message = "", data = data)
    }

    val notNullData: T
        get() = data ?: throw KotlinNullPointerException("data == null")

}