package androidx.core.extension.http

inline fun <T> DataWrapper<DataResult<T>>.onResultNotNull(action: (data: T) -> Unit): DataWrapper<DataResult<T>> {
    if (this is DataWrapper.Success<DataResult<T>>) {
        action.invoke(requireNotNull(data?.data))
    }
    return this
}

inline fun <T> DataWrapper<DataResult<T>>.onResult(action: (data: T?) -> Unit): DataWrapper<DataResult<T>> {
    if (this is DataWrapper.Success<DataResult<T>>) {
        action.invoke(data?.data)
    }
    return this
}

inline fun <T> DataWrapper<T?>.onSuccessNotNull(action: (data: T) -> Unit): DataWrapper<T?> {
    if (this is DataWrapper.Success<T?>) {
        action.invoke(requireNotNull(data))
    }
    return this
}

inline fun <T> DataWrapper<T>.onSuccess(action: (data: T?) -> Unit): DataWrapper<T> {
    if (this is DataWrapper.Success<T>) {
        action.invoke(data)
    }
    return this
}

inline fun <T> DataWrapper<T>.onFailure(action: (exception: Throwable) -> Unit): DataWrapper<T> {
    if (this is DataWrapper.Failure) {
        action.invoke(exception)
    }
    return this
}

inline fun <T> DataWrapper<T>.onLoadingDefault(action: () -> Unit): DataWrapper<T> {
    if (this is DataWrapper.Loading.Default) {
        action.invoke()
    }
    return this
}

inline fun <T> DataWrapper<T>.onLoadingRefresh(action: () -> Unit): DataWrapper<T> {
    if (this is DataWrapper.Loading.Refresh) {
        action.invoke()
    }
    return this
}

inline fun <T> DataWrapper<T>.onLoadingMore(action: () -> Unit): DataWrapper<T> {
    if (this is DataWrapper.Loading.More) {
        action.invoke()
    }
    return this
}

inline fun <T> DataWrapper<T>.onEmptyDefault(action: () -> Unit): DataWrapper<T> {
    if (this is DataWrapper.Empty.Default) {
        action.invoke()
    }
    return this
}

inline fun <T> DataWrapper<T>.onEmptyLoad(action: () -> Unit): DataWrapper<T> {
    if (this is DataWrapper.Empty.Load) {
        action.invoke()
    }
    return this
}