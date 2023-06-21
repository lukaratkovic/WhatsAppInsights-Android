package hr.tvz.android.whatsappinsights.model

import java.time.format.DateTimeFormatter

class Insights(private val messages: List<Message>) {
    init {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.")
        val firstDate = messages.first().time.format(dateFormatter)
        val lastDate = messages.last().time.format(dateFormatter)
        println("> ${messages.size} messages have been loaded, starting on $firstDate and ending on $lastDate")
    }
}