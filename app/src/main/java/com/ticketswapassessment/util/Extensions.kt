package com.ticketswapassessment.util

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun Modifier.applyAnimationScopes(sharedTransitionScope: SharedTransitionScope?, animatedContentScope: AnimatedContentScope?, id: String):
        Modifier {
    return if (sharedTransitionScope != null && animatedContentScope != null) {
        with(sharedTransitionScope) {
            sharedElement(
                state = rememberSharedContentState(key = id),
                animatedVisibilityScope = animatedContentScope
            )
        }
    } else {
        this
    }
}