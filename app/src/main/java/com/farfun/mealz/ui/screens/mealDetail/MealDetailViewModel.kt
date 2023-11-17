package com.farfun.mealz.ui.screens.mealDetail

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farfun.mealz.data.model.MealDetail
import com.farfun.mealz.repository.interfaces.MealsRepository
import com.farfun.mealz.util.RequestResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val repository: MealsRepository,
    private val savedStateHandle: SavedStateHandle,
    private val context: Application
) : ViewModel() {
    private val _mealDetail = MutableStateFlow<MealDetail?>(null)
    val mealDetail: StateFlow<MealDetail?>
        get() = _mealDetail

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean>
        get() = _loadingState

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String>
        get() = _errorMessage

    private val _ingredientList: MutableList<String> = mutableListOf()
    val ingredientList: List<String>
        get() = _ingredientList

    private val _measureList: MutableList<String> = mutableListOf()
    val measureList: List<String>
        get() = _measureList

    private fun clearErrorMessage() {
        _errorMessage.tryEmit("")
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        clearErrorMessage()
    }

    init {
        getMealDetails()
    }

    private fun generateListFromProperty(mealDetail: MealDetail) {
        val mealClass = mealDetail::class.java
        for (i in 1..20) {
            val ingredientPropertyName = mealClass.getDeclaredField("strIngredient$i")
            val measurePropertyName = mealClass.getDeclaredField("strMeasure$i")
            val capitalizedIngredientPropertyName = ingredientPropertyName.name
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            val capitalizedMeasurePropertyName = measurePropertyName.name
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

            val ingredientData = mealClass.getMethod("get$capitalizedIngredientPropertyName").invoke(mealDetail) as String?
            val measureData = mealClass.getMethod("get$capitalizedMeasurePropertyName").invoke(mealDetail) as String?

            if (!ingredientData.isNullOrEmpty()) _ingredientList.add(ingredientData) else _ingredientList.add("")
            if (!measureData.isNullOrEmpty()) _measureList.add(measureData) else _measureList.add("")
        }
    }

    private fun getMealDetails() = viewModelScope.launch(Dispatchers.IO) {
        val navParam: String = checkNotNull(savedStateHandle["mealId"])
        repository.getMealDetails(navParam)
            .catch { Log.e("Meals Repo Error", it.message.toString()) }
            .collect {
                when (it) {
                    is RequestResource.Failure -> {
                        _errorMessage.tryEmit(it.message)
                        _loadingState.tryEmit(false)
                    }
                    is RequestResource.Progress -> {
                        _loadingState.tryEmit(true)
                    }
                    is RequestResource.Success -> {
                        _loadingState.tryEmit(false)
                        _mealDetail.tryEmit(it.data)
                        generateListFromProperty(it.data)
                    }
                }
            }
    }
}