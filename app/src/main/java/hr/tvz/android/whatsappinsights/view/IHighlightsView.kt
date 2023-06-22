package hr.tvz.android.whatsappinsights.view

import android.content.Context
import android.widget.TableRow

interface IHighlightsView {
    fun setTopDays(rows: List<TableRow>, n: Int)
    fun getFragmentContext(): Context
}