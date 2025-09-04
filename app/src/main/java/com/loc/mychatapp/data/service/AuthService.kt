package com.loc.mychatapp.data.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.loc.mychatapp.data.model.User

class AuthService(
) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    fun register(email: String, password: String, name: String, onResult: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener

                val user = User(uid = uid, name = name, email = email)
                db.collection("users").document(uid).set(user)
                onResult(true)
            }
            .addOnFailureListener { onResult(false) }
    }


    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.isSuccessful) }
    }

    fun currentUser(): FirebaseUser? = auth.currentUser

    fun logout() = auth.signOut()
}