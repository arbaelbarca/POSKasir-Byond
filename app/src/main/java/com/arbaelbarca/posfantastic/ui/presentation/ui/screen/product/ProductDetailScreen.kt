package com.arbaelbarca.posfantastic.ui.presentation.ui.screen.product

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arbaelbarca.posfantastic.R
import com.arbaelbarca.posfantastic.ui.presentation.navigation.ObjectRouteScreen
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.items.LoadingOverlay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProductScreen(navController: NavController) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Detail Product") },
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

        var isLoading = remember { mutableStateOf(false) }
        var scopeFunciton = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F5FA))
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top
        ) {

            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Spacer(Modifier.height(16.dp))

                // List of Products
                repeat(3) {
                    OrderItem(
                        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                        price = "10.000",
                        quantity = 2,
                        onIncrease = {},
                        onDecrease = {}
                    )
                    Spacer(Modifier.height(8.dp))
                }

                Spacer(Modifier.height(16.dp))

                // Payment Detail
                Text("Rincian Pembayaran", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        RowItem(label = "Subtotal", value = "10000")
                        RowItem(label = "Service Charge", value = "2000")
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Payment Method
                Text("Metode Pembayaran", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var selected by remember { mutableStateOf("Non Tunai") }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selected == "Tunai",
                                onClick = { selected = "Tunai" }
                            )
                            Text("Tunai")
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selected == "Non Tunai",
                                onClick = { selected = "Non Tunai" }
                            )
                            Text("Non Tunai")
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))


            }

            // Total + Button

            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .weight(1f)
            )

            Box(
                modifier = Modifier
                    .background(color = Color.White)
            ) {
                Column() {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp, vertical = 25.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total Pembayaran", style = MaterialTheme.typography.bodyLarge)
                        Text("12000", style = MaterialTheme.typography.bodyLarge)
                    }

                    Button(
                        onClick = {
                            NextPageScreen(
                                scopeFunciton,
                                isLoading,
                                navController
                            )
                        },
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 3.dp)
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF435283))
                    ) {
                        Text("Lanjutkan Pembayaran", color = Color.White)
                    }
                }

            }

            LoadingOverlay(isLoading.value)
        }
    }

}

fun NextPageScreen(
    scope: CoroutineScope,
    isLoading: MutableState<Boolean>,
    navController: NavController
) {
    scope.launch {
        isLoading.value = true
        delay(1500)
        isLoading.value = false
        navController.navigate(ObjectRouteScreen.QrisPaymentScreenRoute.route)
    }
}

@Composable
fun OrderItem(title: String, price: String, quantity: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(56.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {

        }

        Spacer(Modifier.width(8.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = price, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(25.dp),
                painter = painterResource(id = R.drawable.ic_minus),
                contentDescription = "remove"
            )
            Spacer(
                modifier = Modifier.padding(13.dp, 0.dp, 0.dp, 0.dp)
            )
            Text(quantity.toString())
            IconButton(onClick = onIncrease) {
                Icon(Icons.Default.AddCircle, contentDescription = "Add", tint = Color(0xFF435283))
            }
        }
    }
}

@Composable
fun RowItem(label: String, value: String) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(value)
    }
}
