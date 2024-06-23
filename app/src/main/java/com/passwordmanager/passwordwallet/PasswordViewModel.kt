import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.passwordmanager.passwordwallet.PasswordEntry
import kotlinx.coroutines.launch

class PasswordViewModel(application: Application) : AndroidViewModel(application) {
    // Private mutable LiveData
    private val _passwordEntries = MutableLiveData<List<PasswordEntry>>()

    // Public immutable LiveData
    val passwordEntries: LiveData<List<PasswordEntry>>  = _passwordEntries

//    private val passwordEntryDao: PasswordEntry         = AppDatabase.getDatabase(application).passwordEntryDao()
//
//    fun savePasswordEntry(passwordEntry: PasswordEntry) {
//        viewModelScope.launch {
//            passwordEntryDao.insert(passwordEntry)
//            loadPasswordEntries()
//        }
//    }
//
//    fun loadPasswordEntries() {
//        viewModelScope.launch {
//            val entries = fetchPasswordEntriesFromDatabase()
//            _passwordEntries.value = entries
//        }
//    }
//
//    private suspend fun fetchPasswordEntriesFromDatabase(): List<PasswordEntry> {
//        return passwordEntryDao.getAll()
//    }
}
