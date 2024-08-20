package com.example.newsreaderapp.presentation.ui.news_list

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsreaderapp.R
import com.example.newsreaderapp.domain.model.Article
import com.example.newsreaderapp.presentation.ui.theme.Graay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsList(
    modifier: Modifier,
    newsListViewModel: NewsListViewModel = hiltViewModel()
) {
    val state = newsListViewModel.newsPagingDataFlow.collectAsLazyPagingItems()

    // val state = newsListViewModel.state.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        TopAppBar(title = { Text("News Reader") })
        ExposedDropdownMenu()

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (state.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    Log.e("news", "First LazyColumn")
                    Log.e("news", "itemCount${state.itemCount}")
                    items(count = state.itemCount, key = state.itemKey { it.title }) { index ->
                        Log.e("news", "index$index")
                        state[index]?.let { article ->
                            NewsCard(article = article)
                        }
                    }
                    item {
                        if (state.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
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
    }
}

@Composable
fun NewsCard(modifier: Modifier = Modifier, article: Article) {
    Card(
        modifier = modifier
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
                    .placeholder(R.drawable.baseline_newspaper_24)
                    .build(),
                contentDescription = stringResource(R.string.article_image),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                colorFilter = ColorFilter.colorMatrix(matrix),
                error = painterResource(id = R.drawable.baseline_newspaper_24)
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = article.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            if (!article.description.isNullOrEmpty()) Text(text = article.description)
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
                Text(text = article.publishedAt, fontSize = 14.sp, color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                if (!article.source.isNullOrEmpty())
                    Text(text = article.source, fontSize = 14.sp, color = Color.White)

            }

        }

    }

}


