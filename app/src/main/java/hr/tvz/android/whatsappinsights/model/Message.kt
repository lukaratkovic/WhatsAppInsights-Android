package hr.tvz.android.whatsappinsights.model

import java.time.LocalDateTime
import java.io.Serializable

data class Message(
    val time: LocalDateTime,
    val sender: String,
    var message: String
): Serializable{
    fun appendToMessage(message: String){
        this.message += message
    }
}