package androidx.core.extension.http

fun <T> DataWrapper<T>.getSuccessOrNull(): DataWrapper.Success<T>? {
    if (this is DataWrapper.Success) {
        return this
    }
    return null
}

inline fun <T : Any> DataWrapper<DataResult<T>>.onResultNotNull(action: (data: T) -> Unit): DataWrapper<DataResult<T>> {
    if (this is DataWrapper.Success && data?.data != null) {
        action.invoke(requireNotNull(data.data))
    }
    return this
}

inline fun <T : Any> DataWrapper<DataResult<T>>.onResult(action: (data: T?) -> Unit): DataWrapper<DataResult<T>> {
    if (this is DataWrapper.Success) {
        action.invoke(data?.data)
    }
    return this
}

inline fun <T : Any> DataWrapper<T>.onSuccessNotNull(action: (data: T) -> Unit): DataWrapper<T> {
    if (this is DataWrapper.Success && data != null) {
        action.invoke(data)
    }
    return this
}

inline fun <T : Any> DataWrapper<T>.onSuccess(action: (data: T?) -> Unit): DataWrapper<T> {
    if (this is DataWrapper.Success) {
        action.invoke(data)
    }
    return this
}

inline fun <T> DataWrapper<T>.onFailureDefault(action: (exception: Throwable) -> Unit): DataWrapper<T> {
    if (this is DataWrapper.Failure.Default) {
        action.invoke(exception)
    }
    return this
}

inline fun <T> DataWrapper<T>.onFailureMore(action: (exception: Throwable) -> Unit): DataWrapper<T> {
    if (this is DataWrapper.Failure.More) {
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

inline fun <T> DataWrapper<T>.onEmptyMore(action: () -> Unit): DataWrapper<T> {
    if (this is DataWrapper.Empty.More) {
        action.invoke()
    }
    return this
}