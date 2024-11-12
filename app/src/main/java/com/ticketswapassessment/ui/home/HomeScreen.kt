package com.ticketswapassessment.ui.home

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ticketswapassessment.network.ApiResult
import com.ticketswapassessment.network.response.ArtObject
import com.ticketswapassessment.ui.components.ChangeSystemBarsTheme
import com.ticketswapassessment.ui.theme.primaryContainerLight
import com.ticketswapassessment.ui.theme.primaryLight15
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetails: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
) {

    val context = LocalContext.current as ComponentActivity
    val searchResults by viewModel.searchResults.collectAsState()
    val suggestions by viewModel.termResults.collectAsState()
    val hazeState = remember { HazeState() }

    ChangeSystemBarsTheme(!isSystemInDarkTheme(), context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = listOf(primaryLight15, primaryContainerLight)))
    ) {
        LazyColumn(modifier = Modifier
            .background(Color.Transparent)
            .testTag("homeArtObjectList")
            .haze(hazeState)) {

            item {
                TopInsetPaddingItem()
            }

            when (searchResults) {
                is ApiResult.Success -> {
                    val results = (searchResults as ApiResult.Success<List<ArtObject>>).data

                    if(results.isEmpty()){
                        item {
                            ElevatedCard(modifier = Modifier.fillMaxWidth().padding(16.dp).testTag("homeEmptyResultsCard")) {
                                Text(text = "Couldn't find any results, try searching for the artist name.", Modifier.padding(16.dp))
                            }
                        }
                    } else {
                        items(count = results.size, key = {index -> results[index].id ?: index.toString()}) { index ->
                            ArtObjectCard(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                artObject = results[index],
                                onClick = { onNavigateToDetails.invoke(results[index].objectNumber) },
                                sharedTransitionScope,
                                animatedContentScope
                            )
                            if(index == results.size - 1 && results.size > 1) {
                                // Spacer for bottom navigation bar insets
                                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
                            }
                        }
                    }
                }
                is ApiResult.Error -> {
                    item {
                        ErrorItem(this@Box, searchResults)
                    }

                }
                is ApiResult.Loading -> {
                    item {
                        LoadingItem()
                    }
                }
            }
        }

        SearchBar(
            suggestions = suggestions,
            onQueryChange = {
                viewModel.updateTerm(it)
            },
            onSearch = { viewModel.updateKeyword(it) },
            hazeState = hazeState
        )
    }
}

@Composable
private fun ErrorItem(boxScope: BoxScope, searchResults: ApiResult<List<ArtObject>>) {
    with(boxScope){
        ElevatedCard(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).align(Alignment.BottomCenter)) {
            val error = (searchResults as ApiResult.Error)
            val message = error.message.ifEmpty { error.exception.toString() }
            Text(text = message, Modifier.padding(16.dp))
        }
    }
}

@Composable
private fun LoadingItem() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 32.dp)) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun TopInsetPaddingItem() {
    val displayCutoutInsets = WindowInsets.displayCutout.asPaddingValues().calculateTopPadding()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            // displayCutoutInsets for camera cutout
            // 8.dp for search box margin
            // 56.dp for search box height
            // 16.dp for bottom padding
            .height(displayCutoutInsets + 8.dp + 56.dp + 16.dp)
            .background(Color.Transparent)
    )
}

