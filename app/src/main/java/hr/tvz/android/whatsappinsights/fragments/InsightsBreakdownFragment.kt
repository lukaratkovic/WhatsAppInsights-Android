package hr.tvz.android.whatsappinsights.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hr.tvz.android.whatsappinsights.R
import hr.tvz.android.whatsappinsights.controller.BreakdownController
import hr.tvz.android.whatsappinsights.databinding.FragmentInsightsBreakdownBinding
import hr.tvz.android.whatsappinsights.model.InsightsGenerator
import hr.tvz.android.whatsappinsights.model.MessageDatabase
import hr.tvz.android.whatsappinsights.model.MessageRepository
import hr.tvz.android.whatsappinsights.view.IBreakdownView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class InsightsBreakdownFragment : Fragment(), IBreakdownView {
    private lateinit var binding: FragmentInsightsBreakdownBinding
    private lateinit var breakdownController: BreakdownController
    private val database by lazy { MessageDatabase.getDatabase(this.requireContext()) }
    private val repository by lazy { MessageRepository(database.messageDao()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsightsBreakdownBinding.inflate(inflater, container, false)
        breakdownController = BreakdownController(this)

        CoroutineScope(Dispatchers.IO).launch {
            val messages = repository.allMessages()
            val insights = InsightsGenerator(messages)
            withContext(Dispatchers.Main){
                setTimeOfDayValues(insights.getByTimeOfDay())
                val view = breakdownController.generateViewFromMap(insights.getMonthlyBreakdown())
                addToMonthlyBreakdown(view)
            }
        }

        return binding.root
    }

    override fun setTimeOfDayValues(map: Map<String,Int>) {
        val morningText = "${map["Morning"]} (${(map["Morning"]!!.toDouble()/map.values.sum()*100).roundToInt()}%)"
        val dayText = "${map["Day"]} (${(map["Day"]!!.toDouble()/map.values.sum()*100).roundToInt()}%)"
        val eveningText = "${map["Evening"]} (${(map["Evening"]!!.toDouble()/map.values.sum()*100).roundToInt()}%)"
        val nightText = "${map["Night"]} (${(map["Night"]!!.toDouble()/map.values.sum()*100).roundToInt()}%)"

        binding.breakdownMorning.text = morningText
        binding.breakdownDay.text = dayText
        binding.breakdownEvening.text = eveningText
        binding.breakdownNight.text = nightText
    }

    override fun addToMonthlyBreakdown(view: View) {
        binding.breakdownMonthly.addView(view)
    }

    override fun getFragmentContext(): Context {
        return this.requireContext()
    }

    override fun getBinding(): FragmentInsightsBreakdownBinding = binding
}