package com.example.newsapp.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article

@SuppressLint("QueryPermissionsNeeded")
@Composable
fun NewsDetailScreen(
    new: Article?,
    navigateUp: () -> Unit,
    navigateToSource: () -> Unit,
    detailEvent: (NewsDetailEvent) -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NewsDetailScreenAppBar(
            onBackClick = navigateUp,
            onShareClick = {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, new?.url)
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(sendIntent, null))
            },
            onFavouriteClick = {
                new?.let { detailEvent(NewsDetailEvent.InsertDeleteNews(it)) }
            }
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(new?.urlToImage)
                .size(700, 700)
                .crossfade(true)
                .build(),
            contentDescription = "Image",
            contentScale = ContentScale.FillBounds
        )

        Text(
            modifier = Modifier.padding(top = 14.dp),
            text = new?.title.orEmpty(),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = new?.description.orEmpty(),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = new?.author.orEmpty(),
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = new?.description.orEmpty(),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        ButtonWithWebView(
            navigateToSource = navigateToSource
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsDetailScreenAppBar(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onFavouriteClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = colorResource(id = R.color.black),
            navigationIconContentColor = colorResource(id = R.color.black)
        ),
        title = { },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share"
                )
            }
            IconButton(onClick = onFavouriteClick) {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite"
                )
            }
        },
    )
}


@Composable
private fun ButtonWithWebView(
    navigateToSource: () -> Unit
) {
    OutlinedButton(
        onClick = { navigateToSource() },
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "News Source",
            color = Color.Black
        )
    }
}


