package hr.tvz.android.whatsappinsights.controller

import hr.tvz.android.whatsappinsights.model.Insights
import hr.tvz.android.whatsappinsights.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

interface IInsightController {
    suspend fun loadData(flow: Flow<Message>)
    fun getData(): List<Message>
}

class InsightController: IInsightController{
    var messages = listOf<Message>()
    lateinit var insights: Insights

    override suspend fun loadData(flow: Flow<Message>){
        messages = flow.toList()
        insights = Insights(messages)
    }

    override fun getData(): List<Message> {
        return messages
    }
}