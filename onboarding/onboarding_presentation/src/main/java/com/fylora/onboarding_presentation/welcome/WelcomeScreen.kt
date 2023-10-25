package com.fylora.onboarding_presentation.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.fylora.core.R
import com.fylora.core_ui.LocalSpacing
import com.fylora.core_ui.fontFamily
import com.fylora.onboarding_presentation.components.ActionButton

@Composable
fun WelcomeScreen(
    onNextClick: () -> Unit
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        ActionButton(
            text = stringResource(id = R.string.next),
            modifier = Modifier.align(CenterHorizontally),
            onClick = { onNextClick() }
        )
    }
}