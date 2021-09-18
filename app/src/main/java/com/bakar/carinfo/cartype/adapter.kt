package com.bakar.carinfo.cartype

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bakar.carinfo.R
import com.bakar.carinfo.databinding.ItemCarTypeBinding
import com.bakar.carinfo.manufacturer.ROW_EVEN
import com.bakar.carinfo.manufacturer.ROW_ODD


class CarTypeAdapter(
    private val onClick: (CarTypeItem) -> Unit
) : ListAdapter<CarTypeItem, CarTypeViewHolder>(CarTypeDiffUtil) {

    override fun getItemViewType(position: Int) =
        if (position % 2 == 0) ROW_EVEN else ROW_ODD

    override fun onBindViewHolder(holder: CarTypeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarTypeViewHolder {
        return CarTypeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_car_type,
                parent,
                false
            ),
            viewType = viewType,
            onClick = onClick
        )
    }

}

class CarTypeViewHolder(
    private val binding: ItemCarTypeBinding,
    private val viewType: Int,
    private val onClick: (item: CarTypeItem) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setBackgroundColor(if (viewType == ROW_EVEN) Color.LTGRAY else Color.WHITE)
    }

    fun bind(item: CarTypeItem?) {
        binding.lblTitle.text = item?.name
        binding.root.setOnClickListener {
            onClick(item!!)
        }
    }
}


object CarTypeDiffUtil : DiffUtil.ItemCallback<CarTypeItem>() {
    override fun areItemsTheSame(oldItem: CarTypeItem, newItem: CarTypeItem): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: CarTypeItem, newItem: CarTypeItem): Boolean =
        oldItem == newItem
}