package com.loc.mychatapp.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.loc.mychatapp.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Kayıt Ol", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))
        TextField(value = name, onValueChange = { name = it }, label = { Text("İsim") })
        Spacer(Modifier.height(8.dp))
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Şifre") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            authViewModel.register(email, password, name) { success ->
                if (success) onRegisterSuccess()
            }
        }) {
            Text("Kayıt Ol")
        }

        TextButton(onClick = onNavigateToLogin) {
            Text("Zaten hesabın var mı? Giriş yap")
        }
    }
}
