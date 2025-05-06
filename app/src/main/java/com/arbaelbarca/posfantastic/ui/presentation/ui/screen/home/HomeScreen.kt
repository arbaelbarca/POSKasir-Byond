package com.arbaelbarca.posfantastic.ui.presentation.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arbaelbarca.posfantastic.R
import com.arbaelbarca.posfantastic.ui.model.response.UsersResponse
import com.arbaelbarca.posfantastic.ui.presentation.navigation.ObjectRouteScreen
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.items.ShimmerEffect
import com.arbaelbarca.posfantastic.ui.presentation.viewmodel.UsersViewModel
import kotlinx.coroutines.delay

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val userViewModel = hiltViewModel<UsersViewModel>()
    val stateUsers = userViewModel.stateUsers.collectAsState()
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Home") }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
//            when (val uiState = stateUsers.value) {
//                is UiState.Error -> {
//
//                }
//
//                is UiState.Loading -> {
//
//                }
//
//                is UiState.Success -> {
//                    val getDataUser = uiState.data
//                    ShowListUsersScreen(getDataUser)
//                }
//            }


            SearchBarItems()
            CategoryListItems()

            LaunchedEffect(Unit) {
                delay(3000)
                isLoading = false
            }

            if (isLoading) {
                ShimmerEffect()
            } else ProductListScreen(
                onClickItem = {
                    navController.navigate(ObjectRouteScreen.DetailProductScreenRoute.route)
                }
            )

        }
    }

    // ✅ Trigger fetch once when Composable is first composed
//    LaunchedEffect(Unit) {
//        userViewModel.fetchDataUsers()
//    }
}

@Composable
fun ShowListUsersScreen(listUserItem: List<UsersResponse.UsersResponseItem>) {
    LazyColumn {
        items(listUserItem.size) { index ->
            val item = listUserItem[index]
            Text(text = item.name.toString())
        }
    }
}

@Composable
fun SearchBarItems(modifier: Modifier = Modifier) {
    // Search bar
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Hinted search text") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White),
        shape = RoundedCornerShape(24.dp)
    )
}

@Composable
fun CategoryListItems() {
    val labels = listOf("Label", "Label", "Label", "Label", "Label", "Label")
    // Filter Chips
    LazyRow(contentPadding = PaddingValues(horizontal = 12.dp)) {
        items(labels) { label ->
            val isSelected = label == labels.first()
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .background(
                        if (isSelected) Color.Black else Color.White,
                        RoundedCornerShape(16.dp)
                    )
                    .border(
                        1.dp,
                        if (isSelected) Color.Black else Color.Gray,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = label,
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }
    }

}

@Composable
fun ProductListScreen(
    onClickItem: () -> Unit
) {
    val productList = List(10) { index -> index }
    val selectedCounts = remember { mutableStateMapOf<Int, Int>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F4FA))
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        // Product Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(8.dp),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(productList) { index ->
                val count = selectedCounts[index] ?: 0
                val isSelected = count > 0

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .border(
                            width = if (isSelected) 2.dp else 0.dp,
                            color = if (isSelected) Color(0xFF3B82F6) else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    onClick = onClickItem

                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Placeholder image
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .background(Color.LightGray, RoundedCornerShape(8.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.no_product_not_found),
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentDescription = null,
                                alignment = Alignment.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Title maksimal 2 lines",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(text = "Rp 10.000", fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(4.dp))

                        // Counter button
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isSelected) {
                                IconButton(onClick = {
                                    val current = selectedCounts[index] ?: 0
                                    if (current > 1) {
                                        selectedCounts[index] = current - 1
                                    } else {
                                        selectedCounts.remove(index)
                                    }
                                }) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_minus),
                                        modifier = Modifier.size(25.dp),
                                        contentDescription = "remove"
                                    )
                                }

                                Text(text = "$count")

                                IconButton(onClick = {
                                    selectedCounts[index] = count + 1
                                }) {
                                    Icon(Icons.Default.AddCircle, contentDescription = null)
                                }
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = {
                                    selectedCounts[index] = 1
                                }) {
                                    Icon(Icons.Default.AddCircle, contentDescription = null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
