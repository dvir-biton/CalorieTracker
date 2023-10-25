package com.fylora.tracker_domain.use_case

import com.fylora.tracker_domain.model.TrackableFood
import com.fylora.tracker_domain.model.TrackedFood
import com.fylora.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DeleteTrackedFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(food: TrackedFood) {
        repository.deleteTrackedFood(food)
    }
}