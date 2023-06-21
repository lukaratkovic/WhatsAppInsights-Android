package hr.tvz.android.whatsappinsights.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hr.tvz.android.whatsappinsights.databinding.FragmentInsightsSummaryBinding
import hr.tvz.android.whatsappinsights.model.InsightsGenerator
import hr.tvz.android.whatsappinsights.view.IInsightSummaryView

class InsightsSummaryFragment(val insightsGenerator: InsightsGenerator) : Fragment(), IInsightSummaryView {
    private lateinit var binding: FragmentInsightsSummaryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsightsSummaryBinding.inflate(inflater, container, false)

        setCount(insightsGenerator.getTotalCount())
        setFirstDate(insightsGenerator.getFirstMessageDate())
        setLastDate(insightsGenerator.getLastMessageDate())

        return binding.root
    }

    override fun setCount(n: Int) {
        val text = "$n messages have been loaded"
        binding.insightsMessageCount.text = text
    }

    override fun setFirstDate(date: String) {
        val text = "First message was $date"
        binding.insightsFirstMessageDate.text = text
    }

    override fun setLastDate(date: String) {
        val text = "Last message was $date"
        binding.insightsLastMessageDate.text = text
    }
}