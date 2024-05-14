package com.example.newsapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.model.Article

@Composable
fun NewsDetailScreen(
    new: Article?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            imageVector = Icons.Filled.Clear,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth(),
            contentDescription = "Haber makalesi görseli"
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = new?.title.orEmpty(),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = new?.description.orEmpty(),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = new?.author.orEmpty(),
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = new?.publishedAt.orEmpty(),
                style = MaterialTheme.typography.titleSmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = new?.source?.name.orEmpty(),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
