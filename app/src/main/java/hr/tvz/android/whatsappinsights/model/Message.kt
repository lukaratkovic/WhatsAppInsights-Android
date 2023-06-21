package hr.tvz.android.whatsappinsights.model

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.io.Serializable

@Entity(tableName="messages")
data class Message(
    @ColumnInfo(name="time") val time: String,
    @ColumnInfo(name="sender") val sender: String,
    @ColumnInfo(name="message") var message: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
): Serializable{
    fun appendToMessage(message: String){
        this.message += message
    }
    fun time(): LocalDateTime = LocalDateTime.parse(time)
}

@Dao
interface MessageDao{
    @Query("SELECT * FROM messages")
    fun allMessages(): List<Message>

    @Insert
    suspend fun insertMessage(vararg message: Message)
}

class MessageRepository(private val messageDao: MessageDao){
    @WorkerThread
    suspend fun insertMessage(messages: List<Message>) = messageDao.insertMessage(*messages.toTypedArray())

    fun allMessages() = messageDao.allMessages()
}

@Database(entities = [Message::class], version=1)
abstract class MessageDatabase: RoomDatabase(){
    abstract fun messageDao(): MessageDao

    companion object{
        @Volatile
        private var INSTANCE: MessageDatabase? = null

        fun getDatabase(context: Context): MessageDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MessageDatabase::class.java,
                    "messages_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
