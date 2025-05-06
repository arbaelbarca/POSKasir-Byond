package com.arbaelbarca.posfantastic.ui.presentation.ui.screen.items

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerEffect() {
    val transition = rememberInfiniteTransition()
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1500, easing = LinearOutSlowInEasing),
            RepeatMode.Reverse
        ),
    )

    val shimmer = Color(0xFFC3C3C3)

    val shimmerColors = listOf(
        shimmer.copy(alpha = 0.3f),
        shimmer.copy(alpha = 0.5f),
        shimmer.copy(alpha = 1.0f),
        shimmer.copy(alpha = 0.5f),
        shimmer.copy(alpha = 0.3f),
    )

    val brush by remember {
        derivedStateOf {
            Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(translateAnimation, translateAnimation),
                end = Offset(translateAnimation + 100f, translateAnimation + 100f),
                tileMode = TileMode.Mirror,
            )
        }
    }


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(10.dp),
        userScrollEnabled = false
    ) {
        repeat(30) {
            item {
                CardShimmerEffectProduct(brush)
            }
        }
    }
}

@Composable
fun CardShimmerEffect(brush: Brush) {
    Column {
        Card(
            colors = CardDefaults.cardColors(Color.White),
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 5.dp, end = 5.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            onClick = {

            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .background(brush, RoundedCornerShape(10))
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .fillMaxHeight()
                            .background(brush, RoundedCornerShape(10))
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .fillMaxHeight()
                            .background(brush, RoundedCornerShape(10))
                    )
                }


            }
        }

    }
}

@Composable
fun CardShimmerEffectProduct(brush: Brush) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .border(
                width = 0.dp,
                color = Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Placeholder image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(brush, RoundedCornerShape(10))
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
                    .background(brush, RoundedCornerShape(10))
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(brush, RoundedCornerShape(10))
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }

}
