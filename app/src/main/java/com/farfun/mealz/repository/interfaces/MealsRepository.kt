package com.farfun.mealz.repository.interfaces

import com.farfun.mealz.data.model.MealCategory
import com.farfun.mealz.util.RequestResource
import kotlinx.coroutines.flow.Flow

interface MealsRepository {
    suspend fun getAllCategories(): Flow<RequestResource<List<MealCategory>>>
}