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
                val nDays = 10
                val dayRows = highlightsController.generateTopDaysRowsFromMap(insights.getTopN(nDays))
                setTopDays(dayRows)
                val nEmojis = 10
                val emojiRows = highlightsController.generateTopEmojiRowsFromMap(insights.getTopEmojis(nEmojis))
                setTopEmojis(emojiRows)
            }
        }
        return binding.root
    }

    override fun setTopDays(rows: List<TableRow>) {
        rows.forEach { binding.highlightsTopDays.addView(it) }
    }

    override fun setTopEmojis(rows: List<TableRow>) {
        rows.forEach { binding.highlightsTopEmojis.addView(it) }
    }

    override fun getFragmentContext(): Context {
        return requireContext()
    }
}