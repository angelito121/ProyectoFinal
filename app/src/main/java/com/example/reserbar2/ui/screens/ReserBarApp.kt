package com.example.reserbar2.ui.screens

import AppNavigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReserBarApp(navController: NavHostController = rememberNavController()) {
    AppNavigation(navController = navController)
}


