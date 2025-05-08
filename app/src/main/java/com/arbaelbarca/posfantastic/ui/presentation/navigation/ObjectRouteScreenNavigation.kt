package com.arbaelbarca.posfantastic.ui.presentation.navigation

import android.graphics.drawable.Icon
import androidx.compose.runtime.Composable
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.arbaelbarca.posfantastic.R

sealed class ObjectRouteScreen(
    val route: String,
    var title: String,
) {
    data object HomeRoute : ObjectRouteScreen(
        route = "HOME",
        title = "Home",
//        defaultIcon = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
    )

    data object DetailProductScreenRoute : ObjectRouteScreen(
        route = "DetailProductScreen",
        title = "DetailProductScreen",
//        defaultIcon = Icons.Filled.Home,
    )

    data object QrisPaymentScreenRoute : ObjectRouteScreen(
        route = "QrisPaymentScreen",
        title = "QrisPaymentScreen",
//        defaultIcon = Icons.Filled.Home,
    )


    data object SuccessPaymentScreenRoute : ObjectRouteScreen(
        route = "SuccessPaymentScreen",
        title = "SuccessPaymentScreen",
//        defaultIcon = Icons.Filled.Home,
    )

    data object AddProductScreenRoute : ObjectRouteScreen(
        route = "AddProductScreen",
        title = "AddProductScreen",
//        defaultIcon = Icons.Filled.Home,
    )
}