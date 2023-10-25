package com.fylora.onboarding_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fylora.core_ui.LocalSpacing
import com.fylora.core_ui.fontFamily

@Composable
fun SelectableButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    color: Color,
    selectedTextColor: Color,
    textStyle: TextStyle = MaterialTheme.typography.button,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .border(
                width = 2.dp,
                color = color,
                shape = RoundedCornerShape(100.dp)
            )
            .background(
                color = if(isSelected) color else Color.Transparent,
                shape = RoundedCornerShape(100.dp)
            )
            .clickable {
                onClick()
            }
            .padding(LocalSpacing.current.spaceSmall),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            color = if(isSelected) selectedTextColor else color,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(LocalSpacing.current.spaceSmall)
        )
    }
}