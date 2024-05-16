package com.example.newsapp.presentation.navigation

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//import com.example.newsapp.ui.screen.SearchScreen
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.favorite.FavoritesScreen
import com.example.newsapp.presentation.detail.NewsDetailScreen
import com.example.newsapp.presentation.news.NewsScreenRoute
import com.example.newsapp.presentation.webview.WebViewScreen
import com.example.newsapp.presentation.favorite.FavoriteViewModel
import com.example.newsapp.presentation.detail.NewsDetailViewModel

@Composable
fun Nav() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val isBottomBarVisible = remember(navBackStackEntry) {
        navBackStackEntry?.destination?.route == Screens.News.route ||
                navBackStackEntry?.destination?.route == Screens.Favorites.route
    }
    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                BottomBar(navController = navController)
            }

        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(PaddingValues(bottom = paddingValues.calculateBottomPadding()))
        )
        NavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val appScreens = listOf(
        Screens.News,
        Screens.Favorites
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination



    BottomNavigation {
        appScreens.forEach { appScreen ->
            AddItems(
                appScreen = appScreen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }


}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.News.route
    ) {
        composable(route = Screens.News.route) {
            NewsScreenRoute(
                navigateToDetails = { article ->
                    navigateToDetails(
                        navController = navController,
                        article = article
                    )
                },
                navigate = {}
            )
        }
        composable(route = Screens.Favorites.route) {
            val viewModel: FavoriteViewModel = hiltViewModel()
            val favoriteState = viewModel.favoriteState.value
            FavoritesScreen(
                navigateToDetail = {
                    navigateToDetails(
                        navController = navController,
                        article = it
                    )
                },
                favoriteState = favoriteState
            )
        }
        composable(route = Screens.Search.route) {

        }
        composable(route = Screens.NewsDetail.route) {
            val viewModel: NewsDetailViewModel = hiltViewModel()
            navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                ?.let { article ->
                    NewsDetailScreen(
                        new = article,
                        navigateUp = { navController.navigateUp() },
                        detailEvent = viewModel::onNewsDetailEvent,
                        navigateToSource = {
                            navigateToSource(
                                navController = navController,
                                url = article.url
                            )
                        }
                    )
                }
        }
        composable(route = Screens.WebView.route) {
            navController.previousBackStackEntry?.savedStateHandle?.get<String>("url")
                ?.let { url ->
                    WebViewScreen(
                        url = url,
                        onBackClick = { navController.navigateUp() }
                    )
                }
        }
    }
}

@Composable
fun RowScope.AddItems(
    appScreen: Screens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        modifier = Modifier
            .background(Color.White),
        label = {
            Text(
                text = appScreen.title.orEmpty(),
                color = Color.Black,
                fontSize = 12.sp
            )
        },
        icon = {
            appScreen.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "Icon"
                )
            }
        },
        selected = currentDestination?.hierarchy?.any { it.route == appScreen.route } == true,
        unselectedContentColor = LocalContentColor.current.copy(ContentAlpha.disabled),
        onClick = {
            changeToTab(navController = navController, appScreen.route)
        }
    )
}


private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Screens.NewsDetail.route
    )
}

private fun changeToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let {
            popUpTo(it) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }

}

private fun navigateToSource(navController: NavController, url: String) {
    navController.currentBackStackEntry?.savedStateHandle?.set("url", url)
    navController.navigate(
        route = Screens.WebView.route
    )
}

@Composable
private fun OnBackClick(navController: NavController) {
    BackHandler(true) {
        changeToTab(
            navController = navController,
            route = Screens.News.route
        )
    }
}