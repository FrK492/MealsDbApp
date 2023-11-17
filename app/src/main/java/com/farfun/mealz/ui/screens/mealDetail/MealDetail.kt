package com.farfun.mealz.ui.screens.mealDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.farfun.mealz.ui.composables.CenteredCircularLoader

@Composable
fun MealDetail(
    mealDetailViewModel: MealDetailViewModel
) {
    val loadingState by mealDetailViewModel.loadingState.collectAsState()
    val errorMessage by mealDetailViewModel.errorMessage.collectAsState()
    val mealDetail by mealDetailViewModel.mealDetail.collectAsState()
    val ingredientList = mealDetailViewModel.ingredientList
    val measureList = mealDetailViewModel.measureList
    
    val scrollState = rememberScrollState()

    if (errorMessage.isNotEmpty()) {
        mealDetailViewModel.showToast(errorMessage)
    }

    if (!loadingState) {
        mealDetail?.let { meal ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(meal.strMealThumb)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = meal.strMeal,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
                meal.strInstructions?.let {
                    Box(
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Intructions",
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.padding(bottom = 5.dp)
                            )
                            Text(
                                text = meal.strInstructions,
                                fontSize = 12.sp,
                                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                            )
                        }
                    }
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .border(
                        BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(15.dp)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text ="Ingredients & Measures",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        for (index in 0..19) {
                            if (!measureList.getOrNull(index).isNullOrEmpty() || !ingredientList.getOrNull(index).isNullOrEmpty())
                                Text(
                                    text = measureList.getOrElse(index) {""} + " " + ingredientList.getOrElse(index) {""},
                                    fontSize = 12.sp,
                                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                                )
                        }
                    }
                }
            }
        }
    } else {
        CenteredCircularLoader()
    }
}