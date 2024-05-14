package com.example.newsapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
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

}


@Composable
fun AppcentAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Appcent NewsApp",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search"
            )
        }
    }
}

@Composable
fun SearchBar() {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = "Type a text",
        onValueChange = {},
        placeholder = { Text("Search") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search"
            )
        },
        colors = textFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimary,
            backgroundColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
fun NewsItem() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(100.dp)
    )
    {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "News Title",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipisicing elit.",
                    maxLines = 2,
                )
            }

        }
    }
}
