//package com.arbaelbarca.posfantastic.ui.presentation.ui.screen.product
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material.icons.filled.KeyboardArrowDown
//import androidx.compose.material.icons.filled.KeyboardArrowUp
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//
//@Composable
//fun AddProductScreen(
//    navController: NavController,
//    onBackClick: () -> Unit,
//    onPredictClick: () -> Unit,
//    onSaveClick: () -> Unit
//) {
//    val purpleBlue = Color(0xff2594b3)
//    val textFieldBg = Color.White
//    val background = Color(0xFFF6F7FB)
//
//    val namaProduk = remember { mutableStateOf("") }
//    val jumlahProduk = remember { mutableStateOf("") }
//    val totalHarga = remember { mutableStateOf("") }
//    val hargaJual = remember { mutableStateOf("") }
//
//    val kategoriList = listOf("Elektronik", "Pakaian", "Makanan")
//    val jenisList = listOf("Retail", "Grosir")
//
//    var selectedKategori by remember { mutableStateOf("") }
//    var selectedJenis by remember { mutableStateOf("") }
//
//    var kategoriExpanded by remember { mutableStateOf(false) }
//    var jenisExpanded by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(background)
//            .padding(16.dp)
//    ) {
//        // Back icon
//        IconButton(onClick = onBackClick) {
//            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Upload box
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(180.dp)
//                .border(1.dp, purpleBlue, RoundedCornerShape(12.dp))
//                .padding(8.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(Icons.Default.Add, contentDescription = "Add Image", tint = purpleBlue)
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Nama Produk
//        OutlinedTextFieldWithClear(
//            label = "Nama Produk",
//            value = namaProduk.value,
//            onValueChange = { namaProduk.value = it },
//            onClear = { namaProduk.value = "" }
//        )
//
//        // Jumlah Produk
//        SimpleTextField("Jumlah Produk", jumlahProduk.value) { jumlahProduk.value = it }
//
//        // Total Harga Beli
//        SimpleTextField("Total Harga Beli", totalHarga.value) { totalHarga.value = it }
//
//        // Kategori Produk
//        ExposedDropdownField(
//            label = "Kategori Produk",
//            expanded = kategoriExpanded,
//            selectedValue = selectedKategori,
//            onExpandChange = { kategoriExpanded = it },
//            onValueChange = {
//                selectedKategori = it
//                kategoriExpanded = false
//            },
//            items = kategoriList
//        )
//
//        // Jenis Produk
//        ExposedDropdownField(
//            label = "Jenis Produk",
//            expanded = jenisExpanded,
//            selectedValue = selectedJenis,
//            onExpandChange = { jenisExpanded = it },
//            onValueChange = {
//                selectedJenis = it
//                jenisExpanded = false
//            },
//            items = jenisList
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        // Harga Jual + AI button
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            SimpleTextField(
//                label = "Harga Jual",
//                value = hargaJual.value,
//                modifier = Modifier.weight(1f),
//                onValueChange = { hargaJual.value = it }
//            )
//
//            Button(
//                onClick = onPredictClick,
//                colors = ButtonDefaults.buttonColors(containerColor = purpleBlue),
//                shape = RoundedCornerShape(50)
//            ) {
//                Text("Prediksi Harga Ai", color = Color.White)
//            }
//        }
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        // Simpan button
//        Button(
//            onClick = onSaveClick,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = purpleBlue),
//            shape = RoundedCornerShape(25.dp)
//        ) {
//            Text("Simpan", color = Color.White)
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SimpleTextField(
//    label: String,
//    value: String,
//    modifier: Modifier = Modifier,
//    onValueChange: (String) -> Unit
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(label, color = Color(0xFF3E4EB8)) },
//        modifier = modifier
//            .fillMaxWidth(),
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            containerColor = Color.White,
//            focusedBorderColor = Color(0xFF3E4EB8),
//            unfocusedBorderColor = Color.Gray
//        )
//    )
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun OutlinedTextFieldWithClear(
//    label: String,
//    value: String,
//    onValueChange: (String) -> Unit,
//    onClear: () -> Unit
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(label, color = Color(0xFF3E4EB8)) },
//        trailingIcon = {
//            if (value.isNotEmpty()) {
//                IconButton(onClick = onClear) {
//                    Icon(Icons.Default.Close, contentDescription = "Clear")
//                }
//            }
//        },
//        modifier = Modifier.fillMaxWidth(),
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            containerColor = Color.White,
//            focusedBorderColor = Color(0xFF3E4EB8),
//            unfocusedBorderColor = Color.Gray
//        )
//    )
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ExposedDropdownField(
//    label: String,
//    expanded: Boolean,
//    selectedValue: String,
//    onExpandChange: (Boolean) -> Unit,
//    onValueChange: (String) -> Unit,
//    items: List<String>
//) {
//    Box {
//        OutlinedTextField(
//            value = selectedValue,
//            onValueChange = {},
//            label = { Text(label, color = Color(0xFF3E4EB8)) },
//            readOnly = true,
//            trailingIcon = {
//                Icon(
//                    if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                    contentDescription = null
//                )
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { onExpandChange(!expanded) },
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                containerColor = Color.White,
//                focusedBorderColor = Color(0xFF3E4EB8),
//                unfocusedBorderColor = Color.Gray
//            )
//        )
//
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { onExpandChange(false) }
//        ) {
//            items.forEach {
//                DropdownMenuItem(
//                    text = { Text(it) },
//                    onClick = { onValueChange(it) }
//                )
//            }
//        }
//    }
//}
//
