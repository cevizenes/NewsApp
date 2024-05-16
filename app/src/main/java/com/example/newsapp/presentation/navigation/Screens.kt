package com.example.newsapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screens(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    data object News : Screens(
        route = "news",
        title = "News",
        icon = Icons.Outlined.Home
    )

    data object Favorites : Screens(
        route = "favorites",
        title = "Favorites",
        icon = Icons.Outlined.FavoriteBorder
    )

    data object NewsDetail : Screens(
        route = "newsDetails",
    )

    data object Search : Screens(
        route = "search",
    )

    data object WebView : Screens(
        route = "webView",
    )
}