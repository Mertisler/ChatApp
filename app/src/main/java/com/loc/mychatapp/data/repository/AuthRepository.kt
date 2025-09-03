package com.loc.mychatapp.data.repository

import com.loc.mychatapp.data.model.Message
import com.loc.mychatapp.data.model.User
import com.loc.mychatapp.data.service.AuthService
import com.loc.mychatapp.data.service.ChatService
import com.loc.mychatapp.data.service.UserService

class AuthRepository(private val service: AuthService) {
    fun register(email: String, pass: String, name: String, onResult: (Boolean) -> Unit) =
        service.register(email, pass, name, onResult)

    fun login(email: String, pass: String, onResult: (Boolean) -> Unit) =
        service.login(email, pass, onResult)

    fun currentUser() = service.currentUser()
    fun logout() = service.logout()
}

class UserRepository(private val service: UserService) {
    fun getAllUsers(onResult: (List<User>) -> Unit) = service.getAllUsers(onResult)
}

class ChatRepository(private val service: ChatService) {
    fun sendMessage(fromId: String, toId: String, text: String) = service.sendMessage(fromId, toId, text)
    fun listenMessages(userA: String, userB: String, onMessages: (List<Message>) -> Unit) =
        service.listenMessages(userA, userB, onMessages)
}
