package com.loc.mychatapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.loc.mychatapp.data.repository.AuthRepository
import com.loc.mychatapp.data.repository.ChatRepository
import com.loc.mychatapp.data.repository.UserRepository
import com.loc.mychatapp.data.service.AuthService
import com.loc.mychatapp.data.service.ChatService
import com.loc.mychatapp.data.service.UserService
import com.loc.mychatapp.viewmodel.AuthViewModel
import com.loc.mychatapp.viewmodel.ChatViewModel
import com.loc.mychatapp.viewmodel.UserViewModel

class AppViewModelFactory : ViewModelProvider.Factory {

    private val authService = AuthService()
    private val userService = UserService()
    private val chatService = ChatService()

    private val authRepo = AuthRepository(authService)
    private val userRepo = UserRepository(userService)
    private val chatRepo = ChatRepository(chatService)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(authRepo) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(userRepo) as T
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> ChatViewModel(chatRepo) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
