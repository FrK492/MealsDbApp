package com.farfun.mealz.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_categories")
data class MealCategory(
    @PrimaryKey(autoGenerate = false)
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)
