package com.arbaelbarca.posfantastic.ui.presentation.ui.screen.success

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.arbaelbarca.posfantastic.R

@Composable
fun SuccesPaymentScreen(
    navController: NavController,
    onShareInvoice: () -> Unit,
    onPrintInvoice: () -> Unit
) {
    val backgroundColor = Color(0xFF3E4EB8) // biru latar belakang
    val buttonShareColor = Color(0xFF87A8E3)
    val whiteColor = Color.White

    Scaffold(
        containerColor = Color.White,
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    // Gambar high five
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        GifImageSuccess()
                    }

                    // Teks sukses
                    Text(
                        text = "Assiikk Berhasil payment niih....",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = whiteColor,
                    )

                    // Tombol-tombol
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = onShareInvoice,
                            colors = ButtonDefaults.buttonColors(containerColor = buttonShareColor),
                            shape = RoundedCornerShape(50),
                        ) {
                            Text(text = "Share E-Invoice", color = whiteColor)
                        }

                        Button(
                            onClick = onPrintInvoice,
                            colors = ButtonDefaults.buttonColors(containerColor = whiteColor),
                            shape = RoundedCornerShape(50),
                        ) {
                            Text(text = "Cetak Invoice", color = backgroundColor)
                        }
                    }
                }
            }
        }

    }

}


@Composable
fun SuccessPaymentAnim() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_success)) // file di folder res/raw
    var isAnimationStarted by remember { mutableStateOf(false) }

    val animateProgress by animateLottieCompositionAsState(
        composition = composition,
        iterations = if (isAnimationStarted) 1 else 0,
        isPlaying = isAnimationStarted
    )


    // Jalankan animasi setelah composition siap
    LaunchedEffect(composition) {
        if (composition != null) {
            isAnimationStarted = true
        }
    }

    // Callback ketika animasi selesai
    LaunchedEffect(animateProgress) {
        if (animateProgress == 1f) {

        }
    }

    LottieAnimation(
        composition = composition,
        progress = { animateProgress }
    )
}

@Composable
fun GifImageSuccess() {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            // Gunakan decoder yang sesuai untuk versi Android
            if (android.os.Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data("android.resource://com.arbaelbarca.posfantastic/raw/anim_successgif")
            .build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = "GIF",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}