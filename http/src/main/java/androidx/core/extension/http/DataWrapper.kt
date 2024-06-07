package androidx.core.extension.http

sealed class DataWrapper<out R> {

    val isLoading get() = this is Loading

    val isEmpty get() = this is Empty

    val isFailure get() = this is Failure

    val isSuccess get() = this is Success

    val value get() = if (this is Success) data else null

    val notNullValue get() = (this as Success).notNullData

    /**
     * 初始状态
     */
    data object Normal : DataWrapper<Nothing>()

    /**
     * 正在加载
     */
    sealed class Loading : DataWrapper<Nothing>() {
        data object Default : Loading()
        data object More : Loading()
    }

    /**
     * 数据为空
     */
    sealed class Empty : DataWrapper<Nothing>() {
        data object Default : Empty()
        data object More : Empty()
    }

    /**
     * 数据请求出错
     */
    sealed class Failure(val exception: Throwable) : DataWrapper<Nothing>() {
        data class Default(val ex: Throwable) : Failure(ex)
        data class More(val ex: Throwable) : Failure(ex)
    }

    /**
     * 数据请求成功
     */
    data class Success<out T>(val data: T?) : DataWrapper<T>() {
        val notNullData: T get() = data ?: throw KotlinNullPointerException("data == null")
    }

}