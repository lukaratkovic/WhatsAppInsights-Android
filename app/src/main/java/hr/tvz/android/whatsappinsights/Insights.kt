package hr.tvz.android.whatsappinsights

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.tvz.android.whatsappinsights.controller.IInsightController
import hr.tvz.android.whatsappinsights.controller.IWelcomeController
import hr.tvz.android.whatsappinsights.controller.InsightController
import hr.tvz.android.whatsappinsights.databinding.ActivityInsightsBinding
import hr.tvz.android.whatsappinsights.model.MessageDatabase
import hr.tvz.android.whatsappinsights.model.MessageRepository
import hr.tvz.android.whatsappinsights.view.IInsightView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Insights : AppCompatActivity(), IInsightView {
    private lateinit var binding: ActivityInsightsBinding
    private val database by lazy { MessageDatabase.getDatabase(this) }
    val repository by lazy { MessageRepository(database.messageDao()) }
    private lateinit var insightController: IInsightController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsightsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeTitle(intent.getStringExtra("TITLE") ?: "Unknown title")
        CoroutineScope(Dispatchers.Main).launch {
            insightController.loadData(repository.allMessages)
        }
    }

    override fun changeTitle(title: String) {
        this.title = title
    }

    override fun showData() {
        TODO("Not yet implemented")
    }
}