package hr.tvz.android.whatsappinsights.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InsightsGenerator(private val inputMessages: List<Message>) {
    var messages: List<InsightMessage> = inputMessages
        .map { m -> InsightMessage(m.time(), m.sender, m.message) }
        .sortedBy { it.time }
    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.")

    fun getTotalCount() = messages.size
    fun getFirstMessageDate(): String = messages.first().time.format(dateFormatter)
    fun getLastMessageDate(): String = messages.last().time.format(dateFormatter)
}

data class InsightMessage(
    val time: LocalDateTime,
    val sender: String,
    val message: String
)