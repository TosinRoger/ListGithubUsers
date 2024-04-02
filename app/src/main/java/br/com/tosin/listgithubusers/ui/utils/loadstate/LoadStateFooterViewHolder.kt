package br.com.tosin.listgithubusers.ui.utils.loadstate

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.tosin.listgithubusers.databinding.ItemLoadStateFooterBinding

class LoadStateFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemLoadStateFooterBinding.bind(itemView)

    val progress = binding.progressBarItemLoadStateFooter
}
