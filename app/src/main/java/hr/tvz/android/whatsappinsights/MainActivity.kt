package hr.tvz.android.whatsappinsights

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.tvz.android.whatsappinsights.controller.IWelcomeController
import hr.tvz.android.whatsappinsights.controller.WelcomeController
import hr.tvz.android.whatsappinsights.databinding.ActivityMainBinding
import hr.tvz.android.whatsappinsights.model.Message
import hr.tvz.android.whatsappinsights.model.MessageDatabase
import hr.tvz.android.whatsappinsights.model.MessageRepository
import hr.tvz.android.whatsappinsights.view.IWelcomeView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), IWelcomeView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var welcomeController: IWelcomeController
    private val database by lazy { MessageDatabase.getDatabase(this) }
    val repository by lazy { MessageRepository(database.messageDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        welcomeController = WelcomeController(this)

        binding.loadButton.setOnClickListener {
            welcomeController.onLoad(this)
        }
        binding.instructionsButton.setOnClickListener { onInstructionsInvoke() }
    }

    override fun onInstructionsInvoke() {
        startActivity(Intent(this, Instructions::class.java))
    }

    override fun onFileLoadStart() {
        val intent = Intent(this, Loading::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    override fun onFileLoaded(messages: MutableList<Message>, fileName: String) {
        val scope = this
        database.clearAllTables()
        CoroutineScope(Dispatchers.Main).launch {
            repository.insertMessage(messages)
            startActivity(Intent(scope, Insights::class.java).apply{putExtra("TITLE", fileName)})
        }
    }

    override fun getContext(): Context {
        return this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == WelcomeController.REQUEST_FILE && resultCode == Activity.RESULT_OK){
            welcomeController.parseFile(data?.data, cacheDir, contentResolver)
        }
    }
}