package com.loc.mychatapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.loc.mychatapp.ui.auth.LoginScreen
import com.loc.mychatapp.ui.auth.RegisterScreen
import com.loc.mychatapp.ui.auth.WelcomeScreen
import com.loc.mychatapp.ui.chat.ChatListScreen
import com.loc.mychatapp.ui.chat.ChatScreen
import com.loc.mychatapp.ui.profile.ProfileScreen
import com.loc.mychatapp.viewmodel.AuthViewModel
import com.loc.mychatapp.viewmodel.ChatViewModel
import com.loc.mychatapp.viewmodel.UserViewModel

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object ChatList : Screen("chat_list")
    object Chat : Screen("chat/{userId}") {
        fun createRoute(userId: String) = "chat/$userId"
    }
    object Profile : Screen("profile")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    chatViewModel: ChatViewModel,
    modifier: Modifier = Modifier
) {
    val currentUser by authViewModel.user.collectAsStateWithLifecycle()
    val initialized by authViewModel.initialized.collectAsStateWithLifecycle()

    NavHost(navController, startDestination = Screen.Welcome.route) {

        composable(Screen.Welcome.route) {
            WelcomeScreen(authViewModel) { loggedIn ->
                if (loggedIn) navController.navigate(Screen.ChatList.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = true }
                } else navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    // currentUser Flow güncellendi mi bekle
                    navController.navigate(Screen.ChatList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                authViewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate(Screen.ChatList.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable(Screen.ChatList.route) {
            val currentUser by authViewModel.user.collectAsStateWithLifecycle()
            if (currentUser?.uid.isNullOrBlank()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Giriş yapılmamış, bekleniyor...")
                }
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
                return@composable
            }

            ChatListScreen(
                userViewModel = userViewModel,
                currentUserId = currentUser!!.uid,
                onUserClick = { user ->
                    if (user.uid.isNotBlank()) navController.navigate(Screen.Chat.createRoute(user.uid))
                }
            )
        }


        composable(
            route = Screen.Chat.route,
            arguments = listOf(navArgument("userId") { defaultValue = "" })
        ) { backStackEntry ->
            val otherUserId = backStackEntry.arguments?.getString("userId") ?: ""
            if (currentUser?.uid.isNullOrBlank() || otherUserId.isBlank()) return@composable
            ChatScreen(
                chatViewModel = chatViewModel,
                currentUserId = currentUser!!.uid,
                otherUserId = otherUserId
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(authViewModel) {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.ChatList.route) { inclusive = true }
                }
            }
        }
    }
}
