package com.arbaelbarca.posfantastic.ui.presentation.ui.screen.product

import android.net.Uri
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.arbaelbarca.posfantastic.ui.model.request.AddProductRequest
import com.arbaelbarca.posfantastic.ui.model.response.ProductResponseModel
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.home.initObserverProduct
import com.arbaelbarca.posfantastic.ui.presentation.ui.screen.items.LoadingOverlay
import com.arbaelbarca.posfantastic.ui.viewmodel.ProductViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.CoroutineScope
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
    val jumlahProduk = remember { mutableStateOf("") }
    val totalHarga = remember { mutableStateOf("") }
    val hargaJual = remember { mutableStateOf("") }

    val kategoriList = listOf("Elektronik", "Pakaian", "Makanan")
    val jenisList = listOf("Retail", "Grosir")

    var selectedKategori by remember { mutableStateOf("") }
    var selectedJenis by remember { mutableStateOf("") }

    var kategoriExpanded by remember { mutableStateOf(false) }
    var jenisExpanded by remember { mutableStateOf(false) }

    var isShowPickerDialog by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val productViewModel = hiltViewModel<ProductViewModel>()
    val stateAddProduct = productViewModel.stateAddProduct.collectAsState()

    val isLoadingAdd = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = uri
        }
    }



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
                expanded = kategoriExpanded,
                selectedValue = selectedKategori,
                onExpandChange = { kategoriExpanded = it },
                onValueChange = {
                    selectedKategori = it
                    kategoriExpanded = false
                },
                items = kategoriList
            )

            // Jenis Produk
            ExposedDropdownField(
                label = "Jenis Produk",
                expanded = jenisExpanded,
                selectedValue = selectedJenis,
                onExpandChange = { jenisExpanded = it },
                onValueChange = {
                    selectedJenis = it
                    jenisExpanded = false
                },
                items = jenisList
            )

            Spacer(modifier = Modifier.height(12.dp))

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
                        val addProductRequest = AddProductRequest(
                            1,
                            "",
                            totalHarga.toString().toDouble(),
                            namaProduk.toString(),
                            totalHarga.toString().toDouble(),
                            jumlahProduk.toString().toInt()
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
        initObserverAddProduct(stateAddProduct, isLoading = isLoadingAdd)
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


fun addProductApi(
    productViewModel: ProductViewModel,
    addProductRequest: AddProductRequest
) {
}

@Composable
fun initObserverAddProduct(
    stateProduct: State<UiState<JSONObject>>,
    isLoading: MutableState<Boolean>
) {
    when (val uiState = stateProduct.value) {
        is UiState.Error -> {
            isLoading.value = false
        }

        is UiState.Loading -> {
            isLoading.value = true
        }

        is UiState.Success -> {
            isLoading.value = false
            val getDataUser = uiState.data
            println("respon Data Product $getDataUser")
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
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,
            focusedBorderColor = Color(0xFF3E4EB8),
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
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,
            focusedBorderColor = Color(0xFF3E4EB8),
            unfocusedBorderColor = Color.Gray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownField(
    label: String,
    expanded: Boolean,
    selectedValue: String,
    onExpandChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    items: List<String>
) {
    Box {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            label = { Text(label, color = Color(0xFF3E4EB8)) },
            readOnly = true,
            trailingIcon = {
                Icon(
                    if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpandChange(!expanded) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = Color(0xFF3E4EB8),
                unfocusedBorderColor = Color.Gray
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandChange(false) }
        ) {
            items.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = { onValueChange(it) }
                )
            }
        }
    }
}

