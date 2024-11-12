package com.ticketswapassessment.ui.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ticketswapassessment.network.response.ArtObject
import com.ticketswapassessment.ui.components.ArtImage
import com.ticketswapassessment.util.applyAnimationScopes

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ArtObjectCard(
    modifier: Modifier,
    artObject: ArtObject,
    onClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null
) {
    Card(
        shape = CutCornerShape(0.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.Transparent),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(0.dp)),
        onClick = { onClick.invoke() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ArtImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                id = artObject.id,
                hasImage = artObject.hasImage,
                url = artObject.webImage?.url,
                alignment = Alignment.Center,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(120.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.4f), Color
                                    .Transparent
                            ),
                            startY = Float.POSITIVE_INFINITY,
                            endY = 0f
                        )
                    )
            )

            Text(
                modifier = Modifier
                    .applyAnimationScopes(sharedTransitionScope, animatedContentScope, "title-${artObject.id ?: ""}")
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Start, text = artObject.title, maxLines = 2,
                color = Color
                    .White,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}