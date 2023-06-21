package hr.tvz.android.whatsappinsights

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.tvz.android.whatsappinsights.controller.IWelcomeController
import hr.tvz.android.whatsappinsights.controller.WelcomeController
import hr.tvz.android.whatsappinsights.view.IWelcomeView
import hr.tvz.android.whatsappinsights.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), IWelcomeView {
    lateinit var binding: ActivityMainBinding
    lateinit var welcomeController: IWelcomeController

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

    override fun getContext(): Context {
        return this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == WelcomeController.REQUEST_FILE && resultCode == Activity.RESULT_OK){
            welcomeController.parseFile(data?.data, cacheDir)
        }
    }
}