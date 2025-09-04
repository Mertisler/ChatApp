package com.loc.mychatapp.ui.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loc.mychatapp.data.model.User
import com.loc.mychatapp.viewmodel.UserViewModel

@Composable
fun ChatListScreen(
    userViewModel: UserViewModel,
    currentUserId: String,
    onUserClick: (User) -> Unit
) {
    val users by userViewModel.users.collectAsStateWithLifecycle(initialValue = emptyList())
    LaunchedEffect(Unit) { userViewModel.loadUsers() }

    val filtered = users.filter { it.uid.isNotBlank() && it.uid != currentUserId }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            filtered.isEmpty() -> Text(
                "Henüz başka bir kullanıcı yok. Arkadaşlarını davet et!",
                style = MaterialTheme.typography.bodyLarge
            )
            else -> LazyColumn {
                items(filtered) { user ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onUserClick(user) }
                            .padding(16.dp)
                    ) {
                        Text(user.name, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
