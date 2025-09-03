package com.loc.mychatapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loc.mychatapp.data.model.User
import com.loc.mychatapp.data.repository.UserRepository

class UserViewModel(private val repo: UserRepository) : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun loadUsers() {
        repo.getAllUsers { list -> _users.postValue(list) }
    }
}
