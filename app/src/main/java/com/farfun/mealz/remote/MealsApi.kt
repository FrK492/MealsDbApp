package com.farfun.mealz.remote

import com.farfun.mealz.data.model.MealCategories
import retrofit2.http.GET

interface MealsApi {
    @GET("categories.php")
    suspend fun getAllCategories(): MealCategories
}