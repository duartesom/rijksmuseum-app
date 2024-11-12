package com.ticketswapassessment.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ticketswapassessment.R
import com.ticketswapassessment.navigation.Destination
import com.ticketswapassessment.ui.components.AppAlertDialog
import com.ticketswapassessment.ui.detail.DetailsScreen
import com.ticketswapassessment.ui.home.HomeScreen
import com.ticketswapassessment.ui.image_viewer.ImageViewerScreen
import com.ticketswapassessment.ui.image_viewer.ImageViewerViewModel
import com.ticketswapassessment.ui.theme.TicketSwapAssessmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            TicketSwapAssessmentTheme {
                val navController = rememberNavController()
                val imageViewerVM: ImageViewerViewModel = hiltViewModel()
                val activityViewModel: ActivityViewModel = hiltViewModel()

                val isConnected by activityViewModel.isConnectedFlow.collectAsState()
                var dialogDismissed by remember { mutableStateOf(false) }

                SharedTransitionLayout {
                    NavHost(
                        navController = navController,
                        startDestination = Destination.Home.route
                    ) {
                        composable(Destination.Home.route) {
                            HomeScreen(
                                onNavigateToDetails = {
                                    navController.navigate(route = Destination.Details.withObjectNumber(it))
                                },
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@composable
                            )
                        }
                        composable(Destination.Details.route)
                        { backStackEntry ->
                            val objectNumber = backStackEntry.arguments?.getString("objectNumber") ?: ""
                            DetailsScreen(
                                imageViewerVM = imageViewerVM,
                                objectNumber = objectNumber,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@composable,
                                onImageClick = {
                                    navController.navigate(Destination.ImageViewer.route)
                                }
                            )
                        }
                        composable(Destination.ImageViewer.route) {
                            ImageViewerScreen(
                                viewModel = imageViewerVM,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@composable,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                    AppAlertDialog(
                        showDialog = !isConnected && !dialogDismissed,
                        onDismissRequest = {
                            dialogDismissed = true
                        },
                        icon = painterResource(id = R.drawable.ic_no_internet),
                        title = "No internet connection detected",
                        message = "Please check your connection and try again.",
                    )
                }

            }
        }
    }
}
