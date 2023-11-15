package com.farfun.mealz.ui.screens.categoryDetail

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.farfun.mealz.ui.composables.CategoryListItem
import com.farfun.mealz.ui.composables.CenteredCircularLoader

@Composable
fun CategoryDetail(
    categoryDetailViewModel: CategoryDetailViewModel,
    onItemClick: (navParam: String) -> Unit
) {
    val categoryDetails by categoryDetailViewModel.categoryDetails.collectAsState()
    val loadingState by categoryDetailViewModel.loadingState.collectAsState()
    val errorMessage by categoryDetailViewModel.errorMessage.collectAsState()

    if (errorMessage.isNotEmpty()) {
        Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_LONG).show()
        categoryDetailViewModel.clearErrorMessage()
    }

    if (!loadingState) {
        LazyColumn {
            items(categoryDetails) { mealCategory ->
                CategoryListItem(
                    title = mealCategory.strMeal,
                    description = mealCategory.strMealCategory,
                    image = mealCategory.strMealThumb,
                    onItemClick = fun (navParam: String) {onItemClick(navParam)}
                )
            }
        }
    } else {
        CenteredCircularLoader()
    }
}