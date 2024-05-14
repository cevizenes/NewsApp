package com.example.newsapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.screen.Screens

@Composable
fun Nav() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
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
        Screens.Home,
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
        startDestination = "news"
    ) {
        composable(route = Screens.Home.route) {

        }
        composable(route = Screens.Favorites.route) {

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
                text = appScreen.title,
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
            navController.navigate(appScreen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}