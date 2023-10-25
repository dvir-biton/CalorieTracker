package com.fylora.tracker_presentation.search

data class SearchState(
    val query: String = "",
    val isHintVisible: Boolean = true,
    val isSearching: Boolean = false,
    val trackableFood: List<TrackableFoodUiState> = emptyList(),
)
