package com.fylora.onboarding_presentation.gender

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.core.R
import com.fylora.core.util.UiEvent
import com.fylora.core_ui.LocalSpacing
import com.fylora.core_ui.fontFamily
import com.fylora.onboarding_presentation.components.ActionButton

@Composable
fun GenderScreen(
    onNextClick: () -> Unit,
    viewModel: GenderViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Success -> {
                    onNextClick()
                }
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalSpacing.current.spaceLarge),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.align(Alignment.TopCenter),
            text = stringResource(id = R.string.whats_your_gender),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold
        )

        GenderPicker(
            modifier = Modifier.fillMaxSize(),
            onGenderSelected = { gender ->
                viewModel.onGenderClick(gender)
            }
        )

        ActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick
        )
    }
}