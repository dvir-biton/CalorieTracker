package com.fylora.tracker_data.mapper

import com.fylora.tracker_data.remote.dto.Product
import com.fylora.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun Product.toTrackableFood(): TrackableFood? {
    val carbsPer100g = nutriments.carbohydrates100g.roundToInt()
    val proteinPer100g = nutriments.proteins100g.roundToInt()
    val fatPer100g = nutriments.fat100g.roundToInt()
    val caloriesPer100 = nutriments.energyKca100g.roundToInt()



    return TrackableFood(
        name = productName ?: return null,
        imageUrl = imageFrontThumbUrl,
        caloriesPer100g = caloriesPer100,
        carbsPer100g = carbsPer100g,
        proteinPer100g = proteinPer100g,
        fatPer100g = fatPer100g,

    )
}