package com.ticketswapassessment

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ticketswapassessment.navigation.Destination
import com.ticketswapassessment.network.ApiResult
import com.ticketswapassessment.network.response.ArtObject
import com.ticketswapassessment.network.response.WebImage
import com.ticketswapassessment.ui.home.HomeRepository
import com.ticketswapassessment.ui.home.HomeScreen
import com.ticketswapassessment.ui.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @MockK(relaxed = true)
    lateinit var homeViewModel: HomeViewModel

    @MockK(relaxed = true)
    lateinit var homeRepository: HomeRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun itemsWithImagesAndSearchTerms() {
        initRepositoryForSuccess()

        with(composeTestRule) {
            setContent {
                NavHost(
                    navController = rememberNavController(),
                    startDestination = Destination.Home.route
                ) {
                    composable(Destination.Home.route) {
                        HomeScreen(viewModel = homeViewModel, onNavigateToDetails = { })
                    }
                }
            }
            waitUntilExists(hasTestTag(HOME_ART_OBJECT_LIST_TAG))

            waitForIdle()

            onNodeWithTag(HOME_ART_OBJECT_LIST_TAG).onChildren().apply {
                assertCountEquals(itemList.size)
                itemList.forEachIndexed { index, artObject ->
                    this[index].assertIsDisplayed()
                    this[index].assertTextContains(artObject.title)
                }
            }

            waitUntilExists(hasTestTag(SEARCH_BAR_TAG))
            onNodeWithTag(SEARCH_BAR_TAG).performClick()

            waitForIdle()

            waitUntilExists(hasTestTag(SEARCH_BAR_SUGGESTIONS_LIST_TAG))

            onNodeWithTag(SEARCH_BAR_SUGGESTIONS_LIST_TAG).onChildren().apply {
                // Wait until the items show up, this is due to the debouncing mechanism
                waitUntilExists(hasText(suggestionList[0]))
                assertCountEquals(itemList.size)
                suggestionList.forEachIndexed { index, suggestion ->
                    this[index].assertIsDisplayed()
                    this[index].assertTextContains(suggestion)
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun emptyResults() {
        initRepositoryForEmptyResults()

        with(composeTestRule) {
            setContent {
                NavHost(
                    navController = rememberNavController(),
                    startDestination = Destination.Home.route
                ) {
                    composable(Destination.Home.route) {
                        HomeScreen(viewModel = homeViewModel, onNavigateToDetails = { })
                    }
                }
            }
            waitUntilExists(hasTestTag(HOME_ART_OBJECT_LIST_TAG))

            waitForIdle()

            waitUntilExists(hasText("Couldn't find any results, try searching for the artist name."))
        }
    }

    private fun initRepositoryForSuccess() {
        coEvery { homeRepository.getArtObjectsByInvolvedMaker(any()) } returns ApiResult.Success(itemList)

        coEvery { homeRepository.searchTerms(any()) } returns ApiResult.Success(suggestionList)

        homeViewModel = HomeViewModel(homeRepository)
    }

    private fun initRepositoryForEmptyResults() {
        coEvery { homeRepository.getArtObjectsByInvolvedMaker(any()) } returns ApiResult.Success(emptyList())

        homeViewModel = HomeViewModel(homeRepository)
    }

    companion object {
        const val HOME_ART_OBJECT_LIST_TAG = "homeArtObjectList"
        const val SEARCH_BAR_TAG = "searchBarTestTag"
        const val SEARCH_BAR_SUGGESTIONS_LIST_TAG = "searchBarSuggestionsListTestTag"

        const val SAMPLE_IMAGE_URL = "https://lh5.ggpht" +
                ".com/Dm4qOkEfe0P9YqnN7s-KRxy6K_o4trePSnlVvGNg0TPGcKmiDS_sdmqqbSeSENg22FFjyIz0xI2CEJhkrk9IfmDYHwo=s0"

        val itemList = listOf(
            ArtObject(objectNumber = "1", title = "Title 1", webImage = WebImage(url = SAMPLE_IMAGE_URL), hasImage = true),
            ArtObject(objectNumber = "2", title = "Title 2", webImage = WebImage(url = SAMPLE_IMAGE_URL), hasImage = true),
            ArtObject(objectNumber = "3", title = "Title 3", webImage = WebImage(url = SAMPLE_IMAGE_URL), hasImage = true)
        )

        val suggestionList = listOf("Term 1", "Term 2", "Term 3")
    }
}

@OptIn(ExperimentalTestApi::class)
fun ComposeContentTestRule.waitUntilExists(
    matcher: SemanticsMatcher,
    timeoutMillis: Long = 5000L
) {
    return this.waitUntilNodeCount(matcher, 1, timeoutMillis)
}