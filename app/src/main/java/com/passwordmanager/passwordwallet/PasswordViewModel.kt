import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.PasswordEntry
import com.passwordmanager.passwordwallet.AppDatabase
import com.passwordmanager.passwordwallet.PasswordEntryDao
import kotlinx.coroutines.launch

class PasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val _passwordEntries = MutableLiveData<List<PasswordEntry>>()
    val passwordEntries: LiveData<List<PasswordEntry>> = _passwordEntries

    private val passwordEntryDao: PasswordEntryDao =
        AppDatabase.getDatabase(application).passwordEntryDao()

    fun savePasswordEntry(passwordEntry: PasswordEntry) {
        viewModelScope.launch {
            passwordEntryDao.insert(passwordEntry)
            loadPasswordEntries()
        }
    }

    fun loadPasswordEntries() {
        viewModelScope.launch {
            val entries = fetchPasswordEntriesFromDatabase()
            _passwordEntries.postValue(entries)
        }
    }

    private suspend fun fetchPasswordEntriesFromDatabase(): List<PasswordEntry> {
        return passwordEntryDao.getAll()
    }
}
