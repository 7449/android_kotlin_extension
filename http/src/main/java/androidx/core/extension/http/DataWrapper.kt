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
    data class Success<out T>(val data: T?) : DataWrapper<T>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Failure[type=$this,message=${exception.message}]"
            is Normal -> "Normal"
            is Empty -> "Empty[$this]"
            is Loading -> "Loading[$this]"
        }
    }

}