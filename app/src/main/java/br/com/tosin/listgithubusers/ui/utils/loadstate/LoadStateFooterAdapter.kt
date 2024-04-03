package br.com.tosin.listgithubusers.ui.utils.loadstate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import br.com.tosin.listgithubusers.R

class LoadStateFooterAdapter : LoadStateAdapter<LoadStateFooterViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateFooterViewHolder, loadState: LoadState) {
        holder.progress.isVisible = loadState == LoadState.Loading
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateFooterViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.item_load_state_footer, parent, false)
        return LoadStateFooterViewHolder(view)
    }
}
