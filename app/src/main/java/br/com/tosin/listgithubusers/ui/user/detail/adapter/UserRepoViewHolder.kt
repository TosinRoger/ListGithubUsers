package br.com.tosin.listgithubusers.ui.user.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.tosin.listgithubusers.databinding.ItemUserRepoBinding

class UserRepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemUserRepoBinding.bind(itemView)

    val projectName = binding.textViewItemUserRepoName
    val language = binding.textViewItemUserRepoLanguage
    val lastUpdated = binding.textViewItemUserRepoLastUpdated
    val description = binding.textViewItemUserRepoDescription
    val numStars = binding.textViewItemUserRepoNumStars
    val numFork = binding.textViewItemUserRepoNumForks
}
