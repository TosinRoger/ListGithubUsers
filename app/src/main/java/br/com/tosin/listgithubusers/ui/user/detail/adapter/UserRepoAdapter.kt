package br.com.tosin.listgithubusers.ui.user.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.tosin.listgithubusers.R
import br.com.tosin.listgithubusers.data.model.UserRepo
import br.com.tosin.listgithubusers.ui.utils.defaultDisplayOfStringEmpty
import br.com.tosin.listgithubusers.ui.utils.toFormatViewHolder

class UserRepoAdapter(
    private val userRepoList: List<UserRepo>
) : RecyclerView.Adapter<UserRepoViewHolder>() {

    override fun getItemCount(): Int = userRepoList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRepoViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_user_repo, parent, false)
        return UserRepoViewHolder(view)
    }


    override fun onBindViewHolder(holder: UserRepoViewHolder, position: Int) {
        val userRepo = userRepoList[position]

        holder.projectName.text = userRepo.name
        holder.language.text = userRepo.language.defaultDisplayOfStringEmpty()
        holder.description.text = userRepo.description.defaultDisplayOfStringEmpty()
        holder.numStars.text = userRepo.stargazersCount.toString().defaultDisplayOfStringEmpty()
        holder.numFork.text = userRepo.forks.toString().defaultDisplayOfStringEmpty()

        holder.lastUpdated.text = holder
            .itemView
            .resources
            .getString(R.string.last_updated, userRepo.updatedAt.toFormatViewHolder())
    }
}
