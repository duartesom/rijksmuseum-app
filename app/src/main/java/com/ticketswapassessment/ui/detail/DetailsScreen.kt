package com.ticketswapassessment.ui.detail

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ticketswapassessment.network.ApiResult
import com.ticketswapassessment.network.response.ArtObjectDetail
import com.ticketswapassessment.ui.components.DetailImage
import com.ticketswapassessment.ui.components.DetailsCard
import com.ticketswapassessment.ui.image_viewer.ImageViewerViewModel
import com.ticketswapassessment.ui.theme.primaryContainerLight
import com.ticketswapassessment.ui.theme.primaryLight15
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsScreen(
    detailsVM: DetailsViewModel = hiltViewModel(),
    imageViewerVM: ImageViewerViewModel = hiltViewModel(),
    objectNumber: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onImageClick: () -> Unit
) {
    val context = LocalContext.current as ComponentActivity
    val details by detailsVM.detailsFlow.collectAsState()
    val hazeState = remember { HazeState() }
    val textColor = MaterialTheme.colorScheme.onSecondaryContainer
    val tint = MaterialTheme.colorScheme.surfaceTint

    LaunchedEffect(objectNumber) {
        Log.d("DetailsScreen", "viewModel.getCollectionDetails($objectNumber)")
        detailsVM.getCollectionDetails(objectNumber)
    }

    DisposableEffect(null) {
        context.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT
            ),
        )
        onDispose { }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = listOf(primaryLight15, primaryContainerLight)))
    ) {
        when (details) {
            is ApiResult.Success -> {
                val artObject = (details as ApiResult.Success<ArtObjectDetail>).data

                Column(
                    Modifier
                        .background(Color.Transparent)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    DetailImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.75f),
                        artObject = artObject,
                        alignment = Alignment.TopCenter,
                        sharedTransitionScope,
                        animatedContentScope,
                        onClick = {
                            imageViewerVM.setImageViewerData(artObject.id, artObject.webImage?.url, it)
                            onImageClick.invoke()
                        }
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .haze(state = hazeState),
                        ) {
                            HazeBackground()
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .hazeChild(state = hazeState) {
                                    backgroundColor = tint
                                    tints = listOf(HazeTint(Color.White.copy(alpha = 0.1f)))
                                    blurRadius = 24.dp
                                    noiseFactor = HazeDefaults.noiseFactor
                                })
                        {
                            DetailsCard(sharedTransitionScope, artObject, animatedContentScope, textColor)
                        }
                    }
                }
            }
            is ApiResult.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).background(Color.Transparent))
            }

            is ApiResult.Error -> {
                ElevatedCard(modifier = Modifier.fillMaxWidth().safeContentPadding().align(Alignment.TopCenter)) {
                    val error = (details as ApiResult.Error)
                    val message = error.message.ifEmpty { error.exception.toString() }
                    Text(text = message, Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
private fun HazeBackground() {
    Spacer(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY),
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.secondaryContainer

                    )
                )
            ),
    )
}

