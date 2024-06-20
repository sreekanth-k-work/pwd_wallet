package com.example.passwordmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.passwordmanager.passwordwallet.R

class PasswordAdapter(private var passwords: List<Password>) :
    RecyclerView.Adapter<PasswordAdapter.ViewHolder>() {

    fun updatePasswords(passwords: List<Password>) {
        this.passwords = passwords
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.password_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val password = passwords[position]
        holder.bind(password)
    }

    override fun getItemCount(): Int {
        return passwords.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val urlTextView: TextView       = itemView.findViewById(R.id.urlTextView)
        private val usernameTextView: TextView  = itemView.findViewById(R.id.usernameTextView)

        fun bind(password: Password) {
            urlTextView.text = password.url
            usernameTextView.text = password.username
        }
    }
}
