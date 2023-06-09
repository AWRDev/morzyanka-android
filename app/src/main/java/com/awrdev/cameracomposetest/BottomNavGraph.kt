package com.awrdev.cameracomposetest

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.awrdev.cameracomposetest.presentation.decoder.MainViewModel
import com.awrdev.cameracomposetest.presentation.history.HistoryScreen


@Composable
fun BottomNavGraph(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = BottomBarScreen.Decoder.route){
        composable(route = BottomBarScreen.Decoder.route) {
            DecoderScreen(viewModel = viewModel)
        }
        composable(route = BottomBarScreen.Chat.route) {
            HistoryScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
    }
}