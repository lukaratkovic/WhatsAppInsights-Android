package hr.tvz.android.whatsappinsights.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class InsightsGenerator(private val inputMessages: List<Message>) {
    var messages: List<InsightMessage> = inputMessages
        .map { m -> InsightMessage(m.time(), m.sender, m.message) }
        .sortedBy { it.time }
    private val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy")

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

    fun getAverages(): Triple<Double, Double, Double>{
        val (first, last) = messages.first().time to messages.last().time
        val period = Period.between(first.toLocalDate(), last.toLocalDate())
        val yearlyAverage = messages.size / (period.toTotalMonths()/12.0)
        val monthlyAverage = messages.size / (period.toTotalMonths()).toDouble()
        val dailyAverage = messages.size / ChronoUnit.DAYS.between(first,last).toDouble()
        return Triple(yearlyAverage, monthlyAverage, dailyAverage)
    }

    fun getMonthlyBreakdown(): Map<Int, Map<Int, Int>>{
        val map = mutableMapOf<Int, MutableMap<Int, Int>>()
        for (message in messages){
            val year = message.time.year
            val month = message.time.monthValue

            val yearMap = map.getOrPut(year){ mutableMapOf()}
            yearMap[month] = yearMap.getOrDefault(month, 0)+1

            map[year] = yearMap.toSortedMap(reverseOrder())
        }
        return map.toSortedMap(reverseOrder())
    }

    fun getTopN(n: Int): Map<LocalDate, Int> {
        val map = mutableMapOf<LocalDate, Int>()
        for (message in messages) {
            val messageDate = message.time.toLocalDate()
            map.merge(messageDate, 1) { count, _ -> count + 1 }
        }
        return map.entries.sortedByDescending { it.value }
            .take(n)
            .associate { it.key to it.value }
    }
}

data class InsightMessage(
    val time: LocalDateTime,
    val sender: String,
    val message: String
)