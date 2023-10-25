package com.fylora.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.core.R
import com.fylora.core.util.UiEvent
import com.fylora.core.util.UiText
import com.fylora.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
): ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.OnAmountForFoodChange -> {
                state = state.copy(
                    trackableFood = state.trackableFood.map {
                        if(it.food == event.food) {
                            val amount = if(event.amount.isBlank()) {
                                ""
                            }
                            else if(event.amount.isDigitsOnly()) {
                                event.amount
                            } else it.amount

                            it.copy(amount = amount)
                        } else it
                    }
                )
            }
            is SearchEvent.OnQueryChange -> {
                state = state.copy(
                    query = event.query
                )
            }
            SearchEvent.OnSearch -> {
                executeSearch()
            }
            is SearchEvent.OnSearchFocusChange -> {
                state = state.copy(
                    isHintVisible = !event.isFocused && state.query.isBlank()
                )
            }
            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFood = state.trackableFood.map {
                        if(it.food == event.food) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
            is SearchEvent.OnTrackFoodClick -> {
                trackFood(event)
            }
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            val query = state.query
            state = state.copy(
                isSearching = true,
                trackableFood = emptyList(),
                query = ""
            )
            trackerUseCases
                .searchFood(query)
                .onSuccess { foods ->
                    state = state.copy(
                        trackableFood = foods.map {
                            TrackableFoodUiState(it)
                        },
                        isSearching = false,
                    )
                }
                .onFailure {
                    state = state.copy(isSearching = false)
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                                UiText.StringResource(R.string.error_something_went_wrong
                            )
                        )
                    )
                }
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) {
        viewModelScope.launch {
            val uiState = state.trackableFood.find { it.food == event.food }
            trackerUseCases.trackFood(
                food = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}