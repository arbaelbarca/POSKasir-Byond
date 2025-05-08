package com.arbaelbarca.posfantastic.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.home.HomeScreen
//import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.product.AddProductScreen
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.product.DetailProductScreen
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.qris.QrisPaymentScreen
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.success.SuccesPaymentScreen

@Composable
fun HostNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ObjectRouteScreen.HomeRoute.route) {
        composable(ObjectRouteScreen.HomeRoute.route) { HomeScreen(navController) }
        composable(ObjectRouteScreen.DetailProductScreenRoute.route) { DetailProductScreen(navController) }
        composable(ObjectRouteScreen.QrisPaymentScreenRoute.route) { QrisPaymentScreen(navController) }
        composable(ObjectRouteScreen.SuccessPaymentScreenRoute.route) { SuccesPaymentScreen(navController, onShareInvoice = {}, onPrintInvoice = {}) }
//        composable(ObjectRouteScreen.AddProductScreenRoute.route) { AddProductScreen(navController, onBackClick = {}, onPredictClick = {}, onSaveClick = {}) }
    }
}