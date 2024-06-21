import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.passwordmanager.passwordwallet.PasswordEntry

class PasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PasswordRepository = PasswordRepository(application)
//    val allPasswords: LiveData<List<PasswordEntry>>

    init {
//        allPasswords = repository.allPasswords
    }

    fun insert(passwordEntry: PasswordEntry) {
        repository.insert(passwordEntry)
    }
}
