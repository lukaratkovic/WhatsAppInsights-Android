package hr.tvz.android.whatsappinsights.controller

import hr.tvz.android.whatsappinsights.model.InsightsGenerator
import hr.tvz.android.whatsappinsights.model.Message
import hr.tvz.android.whatsappinsights.model.MessageRepository
import hr.tvz.android.whatsappinsights.view.IInsightView

interface IInsightController {
    suspend fun loadData(repository: MessageRepository)
    fun getData(): List<Message>
    fun getinsightsGenerator(): InsightsGenerator
}

class InsightController(private val insightView: IInsightView): IInsightController{
    var messages = listOf<Message>()
    lateinit var insightsGenerator: InsightsGenerator

    override suspend fun loadData(repository: MessageRepository){
        messages = repository.allMessages()
        insightsGenerator = InsightsGenerator(messages)
    }

    override fun getData(): List<Message> {
        return messages
    }

    override fun getinsightsGenerator(): InsightsGenerator = insightsGenerator
}