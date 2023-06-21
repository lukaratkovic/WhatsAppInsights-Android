package hr.tvz.android.whatsappinsights.view

interface IInsightSummaryView {
    fun setCount(n: Int)
    fun setFirstDate(date: String)
    fun setLastDate(date: String)
    fun setSenderBreakdown(map: Map<String, Int>)
}