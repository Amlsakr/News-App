package com.example.newsreaderapp.presentation.ui.news_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.newsreaderapp.domain.model.Article

@Composable
fun ArticleDetails(modifir :Modifier , article: Article){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = article.title, fontWeight = FontWeight.Bold , color = Color.Black , fontSize = 20.sp)

    }


}