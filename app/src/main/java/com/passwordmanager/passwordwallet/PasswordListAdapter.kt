import android.R
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.PasswordEntry
import com.passwordmanager.passwordwallet.DetailActivity


class PasswordListAdapter : RecyclerView.Adapter<PasswordListAdapter.PasswordViewHolder>() {
    private var passwords = emptyList<PasswordEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(com.passwordmanager.passwordwallet.R.layout.password_item, parent, false)
        return PasswordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val current = passwords[position]
        holder.websiteTextView.text  = current.title
        holder.usernameTextView.text = current.username

        // Handle item click
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                putExtra("PASSWORD_ENTRY", current) // Pass the selected PasswordEntry to DetailActivity
            }
            holder.itemView.context.startActivity(intent)
            (holder.itemView.context as Activity).overridePendingTransition(com.passwordmanager.passwordwallet.R.anim.slide_in_right,com.passwordmanager.passwordwallet.R.anim.stay)

        }
    }

    override fun getItemCount() = passwords.size



    fun setPasswords(passwords: List<PasswordEntry>) {
        this.passwords = passwords
        notifyDataSetChanged()
    }

    class PasswordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val websiteTextView: TextView  = itemView.findViewById(com.passwordmanager.passwordwallet.R.id.id_website_value_tv)
        val usernameTextView: TextView = itemView.findViewById(com.passwordmanager.passwordwallet.R.id.id_username_value_tv)
    }
}
