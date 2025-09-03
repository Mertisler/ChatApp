package com.loc.mychatapp.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.loc.mychatapp.viewmodel.AuthViewModel

@Composable
fun WelcomeScreen(
    authViewModel: AuthViewModel,
    onNavigate: (Boolean) -> Unit
) {
    val user by authViewModel.user.observeAsState()

    LaunchedEffect(user) {
        onNavigate(user != null) // true ise ChatListScreen, false ise LoginScreen
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Hoşgeldin! Kontrol ediliyor...", style = MaterialTheme.typography.headlineMedium)
    }
}
