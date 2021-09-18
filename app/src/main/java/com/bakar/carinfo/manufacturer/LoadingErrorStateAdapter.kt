package com.bakar.carinfo.manufacturer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bakar.carinfo.R
import com.bakar.carinfo.databinding.LoadingErrorLayoutBinding
import com.bakar.carinfo.util.ext.showOrHide

class LoadingErrorStateAdapter : LoadStateAdapter<LoadingErrorStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.view.title.showOrHide(loadState is LoadState.Error)
        holder.view.progress.showOrHide(loadState == LoadState.Loading)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.loading_error_layout,
                parent,
                false
            )
        )
    }

    class ViewHolder(val view: LoadingErrorLayoutBinding) :
        RecyclerView.ViewHolder(view.root)
}