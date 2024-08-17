package com.example.newsreaderapp.presentation.ui.news_list

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsreaderapp.R
import com.example.newsreaderapp.data.sources.remote.model.ArticleDto
import com.example.newsreaderapp.domain.model.Article
import com.example.newsreaderapp.presentation.ui.theme.Graay

@Composable
fun NewsList(
    modifier: Modifier,
    newsListViewModel: NewsListViewModel = hiltViewModel()
) {
    val state = newsListViewModel.newsPagingDataFlow.collectAsLazyPagingItems()

    // val state = newsListViewModel.state.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(count = state.itemCount, key = state.itemKey { it }) { index ->
                state[index]?.let { article ->
                    NewsCard(article = article)
                }
            }
//        if (state.value.isLoading) {
//            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }

        }

    }
}

@Composable
fun NewsCard(modifier: Modifier = Modifier, article: Article) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        val matrix = ColorMatrix()
        matrix.setToSaturation(0F)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Graay),
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = article.urlToImage)
                    .placeholder(R.drawable.newspaper)
                    .build(),
                contentDescription = stringResource(R.string.article_image),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                colorFilter = ColorFilter.colorMatrix(matrix),
                error = painterResource(id = R.drawable.baseline_newspaper_24)
            )
            Log.e("image", article.urlToImage.toString())
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = article.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            if (!article.description.isNullOrEmpty()) Text(text = article.description)
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                ) {
                Text(text = article.publishedAt, fontSize = 12.sp, color = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = article.source)

            }

        }

    }

}