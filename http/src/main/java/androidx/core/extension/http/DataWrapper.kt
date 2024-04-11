package androidx.core.extension.http

sealed class DataWrapper<out R> {

    /**
     * 初始状态
     */
    data class Normal(val value: String = "") : DataWrapper<Nothing>()

    /**
     * 数据请求成功
     */
    data class Success<out T>(val data: T) : DataWrapper<T>()

    /**
     * 数据请求出错
     */
    data class Error(val exception: Exception) : DataWrapper<Nothing>() {
        // TODO
    }

    /**
     * 正在加载
     */
    sealed class Loading(val type: Int) : DataWrapper<Nothing>() {

        companion object {
            const val NOTHING = -1 //无动作
            const val NORMAL = 0 //普通加载
            const val REFRESH = 1 //下拉加载
            const val MORE = 2 //上拉加载
        }

        data class Nothing(val message: String = "") : Loading(NOTHING)
        data class Normal(val message: String = "") : Loading(NORMAL)
        data class Refresh(val message: String = "") : Loading(REFRESH)
        data class More(val message: String = "") : Loading(MORE)
    }

    /**
     * 数据为空
     */
    data class Empty(val value: String = "") : DataWrapper<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[message=${exception.message}]"
            is Empty -> "Empty[$value]"
            is Normal -> "Normal[$value]"
            is Loading.More -> "LoadingMore[$type]"
            is Loading.Normal -> "LoadingNormal[$type]"
            is Loading.Nothing -> "LoadingNothing[$type]"
            is Loading.Refresh -> "LoadingRefresh[$type]"
        }
    }

}