package com.loc.mychatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.loc.mychatapp.navigation.NavGraph
import com.loc.mychatapp.ui.theme.MyChatAppTheme
import com.loc.mychatapp.viewmodel.AuthViewModel
import com.loc.mychatapp.viewmodel.ChatViewModel
import com.loc.mychatapp.viewmodel.UserViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Factory'yi burada oluşturuyoruz. Stateless ise dışarıda oluşturmak gayet iyi.
        val factory = AppViewModelFactory()

        setContent {
            MyChatAppTheme {
                val navController = rememberNavController()

                // viewModel() yalnızca Composable scope içinde çağrılmalı — bu yüzden setContent içinde.
                val authViewModel: AuthViewModel = viewModel(factory = factory)
                val userViewModel: UserViewModel = viewModel(factory = factory)
                val chatViewModel: ChatViewModel = viewModel(factory = factory)

                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    NavGraph(
                        navController = navController,
                        authViewModel = authViewModel,
                        userViewModel = userViewModel,
                        chatViewModel = chatViewModel,
                        modifier = Modifier.padding(paddingValues)
                    )
                }




            }
        }
    }
}
