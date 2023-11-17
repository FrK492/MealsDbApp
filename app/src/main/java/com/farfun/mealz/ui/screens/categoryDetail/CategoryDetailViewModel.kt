package com.farfun.mealz.ui.screens.categoryDetail

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farfun.mealz.data.model.CategoryDetail
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
class CategoryDetailViewModel @Inject constructor(
    private val repository: MealsRepository,
    private val savedStateHandle: SavedStateHandle,
    private val context: Application
): ViewModel() {
    private val _categoryDetails = MutableStateFlow<List<CategoryDetail>>(listOf())
    private val _loadingState = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow("")
    val categoryDetails: StateFlow<List<CategoryDetail>>
        get() = _categoryDetails
    val loadingState: StateFlow<Boolean>
        get() = _loadingState
    val errorMessage: StateFlow<String>
        get() = _errorMessage

    init {
        getCategoryDetails()
    }

    private fun clearErrorMessage() {
        _errorMessage.tryEmit("")
    }

    fun showErrorMessage(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        clearErrorMessage()
    }

    private fun getCategoryDetails() = viewModelScope.launch(Dispatchers.IO) {
        val navParam: String = checkNotNull(savedStateHandle["categoryName"])
        val response = repository.getCategoryDetails(navParam)
        response
            .catch { Log.e("Meals Repo Error", it.message.toString()) }
            .collect {
                when (it) {
                    is RequestResource.Failure -> {
                        _loadingState.tryEmit(false)
                        _errorMessage.tryEmit(it.message)
                    }
                    is RequestResource.Progress -> {
                        _loadingState.tryEmit(true)
                    }
                    is RequestResource.Success -> {
                        _loadingState.tryEmit(false)
                        _categoryDetails.tryEmit(it.data)
                    }
                }
            }
    }
}