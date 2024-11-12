package com.ticketswapassessment.ui.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ticketswapassessment.R
import com.ticketswapassessment.util.applyAnimationScopes

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ArtImage(
    modifier: Modifier,
    id: String? = "",
    url: String?,
    hasImage: Boolean = true,
    alignment: Alignment = Alignment.Center,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
) {
    val _modifier = modifier

    if (hasImage) {
        AsyncImage(
            modifier = _modifier.applyAnimationScopes(sharedTransitionScope, animatedContentScope, "image-${id ?: ""}"),
            alignment = alignment,
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = url)
                .crossfade(true)
                .error(R.drawable.bg_error)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    } else {
        Box(Modifier.fillMaxWidth().height(210.dp).background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_image),
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}