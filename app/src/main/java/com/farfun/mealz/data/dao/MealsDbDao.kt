package com.farfun.mealz.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.farfun.mealz.data.model.MealCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface MealsDbDao {
    @Query("SELECT * from meal_categories")
    fun getAllCategories(): Flow<List<MealCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(mealCategories: List<MealCategory>)
}