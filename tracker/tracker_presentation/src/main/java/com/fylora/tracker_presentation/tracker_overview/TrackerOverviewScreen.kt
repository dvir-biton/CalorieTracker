package com.fylora.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.fylora.core.util.UiEvent
import com.fylora.core_ui.LocalSpacing
import com.fylora.tracker_presentation.tracker_overview.components.AddButton
import com.fylora.tracker_presentation.tracker_overview.components.DaySelector
import com.fylora.tracker_presentation.tracker_overview.components.ExpandableMeal
import com.fylora.tracker_presentation.tracker_overview.components.NutrientsHeader
import com.fylora.tracker_presentation.tracker_overview.components.TrackedFoodItem

@Composable
fun TrackerOverviewScreen(
    onNavigateToSearch: (String, Int, Int, Int) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        NutrientsHeader(state = state)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = spacing.spaceMedium)
        ) {
            item {
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                DaySelector(
                    date = state.date,
                    onPreviousDayClick = {
                        viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick)
                    },
                    onNextDayClick = {
                        viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick)
                    },
                    modifier = Modifier.padding(horizontal = spacing.spaceMedium)
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
            }
            items(state.meals) { meal ->
                ExpandableMeal(
                    meal = meal,
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = spacing.spaceSmall)
                        ){
                            AddButton(
                                text = stringResource(
                                    id = com.fylora.core.R.string.add_meal,
                                    meal.name.asString(context)
                                ),
                                onClick = {
                                    onNavigateToSearch(
                                        meal.name.asString(context),
                                        state.date.dayOfMonth,
                                        state.date.monthValue,
                                        state.date.year,
                                    )
                                },
                                modifier = Modifier
                                    .padding(horizontal = spacing.spaceMedium)
                                    .fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                            val foods = state.trackedFoods.filter {
                                it.mealType == meal.mealType
                            }
                            foods.forEach { food ->
                                TrackedFoodItem(
                                    trackedFood = food,
                                    onDeleteClick = {
                                        viewModel.onEvent(
                                            TrackerOverviewEvent
                                                .DeleteTrackedFoodClick(food)
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                            }
                        }
                    },
                    onToggleClick = {
                        viewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(meal))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}