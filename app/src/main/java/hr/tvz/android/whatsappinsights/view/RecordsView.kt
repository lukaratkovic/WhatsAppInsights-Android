package hr.tvz.android.whatsappinsights.view

import java.time.LocalDate

interface RecordsView {
    fun setTop5(map: Map<LocalDate, Int>)
}