package com.example.newsapp.presentation.search/*
package com.example.newsapp.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.common.SearchBar
import com.example.newsapp.viewmodel.SearchEvent
import com.example.newsapp.viewmodel.SearchState

@Composable
fun SearchScreen(
    searchState: SearchState,
    onSearchEvent: (SearchEvent) -> Unit,
    navigate: (String) -> Unit
) {

    SearchBar(
        text = searchState.searchQuery,
        readOnly = false,
        onSearch = { onSearchEvent(SearchEvent.SearchNewsByQuery) },
        onValueChange = { onSearchEvent(SearchEvent.UpdateSearchQuery(it)) },
        onResetQuery = { onSearchEvent(SearchEvent.UpdateSearchQuery("")) }
    )
    Spacer(modifier = Modifier.height(16.dp))
    searchState.news?.let {
        val news = it.collectAsLazyPagingItems()
        NewsScreen(
            news = news,
            navigate = navigate,
            navigateToDetails = navigateToDetails
        )
    }
}

*/
