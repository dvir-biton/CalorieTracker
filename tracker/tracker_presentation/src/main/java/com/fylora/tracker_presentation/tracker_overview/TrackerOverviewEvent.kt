package com.fylora.tracker_presentation.tracker_overview

import com.fylora.tracker_domain.model.TrackedFood

sealed interface TrackerOverviewEvent {
    object OnNextDayClick: TrackerOverviewEvent
    object OnPreviousDayClick: TrackerOverviewEvent
    data class OnToggleMealClick(val meal: Meal): TrackerOverviewEvent
    data class DeleteTrackedFoodClick(val trackedFood: TrackedFood): TrackerOverviewEvent
}