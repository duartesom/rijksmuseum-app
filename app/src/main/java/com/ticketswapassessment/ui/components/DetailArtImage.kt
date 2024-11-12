package com.ticketswapassessment.ui.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ticketswapassessment.R
import com.ticketswapassessment.network.response.ArtObjectDetail
import com.ticketswapassessment.util.applyAnimationScopes

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailImage(
    modifier: Modifier,
    artObject: ArtObjectDetail,
    alignment: Alignment,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    onClick: (ImageBitmap) -> Unit,
) {
    var imageBitmap: ImageBitmap? by remember { mutableStateOf(null) }

    if(artObject.hasImage){
        AsyncImage(
            modifier = modifier
                .applyAnimationScopes(sharedTransitionScope, animatedContentScope, "image-${artObject.id ?: ""}")
                .clickable {
                    imageBitmap?.let { onClick.invoke(it) }
                },
            alignment = alignment,
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = artObject.webImage?.url)
                .crossfade(true)
                .error(R.drawable.bg_error)
                .listener(
                    onSuccess = { _, result ->
                        val bitmap = result.drawable.toBitmap()
                        imageBitmap = bitmap.asImageBitmap()
                    }
                )
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    } else {
        Box(modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_image),
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}