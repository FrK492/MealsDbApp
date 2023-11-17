package com.farfun.mealz.repository.interfaces

import com.farfun.mealz.data.model.CategoryDetail
import com.farfun.mealz.data.model.MealCategory
import com.farfun.mealz.data.model.MealDetail
import com.farfun.mealz.util.RequestResource
import kotlinx.coroutines.flow.Flow

interface MealsRepository {
    suspend fun getAllCategories(): Flow<RequestResource<List<MealCategory>>>

    suspend fun getCategoryDetails(categoryName: String): Flow<RequestResource<List<CategoryDetail>>>

    suspend fun getMealDetails(mealId: String): Flow<RequestResource<MealDetail>>
}