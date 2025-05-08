package com.arbaelbarca.posfantastic.ui.presentation.ui.screen.product

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.arbaelbarca.posfantastic.ui.model.request.AddProductRequest
import com.arbaelbarca.posfantastic.ui.model.response.CategoriesResponseModel
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.items.LoadingOverlay
import com.arbaelbarca.posfantastic.ui.viewmodel.ProductViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File

@Composable
fun AddProductScreen(
    navController: NavController,
    onBackClick: () -> Unit,
    onPredictClick: () -> Unit,
    onSaveClick: () -> Unit
) {

    val purpleBlue = Color(0xff2594b3)
    val textFieldBg = Color.White
    val background = Color(0xFFF6F7FB)

    val namaProduk = remember { mutableStateOf("") }
    val jumlahProduk = remember { mutableStateOf("0") }
    val totalHarga = remember { mutableStateOf("0") }
    val hargaJual = remember { mutableStateOf("0") }

    val kategoriList = listOf("Elektronik", "Pakaian", "Makanan")
    val jenisList = listOf("Retail", "Grosir")

    var selectedKategori = remember { mutableStateOf("") }
    var selectedJenis = remember { mutableStateOf("") }

    var kategoriExpanded by remember { mutableStateOf(false) }
    var jenisExpanded by remember { mutableStateOf(false) }

    var isShowPickerDialog by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }


    val isLoadingAdd = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val isShowCategories = remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = uri
        }
    }

    val productViewModel = hiltViewModel<ProductViewModel>()
    val stateAddProduct = productViewModel.stateAddProduct.collectAsState()
    val stateCategoriesList = productViewModel.stateCategories.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(WindowInsets.statusBars.asPaddingValues())

    ) {
        Column(
            modifier = Modifier
                .padding(25.dp)
        ) {
            // Upload box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .border(1.dp, purpleBlue, RoundedCornerShape(12.dp))
                    .padding(8.dp)
                    .clickable {
                        isLoadingAdd.value = false
                        isShowPickerDialog = true
                    },
                contentAlignment = Alignment.Center,
            ) {
                if (imageUri == null)
                    Icon(Icons.Default.Add, contentDescription = "Add Image", tint = purpleBlue)
                else Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Nama Produk
            OutlinedTextFieldWithClear(
                label = "Nama Produk",
                value = namaProduk.value,
                onValueChange = { namaProduk.value = it },
                onClear = { namaProduk.value = "" }
            )

            // Jumlah Produk
            SimpleTextField("Jumlah Produk", jumlahProduk.value) { jumlahProduk.value = it }

            // Total Harga Beli
            SimpleTextField("Total Harga Beli", totalHarga.value) { totalHarga.value = it }

            // Kategori Produk
            ExposedDropdownField(
                label = "Kategori Produk",
                items = kategoriList,
                selectedValue = selectedKategori,
                onClick = {
                    isLoadingAdd.value = true
                    productViewModel.fetchCategoriesList()
                }
            )

            if (isLoadingAdd.value) {
                LoadingOverlay(isLoadingAdd.value)
            }

//            // Jenis Produk
//            ExposedDropdownField(
//                label = "Jenis Produk",
//                expanded = jenisExpanded,
//                selectedValue = selectedJenis,
//                onExpandChange = { jenisExpanded = it },
//                onValueChange = {
//                    selectedJenis = it
//                    jenisExpanded = false
//                },
//                items = jenisList,
//                isDisable = isDisable
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))

            // Harga Jual + AI button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SimpleTextField(
                    label = "Harga Jual",
                    value = hargaJual.value,
                    modifier = Modifier.weight(1f),
                    onValueChange = { hargaJual.value = it }
                )

                Button(
                    onClick = onPredictClick,
                    colors = ButtonDefaults.buttonColors(containerColor = purpleBlue),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Prediksi Harga Ai", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Simpan button
            Button(
                onClick = {
                    scope.launch {
                        isLoadingAdd.value = true
                        val addProductRequest = AddProductRequest(
                            1,
                            "",
                            hargaJual.value.toDouble(),
                            namaProduk.value,
                            totalHarga.value.toDouble(),
                            jumlahProduk.value.toInt()
                        )

                        productViewModel.fetchAddProduct(addProductRequest)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = purpleBlue),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("Simpan", color = Color.White)
            }
        }

        CameraGalleryPickerDialog(
            isShowPickerDialog,
            onDismiss = {
                isShowPickerDialog = false
            },
            onImagePicked = { uri ->
                imageUri = uri
            }
        )

        RequestCameraPermission(
            onGranted = {
                isShowPickerDialog = false
            },
            onDenied = {
                isShowPickerDialog = false
            }
        )

        LoadingOverlay(isLoadingAdd.value)
        initObserverAddProduct(
            stateAddProduct,
            isLoading = isLoadingAdd
        )

        initObserverCategories(
            stateCategoriesList,
            isLoading = isLoadingAdd,
            isShowDialog = isShowCategories,
            selectedKategori = selectedKategori
        )
    }
}

