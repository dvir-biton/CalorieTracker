package com.fylora.tracker_domain.use_case

import com.fylora.tracker_domain.model.TrackableFood
import com.fylora.tracker_domain.model.TrackedFood
import com.fylora.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodForDate(
    private val repository: TrackerRepository
) {

    operator fun invoke(date: LocalDate): Flow<List<TrackedFood>> {
        return repository.getFoodForDate(date)
    }
}