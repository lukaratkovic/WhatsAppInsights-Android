package hr.tvz.android.whatsappinsights.view

import android.content.Context
import hr.tvz.android.whatsappinsights.model.Message

interface IWelcomeView {
    fun getContext(): Context
    fun onInstructionsInvoke()
    fun onFileLoadStart()
    fun onFileLoaded(messages: MutableList<Message>)
}