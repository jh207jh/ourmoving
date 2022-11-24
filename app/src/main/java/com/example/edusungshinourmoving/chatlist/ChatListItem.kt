package com.example.edusungshinourmoving.chatlist

data class ChatListItem(
    val buyerId: String,
    val sellerId: String,
    val key: Long,
    val itemTitle: String
) {
    constructor(): this("", "", 0, "")
}
