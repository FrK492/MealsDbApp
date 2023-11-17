package com.farfun.mealz.ui.screens.categoryList

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farfun.mealz.ui.composables.CategoryListItem
import com.farfun.mealz.ui.composables.CenteredCircularLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryList(
    categoryListViewModel: CategoryListViewModel,
    onItemClick: (paramId: String, paramTitle: String) -> Unit
) {
    val categories by categoryListViewModel.mealCategories.collectAsState()
    val errorMessage by categoryListViewModel.errorMessage.collectAsState()
    val loadingState by categoryListViewModel.loadingState.collectAsState()
    val bottomSheetText by categoryListViewModel.bottomSheetText.collectAsState()

    if (bottomSheetText.isNotBlank()) {
        ModalBottomSheet(onDismissRequest = { categoryListViewModel.clearBottomSheet() }) {
            Text(text = bottomSheetText, modifier = Modifier.padding(20.dp))
        }
    }

    if (errorMessage.isNotEmpty()) {
        categoryListViewModel.showErrorMessage(errorMessage)
    }

    if (!loadingState) {
        LazyColumn {
            items(categories) { mealCategory ->
                CategoryListItem(
                    id = mealCategory.idCategory,
                    title = mealCategory.strCategory,
                    description = mealCategory.strCategoryDescription,
                    image = mealCategory.strCategoryThumb,
                    onItemClick = {paramId, paramTitle -> onItemClick(paramId, paramTitle) },
                    onItemLongPress = { bottomSheetText -> categoryListViewModel.onItemLongPress(bottomSheetText) }
                )
            }
        }
    } else {
        CenteredCircularLoader()
    }
}