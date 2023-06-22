package hr.tvz.android.whatsappinsights.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hr.tvz.android.whatsappinsights.R
import hr.tvz.android.whatsappinsights.databinding.FragmentInsightsRecordsBinding
import hr.tvz.android.whatsappinsights.model.InsightsGenerator
import hr.tvz.android.whatsappinsights.model.MessageDatabase
import hr.tvz.android.whatsappinsights.model.MessageRepository
import hr.tvz.android.whatsappinsights.view.RecordsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class InsightsRecordsFragment : Fragment(), RecordsView {
    private lateinit var binding: FragmentInsightsRecordsBinding
    private val database by lazy { MessageDatabase.getDatabase(this.requireContext()) }
    private val repository by lazy { MessageRepository(database.messageDao()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsightsRecordsBinding.inflate(inflater, container, false)
        CoroutineScope(Dispatchers.IO).launch {
            val messages = repository.allMessages()
            val insights = InsightsGenerator(messages)
            withContext(Dispatchers.Main){
                setTop5(insights.getTopN(5))
            }
        }
        return binding.root
    }

    override fun setTop5(map: Map<LocalDate, Int>) {
        val topBuilder = StringBuilder("TOP5\n")
        for((day,count) in map){
            topBuilder.append("$day - $count\n")
        }
        val text = topBuilder.toString()
        binding.recordsTop5.text = text
    }
}