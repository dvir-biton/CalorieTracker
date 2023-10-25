package com.fylora.onboarding_presentation.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.core.domain.model.ActivityLevel
import com.fylora.core.domain.preferences.Preferences
import com.fylora.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val preferences: Preferences
): ViewModel() {

    var selectedActivity by mutableStateOf<ActivityLevel>(
        ActivityLevel.Medium
    )
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onActivityLevelChange(activityLevel: ActivityLevel) {
        selectedActivity = activityLevel
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveActivityLevel(selectedActivity)
            _uiEvent.send(UiEvent.Success)
        }
    }
}