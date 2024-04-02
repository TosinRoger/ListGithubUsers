package br.com.tosin.listgithubusers.ui.user.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.tosin.listgithubusers.databinding.ItemUserListBinding

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
    private val binding = ItemUserListBinding.bind(itemView)

    val name = binding.textViewItemUserListName
    val photo = binding.imageViewItemUserListPhoto
}
