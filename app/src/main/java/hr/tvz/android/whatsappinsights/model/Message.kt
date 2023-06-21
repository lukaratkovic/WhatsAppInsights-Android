package hr.tvz.android.whatsappinsights.model

import java.time.LocalDateTime

data class Message(
    val time: LocalDateTime,
    val sender: String,
    var message: String
){
    fun appendToMessage(message: String){
        this.message += message
    }
}