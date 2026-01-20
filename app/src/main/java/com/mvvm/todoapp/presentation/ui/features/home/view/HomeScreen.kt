package com.mvvm.todoapp.presentation.ui.features.home.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mvvm.todoapp.R
import com.mvvm.todoapp.presentation.ui.features.add_tasks.AddTaskScreen
import com.mvvm.todoapp.presentation.ui.features.calendar.CalendarScreen
import com.mvvm.todoapp.presentation.ui.features.home.navigation.NavigationScreens
import com.mvvm.todoapp.presentation.ui.features.home.navigation.getBottomNavigationItems
import com.mvvm.todoapp.presentation.ui.features.settings.view.SettingsScreen
import com.mvvm.todoapp.presentation.ui.features.tasks.view.TasksScreen
import com.mvvm.todoapp.presentation.ui.features.tasks.viewmodel.TodoViewModel
import com.mvvm.todoapp.presentation.ui.theme.AccentBlue
import com.mvvm.todoapp.presentation.ui.theme.AppBackground
import com.mvvm.todoapp.presentation.ui.theme.TextPrimary
import com.mvvm.todoapp.presentation.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current

    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    val navController = rememberNavController()

    // 1. Sheet State for "Add Task"
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // 2. Auto-select tab based on current route (fixes back button sync issues)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val todoViewModel: TodoViewModel = hiltViewModel()

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Tasks",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, "Menu", tint = TextPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, "More", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppBackground)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = AppBackground,
                contentColor = AccentBlue,
                tonalElevation = 0.dp
            ) {
                getBottomNavigationItems(context).forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = navigationSelectedItem == index,
                        label = {
                            Text(
                                text = item.title,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.white)
                            )
                        },
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.semantics { testTag = item.title }
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AccentBlue,
                            selectedTextColor = AccentBlue,
                            indicatorColor = AppBackground, // Remove the pill background shape
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(item.screenRoute) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        },
        floatingActionButton = {
            // Only show FAB on Tasks screen
            if (currentRoute == NavigationScreens.Tasks.screenRoute) {
                FloatingActionButton(
                    onClick = { showBottomSheet = true }, // <--- Open Sheet
                    containerColor = AccentBlue,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = NavigationScreens.Tasks.screenRoute,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(NavigationScreens.Tasks.screenRoute) {
                TasksScreen(viewModel = todoViewModel)
            }
            composable(NavigationScreens.Calendar.screenRoute) {
                CalendarScreen()
            }
            composable(NavigationScreens.Settings.screenRoute) {
                SettingsScreen()
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = AppBackground
            ) {
                // Reuse the AddTaskScreen we built earlier
                AddTaskScreen(
                    onDismiss = { showBottomSheet = false },
                    onSave = { newTask ->
                        todoViewModel.addTask(newTask)
                        showBottomSheet = false
                    }
                )
            }
        }
    }
}