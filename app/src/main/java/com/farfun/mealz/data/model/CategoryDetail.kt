package com.farfun.mealz.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("category_details")
data class CategoryDetail(
    @PrimaryKey(autoGenerate = false)
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    var strMealCategory: String
)
