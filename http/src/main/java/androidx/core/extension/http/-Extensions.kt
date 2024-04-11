package androidx.core.extension.http

fun <R> DataWrapper<R>.doActionDsl(dataWrapper: DataWrapperDsl<R>.() -> Unit) =
    DataWrapperDsl<R>().also(dataWrapper).build(this)

inline fun <reified R> DataWrapper<R>.doNormalAction(noinline action: () -> Unit) =
    doAction(normal = action)

inline fun <reified R> DataWrapper<R>.doEmptyAction(noinline action: () -> Unit) =
    doAction(empty = action)

inline fun <reified R> DataWrapper<R>.doLoadingMoreAction(noinline action: () -> Unit) =
    doAction(loadingMore = action)

inline fun <reified R> DataWrapper<R>.doLoadingNormalAction(noinline action: () -> Unit) =
    doAction(loadingNormal = action)

inline fun <reified R> DataWrapper<R>.doLoadingRefreshAction(noinline action: () -> Unit) =
    doAction(loadingRefresh = action)

inline fun <reified R> DataWrapper<R>.doLoadingNothingAction(noinline action: () -> Unit) =
    doAction(loadingNothing = action)

inline fun <reified R> DataWrapper<R>.doSuccessAction(noinline action: (data: R) -> Unit) =
    doAction(success = action)

inline fun <reified R> DataWrapper<R>.doErrorAction(noinline action: (exception: Exception) -> Unit) =
    doAction(error = action)

fun <R> DataWrapper<R>.doAction(
    normal: (() -> Unit)? = null,
    empty: (() -> Unit)? = null,
    loadingMore: (() -> Unit)? = null,
    loadingNormal: (() -> Unit)? = null,
    loadingRefresh: (() -> Unit)? = null,
    loadingNothing: (() -> Unit)? = null,
    success: ((data: R) -> Unit)? = null,
    error: ((exception: Exception) -> Unit)? = null,
) = apply {
    when (this) {
        is DataWrapper.Empty -> empty?.invoke()
        is DataWrapper.Error -> error?.invoke(exception)
        is DataWrapper.Loading.More -> loadingMore?.invoke()
        is DataWrapper.Loading.Normal -> loadingNormal?.invoke()
        is DataWrapper.Loading.Nothing -> loadingNothing?.invoke()
        is DataWrapper.Loading.Refresh -> loadingRefresh?.invoke()
        is DataWrapper.Normal -> normal?.invoke()
        is DataWrapper.Success -> success?.invoke(data)
    }
}