package com.fylora.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import com.fylora.core_ui.CarbColor
import com.fylora.core_ui.FatColor
import com.fylora.core_ui.ProteinColor

@Composable
fun NutrientsBar(
    carbs: Int,
    protein: Int,
    fat: Int,
    calories: Int,
    calorieGoal: Int,
    modifier: Modifier = Modifier
) {
    val background = MaterialTheme.colors.background
    val caloriesExceedColor = MaterialTheme.colors.error
    val carbWidthRatio = remember {
        Animatable(0f)
    }
    val proteinWidthRatio = remember {
        Animatable(0f)
    }
    val fatWidthRatio = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = carbs) {
        carbWidthRatio.animateTo(
            targetValue = (carbs * 4f) / calorieGoal
        )
    }
    LaunchedEffect(key1 = protein) {
        proteinWidthRatio.animateTo(
            targetValue = (protein * 4f) / calorieGoal
        )
    }
    LaunchedEffect(key1 = fat) {
        fatWidthRatio.animateTo(
            targetValue = (fat * 9f) / calorieGoal
        )
    }

    Canvas(modifier = modifier) {
        if(calories <= calorieGoal) {
            val carbsWidth = carbWidthRatio.value * size.width
            val proteinWidth = proteinWidthRatio.value * size.width
            val fatWidth = fatWidthRatio.value * size.width

            val clipPath = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            offset = Offset.Zero,
                            size = size
                        ),
                        cornerRadius = CornerRadius(100f)
                    )
                )
            }

            // whole bar
            drawRoundRect(
                color = background,
                size = size,
                cornerRadius = CornerRadius(100f)
            )

            clipPath(clipPath) {
                // fat bar
                drawRoundRect(
                    color = FatColor,
                    size = Size(
                        width = carbsWidth + proteinWidth + fatWidth,
                        height = size.height
                    ),
                    cornerRadius = CornerRadius(100f)
                )

                // protein bar
                drawRoundRect(
                    color = ProteinColor,
                    size = Size(
                        width = carbsWidth + proteinWidth,
                        height = size.height
                    ),
                    cornerRadius = CornerRadius(100f)
                )

                // carbs bar
                drawRoundRect(
                    color = CarbColor,
                    size = Size(
                        width = carbsWidth,
                        height = size.height
                    ),
                    cornerRadius = CornerRadius(100f)
                )
            }

        } else {
            drawRoundRect(
                color = caloriesExceedColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
        }
    }
}