package com.farfun.mealz.ui.screens.categoryDetail

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.farfun.mealz.ui.composables.CategoryListItem
import com.farfun.mealz.ui.composables.CenteredCircularLoader

@Composable
fun CategoryDetail(
    categoryDetailViewModel: CategoryDetailViewModel,
    onItemClick: (paramId: String, paramTitle: String) -> Unit
) {
    val categoryDetails by categoryDetailViewModel.categoryDetails.collectAsState()
    val loadingState by categoryDetailViewModel.loadingState.collectAsState()
    val errorMessage by categoryDetailViewModel.errorMessage.collectAsState()

    if (errorMessage.isNotEmpty()) {
        categoryDetailViewModel.showErrorMessage(errorMessage)
    }

    if (!loadingState) {
        LazyColumn {
            items(categoryDetails) { mealCategory ->
                CategoryListItem(
                    id = mealCategory.idMeal,
                    title = mealCategory.strMeal,
                    description = mealCategory.strMealCategory,
                    image = mealCategory.strMealThumb,
                    onItemClick = fun (paramId: String, paramTitle: String) {onItemClick(paramId, paramTitle)},
                    onItemLongPress = {}
                )
            }
        }
    } else {
        CenteredCircularLoader()
    }
}