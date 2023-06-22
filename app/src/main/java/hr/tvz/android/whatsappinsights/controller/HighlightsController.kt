package hr.tvz.android.whatsappinsights.controller

import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import hr.tvz.android.whatsappinsights.R
import hr.tvz.android.whatsappinsights.view.IHighlightsView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface IHighlightsController{
    fun generateTopDaysRowsFromMap(map: Map<LocalDate, Int>): List<TableRow>
    fun generateTopEmojiRowsFromMap(map: Map<String, Int>): List<TableRow>
}

class HighlightsController(private val highlightsView: IHighlightsView): IHighlightsController{
    private val trParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

    override fun generateTopDaysRowsFromMap(map: Map<LocalDate, Int>): List<TableRow> {
        val dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMMM, yyyy")
        val rows = mutableListOf<TableRow>()

        for((date, count) in map){
            val tableRow = TableRow(highlightsView.getFragmentContext())

            val dateTextView = TextView(highlightsView.getFragmentContext())
            dateTextView.text = dateFormatter.format(date)
            dateTextView.layoutParams = trParams

            val countTextView = TextView(highlightsView.getFragmentContext())
            countTextView.text = count.toString()
            countTextView.gravity = Gravity.CENTER
            countTextView.layoutParams = trParams

            tableRow.addView(dateTextView)
            tableRow.addView(countTextView)

            rows.add(tableRow)
        }

        return rows
    }

    override fun generateTopEmojiRowsFromMap(map: Map<String, Int>): List<TableRow> {
        val rows = mutableListOf<TableRow>()

        for((emoji, count) in map){
            val tableRow = TableRow(highlightsView.getFragmentContext())

            val emojiTextView = TextView(highlightsView.getFragmentContext())
            emojiTextView.text = emoji
            emojiTextView.setTextColor(ContextCompat.getColor(highlightsView.getFragmentContext(), R.color.black))
            emojiTextView.textSize = 20f
            emojiTextView.setPadding(64, 0, 0, 0)
            emojiTextView.layoutParams = trParams

            val countTextView = TextView(highlightsView.getFragmentContext())
            countTextView.text = count.toString()
            countTextView.gravity = Gravity.CENTER
            countTextView.layoutParams = trParams

            tableRow.addView(emojiTextView)
            tableRow.addView(countTextView)

            rows.add(tableRow)
        }

        return rows
    }


}