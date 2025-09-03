package com.loc.mychatapp.data.model

data class Message(
    val id: String = "",
    val fromId: String,
    val toId: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),


)
