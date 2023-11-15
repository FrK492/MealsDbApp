package com.farfun.mealz.ui.screens.categoryList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farfun.mealz.data.model.MealCategory
import com.farfun.mealz.repository.interfaces.MealsRepository
import com.farfun.mealz.util.RequestResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val mealsRepository: MealsRepository
): ViewModel() {
    private val _mealCategories = MutableStateFlow<List<MealCategory>>(listOf())
    private var _loadingState = MutableStateFlow(false)
    private var _errorMessage = MutableStateFlow("")

    val mealCategories: StateFlow<List<MealCategory>>
        get() = _mealCategories
    val loadingState: StateFlow<Boolean>
        get() = _loadingState
    val errorMessage: StateFlow<String>
        get() = _errorMessage

    init {
        getAllCategories()
    }

    fun clearError() {
        _errorMessage.tryEmit("")
    }

    private fun getAllCategories() = viewModelScope.launch(Dispatchers.IO) {
        val response = mealsRepository.getAllCategories()
        response
            .catch { Log.e("Meals Repo Error", it.message.toString()) }
            .collect {
                when (it) {
                    is RequestResource.Success -> {
                        val categoryData = it.data
                        _mealCategories.tryEmit(categoryData)
                        _loadingState.tryEmit(false)
                    }
                    is RequestResource.Failure -> {
                        _loadingState.tryEmit(false)
                        Log.i("error repo", it.message)
                        _errorMessage.tryEmit(it.message)
                    }
                    is RequestResource.Progress -> {
                        _loadingState.tryEmit(true)
                    }
                }
            }
    }
}