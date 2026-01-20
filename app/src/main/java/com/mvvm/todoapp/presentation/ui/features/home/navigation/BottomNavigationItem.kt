package com.mvvm.todoapp.presentation.ui.features.home.navigation

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.mvvm.todoapp.R
import com.mvvm.todoapp.utils.Constants

data class BottomNavigationItem(
    val title: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val screenRoute: String = ""
)

fun getBottomNavigationItems(context: Context): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            title = context.getString(R.string.tasks),
            icon = Icons.Default.CheckCircle,
            screenRoute = NavigationScreens.Tasks.screenRoute
        ),
        BottomNavigationItem(
            title = context.getString(R.string.calendar),
            icon = Icons.Default.DateRange,
            screenRoute = NavigationScreens.Calendar.screenRoute
        ),
        BottomNavigationItem(
            title = context.getString(R.string.settings),
            icon = Icons.Default.Settings,
            screenRoute = NavigationScreens.Settings.screenRoute
        )
    )
}

sealed class NavigationScreens(var screenRoute: String) {
    data object Tasks : NavigationScreens(Constants.TASKS_ROUTES)
    data object Calendar : NavigationScreens(Constants.CALENDAR_ROUTES)
    data object Settings : NavigationScreens(Constants.SETTINGS_ROUTES)
}