package androidx.core.extension.http

class DataWrapperDsl<R> {

    private var normal: (() -> Unit)? = null
    private var empty: (() -> Unit)? = null
    private var loadingMore: (() -> Unit)? = null
    private var loadingNormal: (() -> Unit)? = null
    private var loadingRefresh: (() -> Unit)? = null
    private var loadingNothing: (() -> Unit)? = null
    private var success: ((R) -> Unit)? = null
    private var error: ((Exception) -> Unit)? = null

    fun normal(action: () -> Unit) {
        this.normal = action
    }

    fun empty(action: () -> Unit) {
        this.empty = action
    }

    fun loadingMore(action: () -> Unit) {
        this.loadingMore = action
    }

    fun loadingNormal(action: () -> Unit) {
        this.loadingNormal = action
    }

    fun loadingRefresh(action: () -> Unit) {
        this.loadingRefresh = action
    }

    fun loadingNothing(action: () -> Unit) {
        this.loadingNothing = action
    }

    fun success(action: (data: R) -> Unit) {
        this.success = action
    }

    fun error(action: (exception: Exception) -> Unit) {
        this.error = action
    }

    internal fun build(dataWrapper: DataWrapper<R>): DataWrapper<R> {
        return dataWrapper.doAction(
            normal = normal,
            empty = empty,
            loadingMore = loadingMore,
            loadingNormal = loadingNormal,
            loadingRefresh = loadingRefresh,
            loadingNothing = loadingNothing,
            success = success,
            error = error
        )
    }

}