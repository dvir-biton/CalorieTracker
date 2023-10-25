package com.fylora.tracker_presentation.tracker_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fylora.core.domain.preferences.Preferences
import com.fylora.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases,
): ViewModel() {

    var state by mutableStateOf(TrackerOverviewState())
        private set

    private var getFoodsForDateJob: Job? = null

    init {
        refreshFoods()
        preferences.saveShouldShowOnBoarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when(event) {
            is TrackerOverviewEvent.DeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    trackerUseCases.deleteTrackedFood(event.trackedFood)
                    refreshFoods()
                }
            }
            TrackerOverviewEvent.OnNextDayClick -> {
                state = state.copy(
                    date = state.date.plusDays(1)
                )
                refreshFoods()
            }
            TrackerOverviewEvent.OnPreviousDayClick -> {
                state = state.copy(
                    date = state.date.minusDays(1)
                )
                refreshFoods()
            }
            is TrackerOverviewEvent.OnToggleMealClick -> {
                state = state.copy(
                    meals = state.meals.map { meal ->
                        if(meal.name == event.meal.name) {
                            meal.copy(
                                isExpanded = !meal.isExpanded
                            )
                        } else meal
                    }
                )
            }
        }
    }

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()

        getFoodsForDateJob = trackerUseCases
            .getFoodForDate(state.date)
            .onEach { foods ->
                val nutrients = trackerUseCases.calculateMealNutrients(foods)
                state = state.copy(
                    totalCarbs = nutrients.totalCarbs,
                    totalProtein = nutrients.totalProtein,
                    totalFat = nutrients.totalFat,
                    totalCalories = nutrients.totalCalories,
                    carbsGoal = nutrients.carbsGoal,
                    proteinGoal = nutrients.proteinGoal,
                    fatGoal = nutrients.fatGoal,
                    caloriesGoal = nutrients.caloriesGoal,
                    trackedFoods = foods,
                    meals = state.meals.map {
                        val nutrientsForMeal =
                            nutrients.mealNutrients[it.mealType]
                                ?: return@map it.copy(
                                    carbs = 0,
                                    protein = 0,
                                    fat = 0,
                                    calories = 0,
                                )
                        it.copy(
                            carbs = nutrientsForMeal.carbs,
                            protein = nutrientsForMeal.protein,
                            fat = nutrientsForMeal.fat,
                            calories = nutrientsForMeal.calories,
                        )
                    }
                )
            }.launchIn(viewModelScope)
    }
}