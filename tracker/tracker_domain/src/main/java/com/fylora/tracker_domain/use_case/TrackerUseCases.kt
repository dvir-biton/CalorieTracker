package com.fylora.tracker_domain.use_case

data class TrackerUseCases(
    val trackFood: TrackFood,
    val searchFood: SearchFood,
    val getFoodForDate: GetFoodForDate,
    val deleteTrackedFood: DeleteTrackedFood,
    val calculateMealNutrients: CalculateMealNutrients
)
