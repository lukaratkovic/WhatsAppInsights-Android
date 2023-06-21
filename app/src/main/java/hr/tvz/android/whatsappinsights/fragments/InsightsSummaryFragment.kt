package hr.tvz.android.whatsappinsights.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hr.tvz.android.whatsappinsights.databinding.FragmentInsightsSummaryBinding
import hr.tvz.android.whatsappinsights.model.InsightsGenerator
import hr.tvz.android.whatsappinsights.model.MessageDatabase
import hr.tvz.android.whatsappinsights.model.MessageRepository
import hr.tvz.android.whatsappinsights.view.IInsightSummaryView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InsightsSummaryFragment : Fragment(), IInsightSummaryView {
    private lateinit var binding: FragmentInsightsSummaryBinding
    private val database by lazy { MessageDatabase.getDatabase(this.requireContext()) }
    private val repository by lazy { MessageRepository(database.messageDao()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsightsSummaryBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.IO).launch {
            val messages = repository.allMessages()
            val insights = InsightsGenerator(messages)
            setCount(insights.getTotalCount())
            setFirstDate(insights.getFirstMessageDate())
            setLastDate(insights.getLastMessageDate())

        }
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

    override fun setSenderBreakdown(map: Map<String, Int>) {
        val breakdownBuilder = StringBuilder()
        for((user, count) in map){
            TODO()
        }
    }
}