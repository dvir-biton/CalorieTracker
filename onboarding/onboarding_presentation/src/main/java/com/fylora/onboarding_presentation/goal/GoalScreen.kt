package com.fylora.onboarding_presentation.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.core.R
import com.fylora.core.domain.model.GoalType
import com.fylora.core.util.UiEvent
import com.fylora.core_ui.LocalSpacing
import com.fylora.core_ui.fontFamily
import com.fylora.onboarding_presentation.components.ActionButton
import com.fylora.onboarding_presentation.components.SelectableButton

@Composable
fun GoalScreen(
    viewModel: GoalViewModel = hiltViewModel(),
    onNextClick: () -> Unit,
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Success -> onNextClick()
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
            text = stringResource(id = R.string.lose_keep_or_gain_weight),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(
                LocalSpacing.current.spaceMedium
            )
        ){
            SelectableButton(
                text = stringResource(id = R.string.lose),
                isSelected = viewModel.selectedGoal is GoalType.LoseWeight,
                color = MaterialTheme.colors.primaryVariant,
                selectedTextColor = Color.White,
                onClick = {
                    viewModel.onGoalTypeChange(GoalType.LoseWeight)
                }
            )
            SelectableButton(
                text = stringResource(id = R.string.keep),
                isSelected = viewModel.selectedGoal is GoalType.KeepWeight,
                color = MaterialTheme.colors.primaryVariant,
                selectedTextColor = Color.White,
                onClick = {
                    viewModel.onGoalTypeChange(GoalType.KeepWeight)
                }
            )
            SelectableButton(
                text = stringResource(id = R.string.gain),
                isSelected = viewModel.selectedGoal is GoalType.GainWeight,
                color = MaterialTheme.colors.primaryVariant,
                selectedTextColor = Color.White,
                onClick = {
                    viewModel.onGoalTypeChange(GoalType.GainWeight)
                }
            )
        }

        ActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick
        )
    }
}