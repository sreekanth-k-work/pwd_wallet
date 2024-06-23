import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.PasswordEntry
import com.passwordmanager.passwordwallet.R


class PasswordListAdapter : RecyclerView.Adapter<PasswordListAdapter.PasswordViewHolder>() {
    private var passwords = emptyList<PasswordEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.password_item, parent, false)
        return PasswordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val current = passwords[position]
        holder.websiteTextView.text  = current.title
        holder.usernameTextView.text = current.username
    }

    override fun getItemCount() = passwords.size

    fun setPasswords(passwords: List<PasswordEntry>) {
        this.passwords = passwords
        notifyDataSetChanged()
    }

    class PasswordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val websiteTextView: TextView  = itemView.findViewById(R.id.id_website_value_tv)
        val usernameTextView: TextView = itemView.findViewById(R.id.id_username_value_tv)
    }
}
