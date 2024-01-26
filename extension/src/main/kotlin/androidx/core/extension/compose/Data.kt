package androidx.core.extension.compose

import androidx.compose.runtime.MutableState
import androidx.core.extension.http.DataWrapper

fun <T> MutableState<DataWrapper<T>>.asSuccess(): DataWrapper.Success<T> {
    return value as DataWrapper.Success<T>
}

fun <T> MutableState<DataWrapper<T>>.isSuccess(): Boolean {
    return value is DataWrapper.Success<T>
}

fun <T> MutableState<DataWrapper<T>>.success(values: T) {
    value = DataWrapper.Success(values)
}

fun <T> MutableState<DataWrapper<T>>.successOrNull(): DataWrapper.Success<T>? {
    return value as? DataWrapper.Success<T>
}

fun <T> MutableState<DataWrapper<T>>.isNormal(): Boolean {
    return value is DataWrapper.Normal
}

fun <T> MutableState<DataWrapper<T>>.refresh() {
    value = DataWrapper.Loading.Refresh()
}

fun <T> MutableState<DataWrapper<T>>.refreshing(): Boolean {
    return value is DataWrapper.Loading
}

fun <T> MutableState<DataWrapper<T>>.empty() {
    value = DataWrapper.Empty()
}

fun <T> MutableState<DataWrapper<T>>.isEmpty(): Boolean {
    return value is DataWrapper.Empty
}

fun <T> MutableState<DataWrapper<T>>.error(exception: Exception) {
    value = DataWrapper.Error(exception)
}

fun <T> MutableState<DataWrapper<T>>.isError(): Boolean {
    return value is DataWrapper.Error
}