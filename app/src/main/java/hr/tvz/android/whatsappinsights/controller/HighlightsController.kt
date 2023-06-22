package hr.tvz.android.whatsappinsights.controller

import android.app.ActionBar.LayoutParams
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import hr.tvz.android.whatsappinsights.view.IHighlightsView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface IHighlightsController{
    fun generateTopViewsFromMap(map: Map<LocalDate, Int>): List<TableRow>
}

class HighlightsController(private val highlightsView: IHighlightsView): IHighlightsController{
    override fun generateTopViewsFromMap(map: Map<LocalDate, Int>): List<TableRow> {
        val dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMMM, yyyy")
        val rows = mutableListOf<TableRow>()

        for((date, count) in map){
            val tableRow = TableRow(highlightsView.getFragmentContext())

            val dateTextView = TextView(highlightsView.getFragmentContext())
            dateTextView.text = dateFormatter.format(date)
            dateTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

            val countTextView = TextView(highlightsView.getFragmentContext())
            countTextView.text = count.toString()
            countTextView.gravity = Gravity.CENTER
            countTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

            tableRow.addView(dateTextView)
            tableRow.addView(countTextView)

            rows.add(tableRow)
        }

        return rows
    }

}