package hr.tvz.android.whatsappinsights.controller

import android.view.View
import android.widget.LinearLayout
import hr.tvz.android.whatsappinsights.model.BreakdownMonthTextView
import hr.tvz.android.whatsappinsights.model.HeaderTextView
import hr.tvz.android.whatsappinsights.view.IBreakdownView
import java.time.Month

interface IBreakdownController{
    fun generateViewFromMap(map: Map<Int, Map<Int, Int>>): View
}

class BreakdownController(private val breakdownView: IBreakdownView) : IBreakdownController {
    override fun generateViewFromMap(map: Map<Int, Map<Int, Int>>): View {
        val container = LinearLayout(breakdownView.getFragmentContext())
        container.orientation = LinearLayout.VERTICAL
        for((year, monthMap) in map){
            val view = HeaderTextView(breakdownView.getFragmentContext())
            val text = "$year (${monthMap.values.sum()} messages)"
            view.text = text
            container.addView(view)
            for((month, count) in monthMap){
                val view = BreakdownMonthTextView(breakdownView.getFragmentContext())
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