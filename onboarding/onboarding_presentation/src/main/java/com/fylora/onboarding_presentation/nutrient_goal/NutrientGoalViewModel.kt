package com.fylora.onboarding_presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.core.domain.preferences.Preferences
import com.fylora.core.util.UiEvent
import com.fylora.onboarding_domain.use_case.ValidateNutrients
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val validateNutrients: ValidateNutrients
): ViewModel() {

    var state by mutableStateOf(NutrientGoalState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when(event) {
            is NutrientGoalEvent.OnCarbRatioEnter -> {
                state = state.copy(
                    carbsRatio = filter(event.ratio) ?: return
                )
            }
            is NutrientGoalEvent.OnFatRatioEnter -> {
                state = state.copy(
                    fatRatio = filter(event.ratio) ?: return
                )
            }
            is NutrientGoalEvent.OnProteinRatioEnter -> {
                state = state.copy(
                    proteinRatio = filter(event.ratio) ?: return
                )
            }
            NutrientGoalEvent.OnNextClick -> {
                val result = validateNutrients(
                    carbsRatioText = state.carbsRatio,
                    proteinRatioText = state.proteinRatio,
                    fatRatioText = state.fatRatio
                )
                when(result) {
                    is ValidateNutrients.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackbar(result.message))
                        }
                    }
                    is ValidateNutrients.Result.Success -> {
                        preferences.saveCarbRatio(result.carbsRatio)
                        preferences.saveProteinRatio(result.proteinRatio)
                        preferences.saveFatRatio(result.fatRatio)

                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.Success)
                        }
                    }
                }
            }
        }
    }

    private fun filter(string: String): String? {
        if(string.isBlank()) {
            return ""
        }
        else if(string.isDigitsOnly() && string.toInt() <= MAX_RATIO) {
            return string
        }

        return null
    }

    companion object {
        const val MAX_RATIO = 100
    }
}