package com.arbaelbarca.posfantastic.ui.presentation.ui.screen.product

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.RemoveCircle
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.AddCircle
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arbaelbarca.posfantastic.R
import com.arbaelbarca.posfantastic.data.model.response.ProductsResponse
import com.arbaelbarca.posfantastic.ui.presentation.navigation.ObjectRouteScreen
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.items.LoadingOverlay
import com.arbaelbarca.posfantastic.ui.viewmodel.ProductViewModel
import com.arbaelbarca.posfantastic.utils.RemoteImageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProductScreen(navController: NavController, productViewModel: ProductViewModel) {
    var subTotal = remember { mutableStateOf(0) }
    var isLoading = remember { mutableStateOf(false) }
    val scopeFunction = rememberCoroutineScope()
    val selectedProducts = productViewModel.selectedProduct.value

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Detail Product") },
                navigationIcon = { /* Add back icon if needed */ }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(15.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Pembayaran", style = MaterialTheme.typography.bodyLarge)
                    Text("Rp 12.000", style = MaterialTheme.typography.bodyLarge)
                }
                Button(
                    onClick = {
                        NextPageScreen(
                            scopeFunction,
                            isLoading,
                            navController
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF435283))
                ) {
                    Text("Lanjutkan Pembayaran", color = Color.White)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F5FA))
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {

            ProductList(selectedProducts)

            Spacer(Modifier.height(16.dp))

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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
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

        LoadingOverlay(isLoading.value)
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
fun OrderItem(product: ProductsResponse.ProductItem, onIncrease: (count: Int) -> Unit, onDecrease: (count: Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            RemoteImageLoader(product.imageUrl, Modifier.fillMaxSize())
        }

        Spacer(Modifier.width(8.dp))

        Column(
            Modifier
                .weight(1f)
                .align(Alignment.Top)) {
            Text(
                text = product.name!!,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = product.sellingPrice.toString(), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        }

        var count by remember { mutableStateOf(0) }
        val visible = count > 0

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AnimatedVisibility(visible = visible) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (count > 0) count--
                            onDecrease(count)
//                            onClickItem.invoke(product.copy(quantity = count))
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Rounded.RemoveCircle,
                            contentDescription = "Minus",
                            tint = colorResource(R.color.cornflower_blue_100)
                        )
                    }

                    Text(
                        text = "$count", fontSize = 14.sp
                    )
                }

            }


            IconButton(
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp),
                onClick = {
                    count++
                    onIncrease(count)
//                    onClickItem.invoke(product.copy(quantity = count))
                }) {
                Icon(
                    Icons.Rounded.AddCircle,
                    contentDescription = null,
                    tint = colorResource(R.color.cornflower_blue_600)
                )
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

@Composable
fun ProductList(productList: List<ProductsResponse.ProductItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(productList) { product ->
            val quantity = remember { mutableStateOf(product.quantity) }
            OrderItem(
                product,
                onIncrease = {
                    quantity
                },
                onDecrease = {

                }
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}
