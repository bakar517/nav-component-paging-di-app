package com.bakar.carinfo.manufacturer

import android.graphics.Color
import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bakar.carinfo.R
import com.bakar.carinfo.databinding.ItemManufacturerBinding

const val ROW_ODD = 1
const val ROW_EVEN = 2

class ManufactureListAdapter(
    private val onClick: (item: ManufacturerItem) -> Unit
) :
    PagingDataAdapter<ManufacturerItem, ManufacturerViewHolder>(ManufacturerItemDiffUtil) {

    override fun getItemViewType(position: Int) = if (position % 2 == 0) ROW_EVEN else ROW_ODD

    override fun onBindViewHolder(holder: ManufacturerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManufacturerViewHolder {
        return ManufacturerViewHolder(
            DataBindingUtil.inflate(
                from(parent.context),
                R.layout.item_manufacturer,
                parent,
                false
            ),
            viewType = viewType,
            onClick = onClick
        )
    }
}


class ManufacturerViewHolder(
    private val binding: ItemManufacturerBinding,
    private val viewType: Int,
    private val onClick: (item: ManufacturerItem) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setBackgroundColor(if (viewType == ROW_EVEN) Color.LTGRAY else Color.WHITE)
    }

    fun bind(item: ManufacturerItem?) {
        binding.lblTitle.text = item?.name
        binding.root.setOnClickListener {
            onClick(item!!)
        }
    }
}


object ManufacturerItemDiffUtil : DiffUtil.ItemCallback<ManufacturerItem>() {
    override fun areItemsTheSame(oldItem: ManufacturerItem, newItem: ManufacturerItem): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: ManufacturerItem, newItem: ManufacturerItem): Boolean =
        oldItem == newItem
}