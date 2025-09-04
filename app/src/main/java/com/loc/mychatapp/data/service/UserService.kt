package com.loc.mychatapp.data.service

import com.google.firebase.firestore.FirebaseFirestore
import com.loc.mychatapp.data.model.User

class UserService() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getAllUsers(onResult: (List<User>) -> Unit) {
        db.collection("users").addSnapshotListener { snapshot, _ ->
            val users = snapshot?.toObjects(User::class.java) ?: emptyList()
            onResult(users)
        }
    }
}