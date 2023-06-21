package hr.tvz.android.whatsappinsights.view

interface ISummaryView {
    fun setCount(n: Int)
    fun setFirstDate(date: String)
    fun setLastDate(date: String)
    fun setSenderBreakdown(map: Map<String, Int>)
}