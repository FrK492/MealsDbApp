package com.farfun.mealz.repository.implementations

import com.farfun.mealz.data.dao.MealsDbDao
import com.farfun.mealz.data.model.CategoryDetail
import com.farfun.mealz.data.model.MealCategory
import com.farfun.mealz.remote.MealsApi
import com.farfun.mealz.repository.interfaces.MealsRepository
import com.farfun.mealz.util.RequestResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val api: MealsApi,
    private val dao: MealsDbDao
): MealsRepository {
    override suspend fun getAllCategories(): Flow<RequestResource<List<MealCategory>>> {
        return flow {
            emit(RequestResource.loading())
            try {
                val apiResponse = api.getAllCategories()
                if (apiResponse.categories.isNotEmpty()) {
                    dao.insertCategories(apiResponse.categories)
                }
            } catch (ex: Exception) {
                emit(RequestResource.failure(ex.message.toString()))
            } finally {
                dao.getAllCategories().collect {
                    emit(RequestResource.success(it))
                }
            }
        }
    }

    override suspend fun getCategoryDetails(categoryName: String): Flow<RequestResource<List<CategoryDetail>>> {
        return flow {
            emit(RequestResource.loading())
            try {
                val apiResponse = api.getCategoryDetails(categoryName)
                if (apiResponse.meals.isNotEmpty()) {
                    val injectedCategoryDetails = apiResponse.meals.onEach {
                        it.strMealCategory = categoryName
                    }

                    dao.insertCategoryDetails(injectedCategoryDetails)
                }
            } catch (ex: Exception) {
                emit(RequestResource.failure(ex.message.toString()))
            } finally {
                dao.getCategoryDetail(categoryName).collect {
                    emit(RequestResource.success(it))
                }
            }
        }
    }
}