package com.bakar.carinfo.builtdate

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bakar.carinfo.R
import com.bakar.carinfo.databinding.ItemCarBuiltDateBinding
import com.bakar.carinfo.manufacturer.ROW_EVEN
import com.bakar.carinfo.manufacturer.ROW_ODD


class CarBuiltDateAdapter(
    private val onClick: (CarBuiltDateItem) -> Unit
) : ListAdapter<CarBuiltDateItem, CarBuiltDateViewHolder>(CarBuiltDateDiffUtil) {

    override fun getItemViewType(position: Int) =
        if (position % 2 == 0) ROW_EVEN else ROW_ODD

    override fun onBindViewHolder(holder: CarBuiltDateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarBuiltDateViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_car_built_date,
            parent,
            false
        ),
        viewType = viewType,
        onClick = onClick
    )
}

class CarBuiltDateViewHolder(
    private val binding: ItemCarBuiltDateBinding,
    private val viewType: Int,
    private val onClick: (item: CarBuiltDateItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setBackgroundColor(if (viewType == ROW_EVEN) Color.LTGRAY else Color.WHITE)
    }

    fun bind(item: CarBuiltDateItem?) {
        binding.lblTitle.text = item?.date
        binding.root.setOnClickListener {
            onClick(item!!)
        }
    }
}


object CarBuiltDateDiffUtil : DiffUtil.ItemCallback<CarBuiltDateItem>() {
    override fun areItemsTheSame(oldItem: CarBuiltDateItem, newItem: CarBuiltDateItem): Boolean =
        oldItem.date == newItem.date

    override fun areContentsTheSame(oldItem: CarBuiltDateItem, newItem: CarBuiltDateItem): Boolean =
        oldItem == newItem
}