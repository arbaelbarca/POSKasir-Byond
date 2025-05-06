package com.arbaelbarca.posfantastic.ui.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ObjectRouteScreen(
    val route: String,
    var title: String,
    val defaultIcon: ImageVector
) {
    data object HomeRoute : ObjectRouteScreen(
        route = "HOME",
        title = "Home",
        defaultIcon = Icons.Filled.Home,
    )
    data object DetailProductScreenRoute : ObjectRouteScreen(
        route = "DetailProductScreen",
        title = "DetailProductScreen",
        defaultIcon = Icons.Filled.Home,
    )

    data object QrisPaymentScreenRoute : ObjectRouteScreen(
        route = "QrisPaymentScreen",
        title = "QrisPaymentScreen",
        defaultIcon = Icons.Filled.Home,
    )
}