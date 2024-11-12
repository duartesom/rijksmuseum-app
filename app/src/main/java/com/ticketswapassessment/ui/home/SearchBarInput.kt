package com.ticketswapassessment.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.ticketswapassessment.ui.theme.secondaryLight
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarInputField(
    expanded: MutableState<Boolean>,
    hazeState: HazeState,
    queryState: MutableState<String>,
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit,
) {
    val onSecondary = MaterialTheme.colorScheme.onSecondary

    SearchBarDefaults.InputField(
        modifier = Modifier
            .testTag("searchBarTestTag")
            .clip(RoundedCornerShape(28.dp))
            .hazeChild(hazeState){
                backgroundColor = secondaryLight
                tints = listOf(HazeTint(secondaryLight.copy(alpha = 0.3f)))
                blurRadius = 16.dp
                alpha = if (expanded.value) 0f else 1f
            },
        colors = SearchBarDefaults.inputFieldColors(
            unfocusedTextColor = onSecondary,
            focusedTextColor = onSecondary,
            cursorColor = onSecondary,
            focusedLeadingIconColor = onSecondary,
            unfocusedLeadingIconColor = onSecondary,
        ),
        query = queryState.value,
        onQueryChange = {
            queryState.value = it
            onQueryChange(it)
        },
        onSearch = {
            expanded.value = false
            onSearch(queryState.value)
        },
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it },
        placeholder = { Text("Search for authors", style = MaterialTheme.typography.titleMedium, color = onSecondary) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = onSecondary) },
        trailingIcon = {
            Box(modifier = Modifier.clickable {
                queryState.value = ""
                onQueryChange("")
            }) {
                if (queryState.value.isNotEmpty() && expanded.value) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = onSecondary)
                }
            }
        }
    )
}