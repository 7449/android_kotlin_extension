package androidx.core.extension.http

sealed class DataWrapper<out R> {

    /**
     * 初始状态
     */
    data object Normal : DataWrapper<Nothing>()

    /**
     * 正在加载
     */
    sealed class Loading : DataWrapper<Nothing>() {
        data object Default : Loading()
        data object Refresh : Loading()
        data object More : Loading()
    }

    /**
     * 数据为空
     */
    sealed class Empty : DataWrapper<Nothing>() {
        data object Default : Empty()
        data object Load : Empty()
    }

    /**
     * 数据请求成功
     */
    data class Success<out T>(val data: T?) : DataWrapper<T>()

    /**
     * 数据请求出错
     */
    data class Failure(val exception: Throwable) : DataWrapper<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Failure[message=${exception.message}]"
            is Normal -> "Normal"
            is Empty -> "Empty[$this]"
            is Loading -> "Loading[$this]"
        }
    }

}