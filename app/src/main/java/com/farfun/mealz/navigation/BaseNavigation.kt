package com.farfun.mealz.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.farfun.mealz.ui.screens.categoryDetail.CategoryDetail
import com.farfun.mealz.ui.screens.categoryDetail.CategoryDetailViewModel
import com.farfun.mealz.ui.screens.categoryList.CategoryList
import com.farfun.mealz.ui.screens.categoryList.CategoryListViewModel
import com.farfun.mealz.ui.screens.mealDetail.MealDetail
import com.farfun.mealz.ui.screens.mealDetail.MealDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseNavigation(
    startDestination: String = "categoryList"
) {
    val navController = rememberNavController()
    val currentNavState by navController.currentBackStackEntryAsState()
    val navbarTitle = remember { mutableStateOf("") }

    fun changeNavbarTitle(title: String) {
        navbarTitle.value = title
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = navbarTitle.value) },
                navigationIcon = {
                    if (currentNavState?.destination?.route !== "categoryList") {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Outlined.KeyboardArrowLeft,
                                contentDescription = ""
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) {
        NavHost(navController, startDestination, modifier = Modifier.padding(it)) {
            composable("categoryList") {
                val categoryListViewModel = hiltViewModel<CategoryListViewModel>()
                changeNavbarTitle("Categories")
                CategoryList(categoryListViewModel) { _, paramTitle ->
                    navController.navigate("category/$paramTitle")
                }
            }
            composable("category/{categoryName}") {
                val categoryDetailViewModel = hiltViewModel<CategoryDetailViewModel>()
                changeNavbarTitle("Meals")
                CategoryDetail(categoryDetailViewModel) { paramId, _ ->
                    navController.navigate("meal/$paramId")
                }
            }
            composable("meal/{mealId}") {
                val mealDetailViewModel = hiltViewModel<MealDetailViewModel>()
                changeNavbarTitle("Meal Detail")
                MealDetail(mealDetailViewModel)
            }
        }
    }
}