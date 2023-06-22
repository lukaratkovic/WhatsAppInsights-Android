package hr.tvz.android.whatsappinsights.controller

import android.view.View
import android.widget.LinearLayout
import hr.tvz.android.whatsappinsights.model.TrendsMonthTextView
import hr.tvz.android.whatsappinsights.model.HeaderTextView
import hr.tvz.android.whatsappinsights.view.ITrendsView
import java.time.Month

interface ITrendsController{
    fun generateViewFromMap(map: Map<Int, Map<Int, Int>>): View
}

class TrendsController(private val breakdownView: ITrendsView) : ITrendsController {
    override fun generateViewFromMap(map: Map<Int, Map<Int, Int>>): View {
        val container = LinearLayout(breakdownView.getFragmentContext())
        container.orientation = LinearLayout.VERTICAL
        for((year, monthMap) in map){
            val view = HeaderTextView(breakdownView.getFragmentContext())
            val text = "$year (${monthMap.values.sum()} messages)"
            view.text = text
            container.addView(view)
            for((month, count) in monthMap){
                val view = TrendsMonthTextView(breakdownView.getFragmentContext())
                val text = "${monthToText(month)} - $count messages"
                view.text = text
                container.addView(view)
            }
        }
        return container
    }

    private fun monthToText(monthNumber: Int): String{
        return Month.of(monthNumber).name
    }
}