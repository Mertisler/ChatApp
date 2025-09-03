package com.loc.mychatapp.data.service

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.loc.mychatapp.data.model.Message
import java.util.UUID

class ChatService {
    private val db = FirebaseFirestore.getInstance()

    private fun getConversationId(userA: String, userB: String): String {
        return if (userA < userB) "${userA}_${userB}" else "${userB}_${userA}"
    }

    fun sendMessage(fromId: String, toId: String, text: String) {
        val conversationId = getConversationId(fromId, toId)
        val message = Message(
            id = UUID.randomUUID().toString(),
            fromId = fromId,
            toId = toId,
            text = text
        )
        db.collection("conversations")
            .document(conversationId)
            .collection("messages")
            .document(message.id)
            .set(message)
    }

    fun listenMessages(userA: String, userB: String, onMessages: (List<Message>) -> Unit) {
        val conversationId = getConversationId(userA, userB)
        db.collection("conversations")
            .document(conversationId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, _ ->
                val messages = snapshot?.toObjects(Message::class.java) ?: emptyList()
                onMessages(messages)
            }
    }
}
