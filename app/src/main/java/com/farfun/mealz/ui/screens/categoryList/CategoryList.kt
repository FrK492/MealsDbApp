package com.farfun.mealz.ui.screens.categoryList

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
fun CategoryList(
    categoryListViewModel: CategoryListViewModel,
    onItemClick: () -> Unit
) {
    val categories by categoryListViewModel.mealCategories.collectAsState()
    val errorMessage by categoryListViewModel.errorMessage.collectAsState()
    val loadingState by categoryListViewModel.loadingState.collectAsState()

    if (errorMessage.isNotEmpty()) {
        Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_LONG).show()
        categoryListViewModel.clearError()
    }

    if (!loadingState) {
        LazyColumn {
            items(categories) { mealCategory ->
                CategoryListItem(
                    title = mealCategory.strCategory,
                    description = mealCategory.strCategoryDescription,
                    image = mealCategory.strCategoryThumb,
                    onItemClick = onItemClick
                )
            }
        }
    } else {
        CenteredCircularLoader()
    }
}