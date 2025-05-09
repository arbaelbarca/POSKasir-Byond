package com.arbaelbarca.posfantastic.ui.presentation.ui.screen.home

import android.annotation.SuppressLint
import android.widget.AdapterView.OnItemSelectedListener
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddBusiness
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arbaelbarca.posfantastic.R
import com.arbaelbarca.posfantastic.ui.model.response.CategoryItem
import com.arbaelbarca.posfantastic.ui.model.response.ProductsResponse
import com.arbaelbarca.posfantastic.ui.model.response.UsersResponse
import com.arbaelbarca.posfantastic.ui.presentation.navigation.ObjectRouteScreen
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.items.LoadingOverlay
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.items.ShimmerEffect
import com.arbaelbarca.posfantastic.ui.viewmodel.ProductViewModel
import com.arbaelbarca.posfantastic.ui.viewmodel.UsersViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, productViewModel: ProductViewModel) {
    val userViewModel = hiltViewModel<UsersViewModel>()
    val stateProduct = productViewModel.stateProduct.collectAsState()

    val stateUsers = userViewModel.stateUsers.collectAsState()
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }
    var isLoadingAdd = remember { mutableStateOf(false) }
    val selectedProducts = remember { mutableStateMapOf<Int, ProductsResponse.ProductItem>() }
    val isCartNotEmpty by remember { derivedStateOf { selectedProducts.isNotEmpty() } }

    Scaffold(
        containerColor = colorResource(R.color.cornflower_blue_50),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.cornflower_blue_50)
                ),
                title = { Text("Home") },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(ObjectRouteScreen.AddProductScreenRoute.route)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AddBusiness,
                            tint = colorResource(R.color.cornflower_blue_600),
                            contentDescription = "Add Product",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(visible = isCartNotEmpty) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(ObjectRouteScreen.DetailProductScreenRoute.route)
                    },
                    containerColor = colorResource(R.color.cornflower_blue_600),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp),
                ) {
                    Text(
                        text = "Lanjutkan Pemesanan",
                        color = Color.White
                    )
                }
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            SearchBarItems()
            CategoryListItems(
                listOf(CategoryItem(1, "asjjdk"), CategoryItem(2, "asjjdk")), { category ->
                    //todo viewmodel hit category
                })

            LaunchedEffect(Unit) {
                delay(3000)
                isLoading = false
            }

            if (isLoading) {
                ShimmerEffect()
            } else {
                val dummyProductList = List(10) { index ->
                    ProductsResponse.ProductItem(
                        id = index + 1,
                        name = "Product ${index + 1}",
                        stock = 10 + index,
                        initialPrice = 1.0 + index,
                        sellingPrice = 10.0 + index,
                        categoryName = "Category ${(index % 3) + 1}",
                        createdAt = "2025-05-07T08:36:05.254704",
                        updatedAt = "2025-05-07T08:36:05.254784",
                        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e8/Pecel_Hariadhi.JPG/500px-Pecel_Hariadhi.JPG",
                        quantity = 0
                    )
                }

                ProductListScreen(
                    dummyProductList,
                    onClickItem = { product ->
                        if (product.quantity > 0) selectedProducts[product.id!!] = product
                        else selectedProducts.remove(product.id)
                        productViewModel.addSelectedProducts(selectedProducts.values.toList())
                    })
            }


        }

        LoadingOverlay(isLoadingAdd.value)
    }

    // ✅ Trigger fetch once when Composable is first composed
    LaunchedEffect(Unit) {
        productViewModel.fetchDataProductList()
    }

    initObserverProduct(stateProduct)
}


@Composable
fun initObserverProduct(stateProduct: State<UiState<List<ProductsResponse>>>) {
    when (val uiState = stateProduct.value) {
        is UiState.Error -> {

        }

        is UiState.Loading -> {

        }

        is UiState.Success -> {
            val getDataUser = uiState.data
            println("respon Data Product $getDataUser")

        }
    }
}

fun addPageScreen(
    scope: CoroutineScope, isLoading: MutableState<Boolean>, navController: NavController
) {
    scope.launch {
        isLoading.value = true
        delay(1500)
        isLoading.value = false
        navController.navigate(ObjectRouteScreen.AddProductScreenRoute.route)
    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarItems(modifier: Modifier = Modifier) {
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()
    val inputField = @Composable {
        SearchBarDefaults.InputField(
            modifier = Modifier.fillMaxWidth(),
            searchBarState = searchBarState,
            textFieldState = textFieldState,
            onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
            placeholder = { Text("Search...") },
            trailingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
        )
    }
    // Search bar
    SearchBar(
        state = searchBarState,
        inputField = inputField,
        colors = SearchBarDefaults.colors(colorResource(R.color.white))
    )
}

@Composable
fun CategoryListItems(
    categories: List<CategoryItem>, itemSelectedListener: (category: CategoryItem) -> Unit
) {
    // Filter Chips
    var selectedId by remember { mutableStateOf<Long>(0) }

    LazyRow(modifier = Modifier.padding(top = 16.dp)) {
        items(categories) { category ->
            AssistChip(
                modifier = Modifier.padding(horizontal = 8.dp), onClick = {
                    selectedId = category.id
                    itemSelectedListener(category)
                }, label = { Text(category.name) }, colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (selectedId == category.id) colorResource(id = R.color.cornflower_blue_600)
                    else colorResource(id = R.color.white),
                    labelColor = if (selectedId == category.id) colorResource(id = R.color.white)
                    else Color.Black
                ), border = BorderStroke(
                    width = if (selectedId == category.id) 0.dp else 1.5.dp,
                    color = Color.LightGray,
                )
            )
        }
    }
}

@Composable
fun ProductListScreen(
    products: List<ProductsResponse.ProductItem>,
    onClickItem: (product: ProductsResponse.ProductItem) -> Unit
) {
    val selectedCounts = remember { mutableStateMapOf<Int, Int>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F4FA))
    ) {

//        Spacer(modifier = Modifier.height(8.dp))

        // Product Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(),
        ) {
            items(products) { product ->

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(colorResource(R.color.white)),
                ) {
                    Column(
                        modifier = Modifier
                            .padding()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Placeholder image
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
//                                .background(Color.LightGray, RoundedCornerShape(8.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.no_product_not_found),
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = null,
                                alignment = Alignment.Center,
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = product.name ?: "",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = (product.sellingPrice ?: 0.0).toString(),
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Counter button

                        var count by remember { mutableStateOf(0) }
                        val visible = count > 0

                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.End),
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
                                            onClickItem.invoke(product.copy(quantity = count))
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
                                    onClickItem.invoke(product.copy(quantity = count))
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
            }
        }
    }
}
