package com.bakar.carinfo.manufacturer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.bakar.baselib.Navigator

import javax.inject.Inject

class ManufacturerViewModel @Inject constructor(
    private val source: ManufacturerPagingSource,
    private val navigator: Navigator,
) : ViewModel() {

    val data = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = { source }
    ).flow.cachedIn(viewModelScope)

    val state = ManufactureViewState(
        onItemClick = {
            navigator.goToCarType(it)
        }
    )
}

data class ManufactureViewState(
    val onItemClick: (item: ManufacturerItem) -> Unit
)

data class ManufacturerItem(
    val key: String,
    val name: String
)