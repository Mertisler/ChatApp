package com.loc.mychatapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.loc.mychatapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel(
    private val repo: AuthRepository

) : ViewModel() {
    private val _user = MutableLiveData<FirebaseUser?>(repo.currentUser())
    val user: LiveData<FirebaseUser?> = _user

    fun register(email: String, pass: String, name: String, onResult: (Boolean) -> Unit) {
        repo.register(email, pass, name) { success ->
            if (success) _user.value = repo.currentUser()
            onResult(success)
        }
    }
    fun login(email: String, pass: String, onResult: (Boolean) -> Unit) {
        repo.login(email, pass) { success ->
            if (success) _user.value = repo.currentUser()
            onResult(success)
        }
    }

    fun logout() {
        repo.logout()
        _user.value = null
    }
}