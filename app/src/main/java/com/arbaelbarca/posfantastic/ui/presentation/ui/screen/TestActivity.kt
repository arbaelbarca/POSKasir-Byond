package com.arbaelbarca.posfantastic.ui.presentation.ui.screen

import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutInfo
import androidx.compose.ui.viewinterop.AndroidView
import com.arbaelbarca.posfantastic.databinding.LayoutTestActivityBinding

@Composable
fun TestScreen() {
    AndroidView(
        factory = { context ->
            val infalter = LayoutInflater.from(context)
            val binding = LayoutTestActivityBinding.inflate(infalter)
            binding.tvTest.text = "ARBAAAAell"
            binding.root
        }
    )
}