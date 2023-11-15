package com.farfun.mealz.remote

import com.farfun.mealz.data.model.CategoryDetails
import com.farfun.mealz.data.model.MealCategories
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {
    @GET("categories.php")
    suspend fun getAllCategories(): MealCategories

    @GET("filter.php")
    suspend fun getCategoryDetails(@Query("c") categoryName: String): CategoryDetails
}