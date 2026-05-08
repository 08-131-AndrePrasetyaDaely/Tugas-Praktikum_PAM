package com.example.myprofileapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myprofileapp.screens.*
import com.example.myprofileapp.ui.ProfileScreen
import com.example.myprofileapp.viewmodel.AiViewModel
import com.example.myprofileapp.viewmodel.NotesViewModel
import com.example.myprofileapp.viewmodel.ProfileViewModel
import com.example.myprofileapp.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    profileViewModel: ProfileViewModel
) {
    val notesViewModel: NotesViewModel = koinViewModel()
    val settingsViewModel: SettingsViewModel = koinViewModel()
    val aiViewModel: AiViewModel = koinViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Aplikasi Catatan", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.headlineMedium)
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Catatan") },
                    selected = currentRoute == Screen.Notes.route,
                    onClick = {
                        navController.navigate(Screen.Notes.route)
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Favorit") },
                    selected = currentRoute == Screen.Favorites.route,
                    onClick = {
                        navController.navigate(Screen.Favorites.route)
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Asisten AI") },
                    selected = currentRoute == Screen.AiChat.route,
                    onClick = {
                        navController.navigate(Screen.AiChat.route)
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Profil") },
                    selected = currentRoute == Screen.Profile.route,
                    onClick = {
                        navController.navigate(Screen.Profile.route)
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Pengaturan") },
                    selected = currentRoute == Screen.Settings.route,
                    onClick = {
                        navController.navigate(Screen.Settings.route)
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                val showTopBar = currentRoute in listOf(
                    Screen.Notes.route, 
                    Screen.Favorites.route, 
                    Screen.AiChat.route,
                    Screen.Profile.route,
                    Screen.Settings.route
                )
                if (showTopBar) {
                    CenterAlignedTopAppBar(
                        title = { 
                            Text(when(currentRoute) {
                                Screen.Notes.route -> "Catatan Saya"
                                Screen.Favorites.route -> "Favorit"
                                Screen.AiChat.route -> "Asisten AI"
                                Screen.Profile.route -> "Profil"
                                Screen.Settings.route -> "Pengaturan"
                                else -> "Aplikasi Catatan"
                            })
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                }
            },
            bottomBar = {
                val showBottomBar = currentRoute in listOf(
                    Screen.Notes.route, 
                    Screen.Favorites.route, 
                    Screen.AiChat.route,
                    Screen.Profile.route
                )
                if (showBottomBar) {
                    NavigationBar {
                        val items = listOf(
                            Triple(Screen.Notes, "Catatan", Screen.Notes.icon),
                            Triple(Screen.Favorites, "Favorit", Screen.Favorites.icon),
                            Triple(Screen.AiChat, "AI Chat", Screen.AiChat.icon),
                            Triple(Screen.Profile, "Profil", Screen.Profile.icon)
                        )
                        items.forEach { (screen, label, icon) ->
                            NavigationBarItem(
                                icon = { icon?.let { Icon(it, contentDescription = label) } },
                                label = { Text(label) },
                                selected = currentRoute == screen.route,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Notes.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Notes.route) {
                    NotesScreen(
                        viewModel = notesViewModel,
                        onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) },
                        onAddNoteClick = { navController.navigate(Screen.AddEditNote.createRoute()) }
                    )
                }
                composable(Screen.Favorites.route) {
                    FavoritesScreen(
                        viewModel = notesViewModel,
                        onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) }
                    )
                }
                composable(Screen.AiChat.route) {
                    AiChatScreen(viewModel = aiViewModel)
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(
                        viewModel = profileViewModel,
                        settingsViewModel = settingsViewModel
                    )
                }
                composable(Screen.Settings.route) {
                    SettingsScreen(viewModel = settingsViewModel)
                }
                composable(
                    route = Screen.NoteDetail.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getLong("noteId") ?: return@composable
                    NoteDetailScreen(
                        noteId = noteId,
                        viewModel = notesViewModel,
                        aiViewModel = aiViewModel,
                        onBack = { navController.popBackStack() },
                        onEdit = { id -> navController.navigate(Screen.AddEditNote.createRoute(id)) }
                    )
                }
                composable(
                    route = Screen.AddEditNote.route,
                    arguments = listOf(navArgument("noteId") {
                        type = NavType.LongType
                        defaultValue = -1L
                    })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getLong("noteId")
                    val noteId = if (id == -1L) null else id
                    AddEditNoteScreen(
                        noteId = noteId,
                        viewModel = notesViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
