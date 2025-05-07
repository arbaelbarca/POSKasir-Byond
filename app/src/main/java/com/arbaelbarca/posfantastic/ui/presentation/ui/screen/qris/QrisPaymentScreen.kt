package com.arbaelbarca.posfantastic.ui.presentation.ui.screen.qris

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arbaelbarca.posfantastic.R
import com.arbaelbarca.posfantastic.ui.presentation.navigation.ObjectRouteScreen
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrisPaymentScreen(navController: NavController) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Payment Qris") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Toko Makmur Mahakarya",
                style = TextStyle(
                    fontSize = 25.sp
                )
            )

            Spacer(
                modifier = Modifier.padding(15.dp)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
            ) {
                Image(
                    modifier = Modifier
                        .size(350.dp),
                    painter = painterResource(id = R.drawable.ic_qris),
                    contentDescription = "qris"
                )
            }

            Spacer(
                modifier = Modifier.padding(15.dp)
            )

            Text(
                text = "Total Pembayaran \n Rp 50.0000",
                style = TextStyle(
                    fontSize = 20.sp
                )
            )
        }
    }

    NextPaymentSuccess(navController)
}

@Composable
fun NextPaymentSuccess(navController: NavController) {
    var isSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(3000)
        isSuccess = true
    }

    if (isSuccess)
        navController.navigate(ObjectRouteScreen.SuccessPaymentScreenRoute.route) {
            popUpTo(ObjectRouteScreen.HomeRoute.route) {
                inclusive = false
            }
            launchSingleTop = true // cegah navigasi ulang jika sudah di atas
            isSuccess = false
        }
}