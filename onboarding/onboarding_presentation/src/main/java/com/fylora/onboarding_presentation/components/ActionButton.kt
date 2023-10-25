package com.fylora.onboarding_presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fylora.core_ui.LocalSpacing
import com.fylora.core_ui.fontFamily

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.button,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(15.dp),
        onClick = onClick,
    ) {
        Text(
            text = text,
            style = textStyle,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(LocalSpacing.current.spaceSmall),
            fontFamily = fontFamily,
            fontWeight = FontWeight.ExtraBold
        )
    }
}