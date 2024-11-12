package com.ticketswapassessment.ui.image_viewer

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.hilt.navigation.compose.hiltViewModel
import com.ticketswapassessment.util.applyAnimationScopes
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ImageViewerScreen(
    viewModel: ImageViewerViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBack: () -> Unit
) {
    val data by viewModel.imageViewerModel.collectAsState()

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val scope = rememberCoroutineScope()
    val animationSpeed = 300

    // Handle back navigation, zoom out to scale = 1 and pan to zero, then go to previous screen.
    BackHandler {
        if (scale == 1f && offset == Offset.Zero) {
            onBack()
        } else {
            scope.launch {
                // Zoom out
                val zoomOut = async {
                    Animatable(scale).animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = animationSpeed),
                    ) {
                        scale = value
                    }
                }
                // Set x offset to zero
                val panXToZero = async {
                    Animatable(offset.x).animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = animationSpeed),
                    ) {
                        offset = offset.copy(x = value)
                    }
                }
                // Set y offset to zer
                val panYToZero = async {
                    Animatable(offset.y).animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = animationSpeed),
                    ) {
                        offset = offset.copy(y = value)
                    }
                }
                panXToZero.await()
                panYToZero.await()
                zoomOut.await()

                onBack()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 5f)

                    val imageBitmap = data.bitmap
                    val imageWidth = imageBitmap?.width?.times(scale) ?: 0f
                    val imageHeight = imageBitmap?.height?.times(scale) ?: 0f

                    // Screen dimensions
                    val screenWidth = this.size.width.toFloat()
                    val screenHeight = this.size.height.toFloat()

                    // Calculate max pan offsets, ensuring they are non-negative
                    val maxX = ((imageWidth - screenWidth) / 2).coerceAtLeast(0f)
                    val maxY = ((imageHeight - screenHeight) / 2).coerceAtLeast(0f)

                    // Constrain offset within bounds
                    offset = Offset(
                        x = (offset.x + pan.x).coerceIn(-maxX, maxX),
                        y = (offset.y + pan.y).coerceIn(-maxY, maxY)
                    )
                }
            }
    ) {
        if (data.bitmap != null) {
            Image(
                bitmap = data.bitmap!!,
                contentDescription = "Image Viewer",
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .align(Alignment.Center)
                    .applyAnimationScopes(sharedTransitionScope, animatedContentScope, "image-${data.id}")
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                Text(text = "Failed to load the image")
            }
        }
    }
}