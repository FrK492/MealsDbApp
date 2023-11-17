package com.farfun.mealz.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farfun.mealz.ui.screens.categoryDetail.CategoryDetail
import com.farfun.mealz.ui.screens.categoryDetail.CategoryDetailViewModel
import com.farfun.mealz.ui.screens.categoryList.CategoryList
import com.farfun.mealz.ui.screens.categoryList.CategoryListViewModel
import com.farfun.mealz.ui.screens.mealDetail.MealDetail
import com.farfun.mealz.ui.screens.mealDetail.MealDetailViewModel

@Composable
fun BaseNavigation(
    startDestination: String = "categoryList"
) {
    val navController = rememberNavController()
    Scaffold {
        NavHost(navController, startDestination, modifier = Modifier.padding(it)) {
            composable("categoryList") {
                val categoryListViewModel = hiltViewModel<CategoryListViewModel>()
                CategoryList(categoryListViewModel) { _, paramTitle ->
                    navController.navigate("category/$paramTitle")
                }
            }
            composable("category/{categoryName}") {
                val categoryDetailViewModel = hiltViewModel<CategoryDetailViewModel>()
                CategoryDetail(categoryDetailViewModel) { paramId, _ ->
                    navController.navigate("meal/$paramId")
                }
            }
            composable("meal/{mealId}") {
                val mealDetailViewModel = hiltViewModel<MealDetailViewModel>()
                MealDetail(mealDetailViewModel)
            }
        }
    }
}