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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseUser
import com.loc.mychatapp.viewmodel.AuthViewModel

@Composable
fun WelcomeScreen(
    authViewModel: AuthViewModel,
    onNavigate: (Boolean) -> Unit
) {
    val user by authViewModel.user.collectAsStateWithLifecycle()
    val initialized by authViewModel.initialized.collectAsStateWithLifecycle()

    LaunchedEffect(initialized, user) {
        if (!initialized) return@LaunchedEffect
        if (!user?.uid.isNullOrEmpty()) onNavigate(true) else onNavigate(false)
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Hoşgeldin! Kontrol ediliyor...", style = MaterialTheme.typography.headlineMedium)
    }
}


