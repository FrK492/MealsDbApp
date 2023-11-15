package com.farfun.mealz.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.farfun.mealz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListItem(
    title: String,
    description: String,
    image: String,
    onItemClick: () -> Unit
) {
   Card(
       modifier = Modifier
           .fillMaxWidth()
           .height(120.dp)
           .padding(horizontal = 10.dp, vertical = 5.dp),
       onClick = onItemClick,
       elevation = CardDefaults.cardElevation(
           defaultElevation = 10.dp
       )
   ) {
       Surface(
           modifier = Modifier.fillMaxSize()
       ) {
           Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
           ) {
               AsyncImage(
                   model = ImageRequest.Builder(LocalContext.current)
                       .data(image)
                       .crossfade(true)
                       .build(),
                   placeholder = painterResource(id = R.drawable.placeholder),
                   contentDescription = null,
                   modifier = Modifier
                       .size(height = 70.dp, width = 70.dp)
                       .clip(RoundedCornerShape(70.dp))
               )
               Column(
                   modifier = Modifier.padding(start = 15.dp)
               ) {
                   Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                   Text(text = description, maxLines = 4, overflow = TextOverflow.Ellipsis)
               }
           }
       }
   }
}

@Preview
@Composable
fun CategoryListItemPreview() {
    CategoryListItem(
        title = "Beef",
        description = "Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit ametLorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet",
        image = "https://www.themealdb.com/images/category/beef.png") {

    }
}