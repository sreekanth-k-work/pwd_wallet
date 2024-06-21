import android.app.Application
import androidx.room.Room.databaseBuilder
import com.passwordmanager.passwordwallet.AppDatabase
import com.passwordmanager.passwordwallet.PasswordDao
import com.passwordmanager.passwordwallet.PasswordEntry

class PasswordRepository(application: Application?) {
    private val passwordDao: PasswordDao

    init {
        val db =
            databaseBuilder(application!!, AppDatabase::class.java, "password_database").build()
        passwordDao = db.passwordDao()
    }

    fun insert(passwordEntry: PasswordEntry?) {
        Thread { passwordDao.insert(passwordEntry) }.start()
    }

    val allPasswords: List<PasswordEntry>
        get() = passwordDao.allPasswords
}