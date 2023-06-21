package hr.tvz.android.whatsappinsights.model

import java.lang.IllegalArgumentException
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

class Insights(private val messages: List<Message>) {

    init {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.")
        val firstDate = messages.first().time.format(dateFormatter)
        val lastDate = messages.last().time.format(dateFormatter)
        println("> ${messages.size} messages have been loaded, starting on $firstDate and ending on $lastDate")
    }

    fun averages(){
        val (first, last) = messages.first().time to messages.last().time
        val period = Period.between(messages.first().time.toLocalDate(), messages.last().time.toLocalDate())
        val yearlyAverage = messages.size / (period.toTotalMonths() / 12.0)
        val monthlyAverage = messages.size / (period.toTotalMonths()).toDouble()
        val dailyAverage = messages.size / ChronoUnit.DAYS.between(first, last).toDouble()

        println(getHeader("AVERAGES"))
        println("• ${yearlyAverage.roundToInt()} messages per year")
        println("• ${monthlyAverage.roundToInt()} messages per month")
        println("• ${dailyAverage.roundToInt()} messages per day")
    }

    fun monthlyBreakdown(){
        val monthlyData = mutableListOf<MonthlyData>()

        var (month, year) = messages.first().time.month to messages.first().time.year
        var counter = 0

        for(message in messages){
            if(message.time.month == month && message.time.year == year){
                counter++
            }
            else{
                monthlyData.add(MonthlyData(month, year, counter))
                (month to year).apply { month = message.time.month; year = message.time.year }
                counter = 1
            }
        }

        println(getHeader("MONTHLY DATA"))
        monthlyData.forEach(MonthlyData::print)
    }

    fun hourPlot(graph: Boolean = true){
        val map = (0..23).associateWith { 0.0 }.toMutableMap()
        // Count messages per hour interval
        for(message in messages){
            val hour = message.time.toLocalTime().hour
            map[hour] = map[hour]!!+1
        }

        // Draw graph
        val maxValue = map.values.maxOrNull() ?: return
        val scale = 50.0 / maxValue

        println(getHeader("HOURLY DATA"))
        println("   TIME SPAN          AMOUNT OF MESSAGES")
        for ((label, value) in map) {
            val bar = if(graph)
                    "█".repeat((value * scale).toInt())
                else ""
            val percent = (value/messages.size.toDouble() * 10000).roundToInt() / 100.0
            println("%11s %5.0f (%5s%%) %s".format("$label:00-$label:59", value, percent, bar))
        }
    }

    fun topDays(n: Int){
        val map = mutableMapOf<LocalDate, Int>()
        for(message in messages){
            val messageDate = message.time.toLocalDate()
            map.merge(messageDate, 1) { count, _ -> count+1 }
        }
        val top = map.entries.sortedByDescending { it.value }
            .take(n)
            .associate { it.key to it.value }

        // Output
        println(getHeader("TOP DAYS"))
        for((date, count) in top){
            System.out.printf("%10s %5d%n",date,count)
        }
    }

    fun ofDate(date: LocalDate) {
        var totalMessages = 0

        val map = (0..23).associateWith { 0.0 }.toMutableMap()

        for(message in messages){
            // Count messages for day
            if(message.time.toLocalDate().isEqual(date))
                totalMessages++
            // Count messages for hour
            val hour = message.time.toLocalTime().hour
            map[hour] = map[hour]!!+1
        }

        println(getHeader(date.toString()))
        println("Total: $totalMessages messages")
    }

    fun bySender(){
        val map = mutableMapOf<String, Int>()
        for(message in messages){
            map.merge(message.sender, 1) { count, _ -> count+1 }
        }

        println(getHeader("MESSAGES BY SENDER"))
        for((sender, messages) in map){
            System.out.printf("%-10s %5d%n",sender,messages)
        }
    }

    private fun getHeader(text: String): String{
        val totalLength = 50
        if(text.length > totalLength)
            throw IllegalArgumentException("getHeader input parameter must not be longer than $totalLength characters")
        val paddingLength = (totalLength - text.length)/2
        val padding = "-".repeat(paddingLength)
        var header = "\n$padding$text$padding"
        if(header.length==50) header += "-"
        return header
    }
}