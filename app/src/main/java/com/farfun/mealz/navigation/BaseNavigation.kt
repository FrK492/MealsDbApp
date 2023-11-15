package com.farfun.mealz.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farfun.mealz.ui.screens.categoryList.CategoryList
import com.farfun.mealz.ui.screens.categoryList.CategoryListViewModel

@Composable
fun BaseNavigation(
    startDestination: String = "categoryList"
) {
    val navController = rememberNavController()
    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            NavHost(navController, startDestination) {
                composable("categoryList") {
                    val categoryListViewModel = hiltViewModel<CategoryListViewModel>()
                    CategoryList(categoryListViewModel) {

                    }
                }
            }
        }
    }
}