@Composable
private fun initObserverCategories(
    stateCategoriesList: State<UiState<List<CategoriesResponseModel>>>,
    isLoading: MutableState<Boolean>,
    isShowDialog: MutableState<Boolean>,
    selectedKategori: MutableState<String>
) {
    when (val uiState = stateCategoriesList.value) {
        is UiState.Error -> {
            isLoading.value = false
        }

        is UiState.Loading -> {
            isLoading.value = true
        }

        is UiState.Success -> {
            isLoading.value = false
            isShowDialog.value = true
        }
    }

    if (isShowDialog.value) {
        ShowDialogCategories(
            title = "Pilih Kategori",
            items = listOf("Elektronik", "Pakaian", "Makanan"),
            onItemSelected = {
                isShowDialog.value = false
                selectedKategori.value = it
            },
            onDismissRequest = {
                isShowDialog.value = false
            }
        )
    }
}

@Composable
fun CameraImagePicker(
    resultImageUri: (Uri) -> Unit
) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        // Foto berhasil diambil
        if (success) {
            // imageUri.value sudah diisi
        }
    }

    val fileUri = remember {
        val file = File(context.cacheDir, "photo_pos.jpg")
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            imageUri.value = fileUri
            cameraLauncher.launch(fileUri)
            resultImageUri(fileUri)
        }) {
            Text("Ambil Foto dari Kamera")
        }

        Spacer(modifier = Modifier.height(16.dp))

        imageUri.value?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Foto dari Kamera",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun initObserverAddProduct(
    stateProduct: State<UiState<JSONObject>>,
    isLoading: MutableState<Boolean>
) {

    val navController = rememberNavController()

    val context = LocalContext.current

    when (val uiState = stateProduct.value) {
        is UiState.Error -> {
            isLoading.value = false
        }

        is UiState.Loading -> {
            isLoading.value = true
        }

        is UiState.Success -> {
            isLoading.value = false

            LaunchedEffect(Unit) {
                Toast.makeText(context, "Success Add Product ", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }

        }
    }
}

@Composable
fun CameraGalleryPickerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onImagePicked: (Uri) -> Unit
) {
    val context = LocalContext.current

    // File kosong untuk kamera
    val cameraUri = remember {
        val file = File(context.cacheDir, "photo_pos.jpg")
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { onImagePicked(it) }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            onImagePicked(cameraUri)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Ambil Gambar") },
            text = { Text("Pilih dulu mau dari mana?") },
            confirmButton = {
                TextButton(onClick = {
                    onDismiss()
                    cameraLauncher.launch(cameraUri)
                }) {
                    Text("Kamera")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                    galleryLauncher.launch("image/*")
                }) {
                    Text("Galeri")
                }
            }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraPermission(
    onGranted: () -> Unit,
    onDenied: () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    when {
        cameraPermissionState.status.isGranted -> {
            onGranted()
        }

        !cameraPermissionState.status.shouldShowRationale -> {
            // First time request or "Don't ask again"
            onDenied()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color(0xFF3E4EB8)) },
        modifier = modifier
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF3E4EB8),
            focusedTextColor = Color.White,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldWithClear(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color(0xFF3E4EB8)) },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = onClear) {
                    Icon(Icons.Default.Close, contentDescription = "Clear")
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF3E4EB8),
            focusedTextColor = Color.White,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownField(
    label: String,
    selectedValue: MutableState<String>,
    items: List<String>,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        OutlinedTextField(
            value = selectedValue.value,
            onValueChange = {},
            label = { Text(label, color = Color(0xFF3E4EB8)) },
            readOnly = true,
            enabled = false,
            trailingIcon = {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3E4EB8),
                focusedTextColor = Color.White,
                unfocusedBorderColor = Color.Gray
            )
        )
    }

}

@Composable
fun ShowDialogCategories(
    title: String,
    items: List<String>,
    onItemSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LazyColumn {
                    items(items) { item ->
                        Text(
                            text = item,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onItemSelected(item)
                                }
                                .padding(vertical = 12.dp)
                        )
                    }
                }
            }
        }
    }
}

