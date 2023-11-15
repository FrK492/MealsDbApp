package com.farfun.mealz.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.farfun.mealz.data.dao.MealsDbDao
import com.farfun.mealz.data.model.CategoryDetail
import com.farfun.mealz.data.model.MealCategory

@Database(entities = [MealCategory::class, CategoryDetail::class], version = 1, exportSchema = false)
abstract class MealsDb: RoomDatabase() {
    abstract fun mealsDbDao(): MealsDbDao
}