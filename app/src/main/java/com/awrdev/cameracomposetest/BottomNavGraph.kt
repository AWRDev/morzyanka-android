package com.awrdev.cameracomposetest

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun BottomNavGraph(navController: NavHostController, context: Context, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = BottomBarScreen.Decoder.route){
        composable(route = BottomBarScreen.Decoder.route) {
            DecoderScreen(context = context, viewModel = viewModel)
        }
        composable(route = BottomBarScreen.Chat.route) {

        }
        composable(route = BottomBarScreen.Settings.route) {

        }
    }
}