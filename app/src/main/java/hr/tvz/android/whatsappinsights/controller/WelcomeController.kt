package hr.tvz.android.whatsappinsights.controller

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import hr.tvz.android.whatsappinsights.model.Message
import hr.tvz.android.whatsappinsights.model.MessageDatabase
import hr.tvz.android.whatsappinsights.model.MessageRepository
import hr.tvz.android.whatsappinsights.view.IWelcomeView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface IWelcomeController {
    fun onLoad(activity: Activity)
    fun parseFile(uri: Uri?, cacheDir: File, contentResolver: ContentResolver)
    fun getFileNameFromUri(uri: Uri?, contentResolver: ContentResolver): String

    fun loadPrevious()
}

class WelcomeController(private val welcomeView: IWelcomeView): IWelcomeController {
    private val database by lazy { MessageDatabase.getDatabase(welcomeView.getContext()) }
    private val repository by lazy { MessageRepository(database.messageDao()) }
    override fun onLoad(activity: Activity) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "text/plain" }
        activity.startActivityForResult(intent, REQUEST_FILE)
    }

    override fun parseFile(uri: Uri?, cacheDir: File, contentResolver: ContentResolver) {
        val file: File? = getFileFromUri(uri, cacheDir)
        val fileName = getFileNameFromUri(uri, contentResolver)
        welcomeView.onFileLoadStart()
        Thread{
            if(file == null){
                Toast.makeText(welcomeView.getContext(),
                    "Error opening file!",
                    Toast.LENGTH_SHORT).show()
                return@Thread
            }
            val regex = Regex("^\\d{1,2}/\\d{1,2}/\\d{2},\\s\\d{1,2}:\\d{2}\\u202F[AP]M.*")

            val parsedMessages = mutableListOf<Message>()
            var text = ""
            for(message in file.readLines()){
                if(message.matches(Regex("^\\d{1,2}/\\d{1,2}/\\d{2},\\s\\d{1,2}:\\d{2}\\u202F[AP]M - Messages and calls are end-to-end encrypted. No one outside of this chat, not even WhatsApp, can read or listen to them. Tap to learn more."))
                    || message.matches(Regex("^\\d{1,2}\\/\\d{1,2}\\/\\d{2},\\s\\d{1,2}:\\d{2}\\u202F[AP]M\\s-\\s[^:]*")))
                    continue
                if(message.matches(regex)){
                    parsedMessages.add(parseMessage(message))
                }
                else parsedMessages.last().appendToMessage(message)
            }
            welcomeView.onFileLoaded(parsedMessages, fileName)
        }.start()
    }

    private fun getFileFromUri(uri: Uri?, cacheDir: File): File?{
        uri?.let {
            val contentResolver = welcomeView.getContext().contentResolver
            val inputStream = contentResolver.openInputStream(it)
            val tempFile = File.createTempFile("temp", ".txt", cacheDir)
            inputStream?.use { input ->
                tempFile.outputStream().use { fileOut ->
                    input.copyTo(fileOut)
                }
            }
            return tempFile
        }
        return null
    }

    override fun getFileNameFromUri(uri: Uri?, contentResolver: ContentResolver): String {
        var filename: String? = null

        if (uri?.scheme == "file") {
            // If the URI is a file URI, extract the filename from the path
            filename = uri.lastPathSegment
        } else {
            // If the URI is a content URI, query the media provider to get the filename
            var cursor: Cursor? = null
            try {
                cursor = contentResolver.query(uri!!, null, null, null, null)
                cursor?.let {
                    if (it.moveToFirst()) {
                        val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (displayNameIndex != -1) {
                            filename = it.getString(displayNameIndex)
                        }
                    }
                }
            } finally {
                cursor?.close()
            }
        }

        return filename ?: "Unknown filename"
    }

    override fun loadPrevious() {
        CoroutineScope(Dispatchers.IO).launch {
            if(repository.messageCount().compareTo(0) == 0){
                Log.w("TEST", "IF")
                withContext(Dispatchers.Main){
                    Toast.makeText(welcomeView.getContext(), "No previous file found. Please load a new one.", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Log.w("TEST", "ELSE")
                welcomeView.onPreviousLoaded()
            }
        }
    }

    fun parseMessage(message: String): Message {
        val dateTimeString = message.split(" - ")[0]
        val senderMessage = message.split(" - ")[1]

        // Parse DateTime
        val formatter = DateTimeFormatter.ofPattern("M/d/yy, h:mm\u202Fa")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)

        //Parse Sender
        val sender = senderMessage.split(":", limit=2)[0]
        val message = senderMessage.split(":", limit=2)[1]

        return Message(dateTime.toString(), sender, message)
    }

    companion object {
        const val REQUEST_FILE = 1
    }
}