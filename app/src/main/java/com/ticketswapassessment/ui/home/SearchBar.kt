package com.ticketswapassessment.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ticketswapassessment.network.ApiResult
import dev.chrisbanes.haze.HazeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onQueryChange: (String) -> Unit, suggestions: ApiResult<List<String>>, onSearch: (String) -> Unit, hazeState: HazeState) {
    val queryState = rememberSaveable { mutableStateOf("") }
    val expanded = rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.secondary),
            expanded = expanded.value,
            onExpandedChange = { expanded.value = it },
            inputField = { SearchBarInputField(expanded = expanded, hazeState = hazeState, queryState = queryState, onQueryChange = onQueryChange,
                onSearch = onSearch) }
        ) {
            LazyColumn(
                Modifier
                    .testTag("searchBarSuggestionsListTestTag")
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .fillMaxHeight()
            ) {
                when (suggestions) {
                    is ApiResult.Loading -> {
                        item {
                            ListItem(
                                headlineContent = {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(24.dp)
                                    )
                                },
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }

                    is ApiResult.Success -> {
                        val results = suggestions.data
                        if (results.isEmpty()) {
                            item {
                                ListItem(
                                    headlineContent = { Text("No results found.") },
                                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 4.dp)
                                )
                            }
                        } else {
                            items(count = results.size) { index ->
                                ListItem(
                                    headlineContent = { Text(results[index]) },
                                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                    modifier = Modifier
                                        .clickable {
                                            queryState.value = results[index]
                                            expanded.value = false
                                            onSearch.invoke(results[index])
                                        }
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    is ApiResult.Error -> {
                        val message = suggestions.message.ifEmpty { suggestions.exception.message }
                        item {
                            ListItem(
                                headlineContent = { Text("Error finding results: $message") },
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    val hazeState = remember { HazeState() }

    SearchBar(
        onQueryChange = {},
        suggestions = ApiResult.Success(listOf("A","B","C")),
        onSearch = {},
        hazeState = hazeState
    )
}