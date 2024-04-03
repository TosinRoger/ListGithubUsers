package br.com.tosin.listgithubusers.ui.user.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.tosin.listgithubusers.R
import br.com.tosin.listgithubusers.data.model.User
import br.com.tosin.listgithubusers.ui.utils.onItemClicked
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class UserAdapter(
    private val delegateItemClicked: onItemClicked
) : PagingDataAdapter<User, UserViewHolder>(diffCallback) {
    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id &&
                        oldItem.login == newItem.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.item_user_list, parent, false)
        return UserViewHolder(view)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { user ->
            holder.name.text = user.login

            Glide
                .with(holder.itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_user_rounded)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.photo)

            holder.itemView.setOnClickListener {
                delegateItemClicked(user.login)
            }
        }
    }
}
