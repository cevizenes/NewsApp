package com.example.newsapp.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screens(
    val route: String,
    val title: String,
    val icon: ImageVector?
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
        title = "News Details",
        icon = null
    )
}