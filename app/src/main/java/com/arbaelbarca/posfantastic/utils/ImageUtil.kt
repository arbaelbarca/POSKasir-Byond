package com.arbaelbarca.posfantastic.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arbaelbarca.posfantastic.R

@Composable
fun RemoteImageLoader(url: String?, modifier: Modifier) {
    if (url.isNullOrBlank()) {
        Image(
            painter = painterResource(id = R.drawable.no_product_not_found),
            contentDescription = "image url empty",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        // Load gambar dari internet
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = "iamge url",
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.no_product_not_found),
            modifier = modifier
        )
    }
}