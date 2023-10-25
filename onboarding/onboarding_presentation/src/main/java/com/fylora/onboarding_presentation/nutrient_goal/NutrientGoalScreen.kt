package com.fylora.onboarding_presentation.nutrient_goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.core.R
import com.fylora.core.util.UiEvent
import com.fylora.core_ui.LocalSpacing
import com.fylora.core_ui.fontFamily
import com.fylora.onboarding_presentation.components.ActionButton
import com.fylora.onboarding_presentation.components.UnitTextField

@Composable
fun NutrientGoalScreen(
    scaffoldState: ScaffoldState,
    viewModel: NutrientGoalViewModel = hiltViewModel(),
    onNextClick: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Success -> onNextClick()
                is UiEvent.ShowSnackbar -> scaffoldState.snackbarHostState.showSnackbar(
                    message = event.message.asString(context)
                )
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalSpacing.current.spaceLarge),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.align(Alignment.TopCenter),
            text = stringResource(id = R.string.what_are_your_nutrient_goals),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UnitTextField(
                value = viewModel.state.carbsRatio,
                onValueChange = {
                    viewModel.onEvent(NutrientGoalEvent.OnCarbRatioEnter(it))
                },
                unit = stringResource(id = R.string.percent_carbs)
            )

            UnitTextField(
                value = viewModel.state.proteinRatio,
                onValueChange = {
                    viewModel.onEvent(NutrientGoalEvent.OnProteinRatioEnter(it))
                },
                unit = stringResource(id = R.string.percent_proteins)
            )

            UnitTextField(
                value = viewModel.state.fatRatio,
                onValueChange = {
                    viewModel.onEvent(NutrientGoalEvent.OnFatRatioEnter(it))
                },
                unit = stringResource(id = R.string.percent_fats)
            )
        }

        ActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = stringResource(id = R.string.next),
            onClick = {
                viewModel.onEvent(NutrientGoalEvent.OnNextClick)
            }
        )
    }
}