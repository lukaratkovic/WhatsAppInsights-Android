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
    fun getBySender(): Map<String, Int>{
        val map = mutableMapOf<String, Int>()
        for(message in messages){
            map.merge(message.sender, 1){ count, _ -> count+1 }
        }
        return map.toList().sortedByDescending { (_, value) -> value }.toMap()
    }
    fun getByTimeOfDay(): Map<String, Int> {
        val map = mutableMapOf("Morning" to 0, "Day" to 0, "Evening" to 0, "Night" to 0)
        for(message in messages){
            if(message.time.hour in 4..9)
                map["Morning"] = map["Morning"]!!+1
            else if(message.time.hour in 10..15)
                map["Day"] = map["Day"]!!+1
            else if(message.time.hour in 16..21)
                map["Evening"] = map["Evening"]!!+1
            else map["Night"] = map["Night"]!!+1
        }
        return map
    }
}

data class InsightMessage(
    val time: LocalDateTime,
    val sender: String,
    val message: String
)