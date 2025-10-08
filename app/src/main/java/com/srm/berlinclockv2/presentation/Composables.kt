package com.srm.berlinclockv2.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Light(
    colorCode: Char,
    modifier: Modifier = Modifier,
    isSquare: Boolean = false
) {
    val lightColor = colorCode.toColor()
    val shape = if (isSquare) RoundedCornerShape(4.dp) else CircleShape

    Box(
        modifier = modifier
            .padding(2.dp)
            .aspectRatio(if (isSquare) 4f else 1f)
            .background(lightColor, shape)
            .height(if (isSquare) 20.dp else 32.dp)
    )
}

private fun Char.toColor(): Color {
    return when (this) {
        'R' -> Color(0xFFC62828)
        'Y' -> Color(0xFFFFEB3B)
        else -> Color(0xFF424242)
    }
}