package com.arbaelbarca.posfantastic.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.home.HomeScreen
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.product.AddProductScreen
//import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.product.AddProductScreen
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.product.DetailProductScreen
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.qris.QrisPaymentScreen
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.success.SuccesPaymentScreen
import com.arbaelbarca.posfantastic.ui.viewmodel.ProductViewModel

@Composable
fun HostNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ObjectRouteScreen.HomeRoute.route) {
        composable(ObjectRouteScreen.HomeRoute.route) {
            val parentEntry = remember {
                navController.getBackStackEntry( ObjectRouteScreen.HomeRoute.route)
            }
            val productViewModel: ProductViewModel = hiltViewModel(parentEntry)
            HomeScreen(navController, productViewModel)
        }
        composable(ObjectRouteScreen.DetailProductScreenRoute.route) {
            val parentEntry = remember {
                navController.getBackStackEntry( ObjectRouteScreen.HomeRoute.route)
            }
            val productViewModel: ProductViewModel = hiltViewModel(parentEntry)
            DetailProductScreen(navController, productViewModel)
        }
        composable(ObjectRouteScreen.QrisPaymentScreenRoute.route) { QrisPaymentScreen(navController) }
        composable(ObjectRouteScreen.SuccessPaymentScreenRoute.route) { SuccesPaymentScreen(navController, onShareInvoice = {}, onPrintInvoice = {}) }
        composable(ObjectRouteScreen.AddProductScreenRoute.route) { AddProductScreen(navController, onBackClick = {}, onPredictClick = {}, onSaveClick = {}) }
    }
}