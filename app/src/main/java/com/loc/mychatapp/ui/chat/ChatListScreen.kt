package com.loc.mychatapp.ui.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.loc.mychatapp.data.model.User
import com.loc.mychatapp.viewmodel.UserViewModel

@Composable
fun ChatListScreen(
    userViewModel: UserViewModel,
    currentUserId: String,
    onUserClick: (User) -> Unit
) {
    val users by userViewModel.users.observeAsState(emptyList())

    LaunchedEffect(Unit) { userViewModel.loadUsers() }

    LazyColumn {
        items(users.filter { it.uid != currentUserId }) { user ->
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
