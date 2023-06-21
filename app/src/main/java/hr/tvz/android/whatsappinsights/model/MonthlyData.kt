package hr.tvz.android.whatsappinsights.model

import java.time.Month

data class MonthlyData(val month: Month, val year: Int, val total: Int){
    fun print(){
        println("%20s %8d messages".format("$month $year", total))
    }
}