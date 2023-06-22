package hr.tvz.android.whatsappinsights.view

import android.content.Context
import android.view.View
import hr.tvz.android.whatsappinsights.databinding.FragmentInsightsBreakdownBinding

interface IBreakdownView {
    fun setTimeOfDayValues(map: Map<String,Int>)
    fun addToMonthlyBreakdown(view: View)
    fun getFragmentContext(): Context
    fun getBinding(): FragmentInsightsBreakdownBinding
}