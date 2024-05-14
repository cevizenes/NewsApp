package com.example.newsapp.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.data.model.Article
import com.example.newsapp.viewmodel.NewsViewModel

@Composable
fun NewsScreenRoute(
    viewModel: NewsViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit
) {
    val news = viewModel.getNews().collectAsLazyPagingItems()
    NewsScreen(news = news, navigateToDetail = {
        navigateToDetail.invoke(it)
    })
}

@Composable
internal fun NewsScreen(
    news: LazyPagingItems<Article>,
    navigateToDetail: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        AppBar()
        SearchBar()
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 64.dp)
        ) {
            items(news.itemCount) { index ->
                val item = news[index]
                item?.let { new ->
                    NewsItem(new)
                }
                if (index != 0 || index != news.itemCount) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray.copy(alpha = 0.5f))
                    )
                }
            }
            news.apply {
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                val loading = when {
                    loadState.prepend is LoadState.Loading -> loadState.prepend as LoadState.Loading
                    loadState.append is LoadState.Loading -> loadState.append as LoadState.Loading
                    loadState.refresh is LoadState.Loading -> loadState.refresh as LoadState.Loading
                    else -> null
                }

                if (loading != null) {
                    repeat((0..20).count()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .background(color = Color.DarkGray)
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }

                if (error != null) {
                    error.error.localizedMessage?.let { Log.e("Error", it) }
                }
            }
        }

    }
}

@Composable
fun AppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Appcent NewsApp",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SearchBar() {
    val searchQuery = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp)),
        value = searchQuery.value,
        label = {
            Text(
                text = "Search",
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        },
        onValueChange = { searchQuery.value = it },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = { searchQuery.value = "" }
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear"
                )
            }
        }

    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun NewsItem(
    news: Article?
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = news?.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = news?.description ?: "",
                fontSize = 12.sp,
                maxLines = 2,
            )

        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news?.urlToImage)
                    .size(width = 250, height = 250)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image",
                contentScale = ContentScale.Crop
            )
        }

    }
}


/*@Composable
fun NewsItem(
    news: Article?
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = news?.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = news?.description ?: "",
                fontSize = 12.sp,
                maxLines = 2,
            )
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(news?.urlToImage)
                .crossfade(true)
                .size(300, 300)
                .build(),
            contentDescription = "Image",
            contentScale = ContentScale.FillBounds
        )

    }
}*/
