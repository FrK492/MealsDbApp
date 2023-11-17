package com.farfun.mealz.ui.screens.categoryList

import android.app.Application
import android.util.Log
import android.widget.Toast
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
    private val mealsRepository: MealsRepository,
    private val context: Application
): ViewModel() {
    private val _mealCategories = MutableStateFlow<List<MealCategory>>(listOf())
    private val _loadingState = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow("")
    private val _bottomSheetText = MutableStateFlow("")

    val mealCategories: StateFlow<List<MealCategory>>
        get() = _mealCategories
    val loadingState: StateFlow<Boolean>
        get() = _loadingState
    val errorMessage: StateFlow<String>
        get() = _errorMessage
    val bottomSheetText: StateFlow<String>
        get() = _bottomSheetText

    init {
        getAllCategories()
    }

    private fun clearError() {
        _errorMessage.tryEmit("")
    }

    fun showErrorMessage(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        clearError()
    }

    fun onItemLongPress(bottomSheetText: String) {
        _bottomSheetText.tryEmit(bottomSheetText)
    }

    fun clearBottomSheet() {
        _bottomSheetText.tryEmit("")
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
                        _errorMessage.tryEmit(it.message)
                    }
                    is RequestResource.Progress -> {
                        _loadingState.tryEmit(true)
                    }
                }
            }
    }
}