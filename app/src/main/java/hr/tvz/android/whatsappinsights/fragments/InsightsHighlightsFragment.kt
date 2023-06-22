package hr.tvz.android.whatsappinsights.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import hr.tvz.android.whatsappinsights.controller.HighlightsController
import hr.tvz.android.whatsappinsights.databinding.FragmentInsightsRecordsBinding
import hr.tvz.android.whatsappinsights.model.InsightsGenerator
import hr.tvz.android.whatsappinsights.model.MessageDatabase
import hr.tvz.android.whatsappinsights.model.MessageRepository
import hr.tvz.android.whatsappinsights.view.IHighlightsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InsightsHighlightsFragment : Fragment(), IHighlightsView {
    private lateinit var binding: FragmentInsightsRecordsBinding
    private lateinit var highlightsController: HighlightsController
    private val database by lazy { MessageDatabase.getDatabase(this.requireContext()) }
    private val repository by lazy { MessageRepository(database.messageDao()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsightsRecordsBinding.inflate(inflater, container, false)
        highlightsController = HighlightsController(this)

        CoroutineScope(Dispatchers.IO).launch {
            val messages = repository.allMessages()
            val insights = InsightsGenerator(messages)
            withContext(Dispatchers.Main){
                val n = 10
                val rows = highlightsController.generateTopViewsFromMap(insights.getTopN(n))
                setTopDays(rows, n)

            }
        }
        return binding.root
    }

    override fun setTopDays(rows: List<TableRow>, n: Int) {
        rows.forEach { binding.recordsTop5.addView(it) }
    }

    override fun getFragmentContext(): Context {
        return requireContext()
    }
}