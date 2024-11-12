package com.ticketswapassessment

import androidx.compose.animation.ExperimentalSharedTransitionApi
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
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ticketswapassessment.HomeScreenTest.Companion.HOME_ART_OBJECT_LIST_TAG
import com.ticketswapassessment.HomeScreenTest.Companion.SAMPLE_IMAGE_URL
import com.ticketswapassessment.HomeScreenTest.Companion.SEARCH_BAR_SUGGESTIONS_LIST_TAG
import com.ticketswapassessment.HomeScreenTest.Companion.SEARCH_BAR_TAG
import com.ticketswapassessment.navigation.Destination
import com.ticketswapassessment.network.ApiResult
import com.ticketswapassessment.network.response.ArtObject
import com.ticketswapassessment.network.response.WebImage
import com.ticketswapassessment.ui.home.HomeRepository
import com.ticketswapassessment.ui.home.HomeScreen
import com.ticketswapassessment.ui.home.HomeViewModel
import com.ticketswapassessment.ui.theme.TicketSwapAssessmentTheme
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchBarTest {

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
    fun searchBarSuggestionsSuccess() {
        initRepositoryForSuccess()

        with(composeTestRule) {
            setContent {
                TicketSwapAssessmentTheme {
                    NavHost(
                        navController = rememberNavController(),
                        startDestination = Destination.Home.route
                    ) {
                        composable(Destination.Home.route) {
                            HomeScreen(viewModel = homeViewModel, onNavigateToDetails = { })
                        }
                    }
                }
            }
            clickOnSearchBarAndSearchTerm(this)

            onNodeWithTag(SEARCH_BAR_SUGGESTIONS_LIST_TAG).onChildren().apply {
                // Wait until the items show up, this is due to the debouncing mechanism
                waitUntilExists(hasText(TEST_TERM))
                this[0].performClick()
            }

            waitUntilExists(hasTestTag(HOME_ART_OBJECT_LIST_TAG))

            onNodeWithTag(HOME_ART_OBJECT_LIST_TAG).onChildren().apply {
                assertCountEquals(itemList.size)
                itemList.forEachIndexed { index, artObject ->
                    this[index].assertIsDisplayed()
                    this[index].assertTextContains(artObject.title)
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun searchBarEmptySuggestions() {
        initRepositoryForEmptySuggestions()

        with(composeTestRule) {
            setContent {
                TicketSwapAssessmentTheme {
                    NavHost(
                        navController = rememberNavController(),
                        startDestination = Destination.Home.route
                    ) {
                        composable(Destination.Home.route) {
                            HomeScreen(viewModel = homeViewModel, onNavigateToDetails = { })
                        }
                    }
                }
            }
            clickOnSearchBarAndSearchTerm(this)

            waitUntilExists(hasText("No results found."))
        }
    }

    private fun clickOnSearchBarAndSearchTerm(composeContentTestRule: ComposeContentTestRule) {
        with(composeContentTestRule){
            waitForIdle()

            onNodeWithTag(SEARCH_BAR_TAG).apply {
                performClick()
                performTextInput("test")
            }
            waitForIdle()

            waitUntilExists(hasTestTag(SEARCH_BAR_SUGGESTIONS_LIST_TAG))

            waitForIdle()
        }
    }


    private fun initRepositoryForSuccess() {
        coEvery { homeRepository.getArtObjectsByInvolvedMaker(TEST_TERM) } returns ApiResult.Success(itemList)

        coEvery { homeRepository.searchTerms("test") } returns ApiResult.Success(listOf(TEST_TERM))

        homeViewModel = HomeViewModel(homeRepository)
    }

    private fun initRepositoryForEmptySuggestions() {
        coEvery { homeRepository.getArtObjectsByInvolvedMaker(TEST_TERM) } returns ApiResult.Success(itemList)

        coEvery { homeRepository.searchTerms("test") } returns ApiResult.Success(emptyList())

        homeViewModel = HomeViewModel(homeRepository)
    }

    companion object {
       const  val TEST_TERM = "testTerm"

        val itemList = listOf(
            ArtObject(objectNumber = "1", title = "test", webImage = WebImage(url = SAMPLE_IMAGE_URL), hasImage = true),
        )
    }
}
