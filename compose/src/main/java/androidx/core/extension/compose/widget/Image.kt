package androidx.core.extension.compose.widget

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.extension.compose.DefaultUA
import androidx.core.extension.text.logE
import coil3.compose.AsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation
import java.io.Serializable

data class LazyImageModel(
    val model: Any? = null,
    val header: Map<String, String> = emptyMap(),
) : Serializable

@Composable
fun LazyImage(
    model: LazyImageModel,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    placeholder: Int? = null,
    radius: Int = 0,
) = AsyncImage(
    model = ImageRequest.Builder(LocalContext.current)
        .data(model.model)
        .crossfade(true)
        .httpHeaders(model.toHeaders())
        .transformations(RoundedCornersTransformation(radius.toFloat()))
        .build(),
    contentDescription = "",
    modifier = modifier.shape(radius),
    contentScale = contentScale,
    placeholder = placeholder?.let { painterResource(it) },
    error = placeholder?.let { painterResource(it) },
    fallback = placeholder?.let { painterResource(it) },
    onError = { logE("Coil Failure :${it.result.throwable}") }
)

private fun LazyImageModel.toHeaders(): NetworkHeaders {
    return NetworkHeaders.Builder().apply { header.forEach { (t, u) -> add(t, u) } }.build()
}

private fun Modifier.shape(radius: Int = 0) = apply {
    if (radius != 0) clip(RoundedCornerShape(radius))
}

fun Any?.toLazyImageModel(referer: String? = null, ua: String = DefaultUA): LazyImageModel {
    val headers = HashMap<String, String>()
    headers["User-Agent"] = ua
    referer?.let { headers["Referer"] = it }
    return LazyImageModel(model = this, header = headers)
}

val nullLazyImageModel: LazyImageModel = LazyImageModel()