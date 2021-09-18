package com.bakar.carinfo.manufacturer

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bakar.baselib.ErrorLog
import com.bakar.carinfo.service.AutoService
import javax.inject.Inject

const val PAGE_SIZE = 15
const val DEFAULT_PAGE_INDEX = 0

class ManufacturerPagingSource @Inject constructor(
    private val service: AutoService,
    private val errorLog: ErrorLog,
) : PagingSource<Int, ManufacturerItem>() {

    override fun getRefreshKey(state: PagingState<Int, ManufacturerItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ManufacturerItem> =
        runCatching {
            val page = params.key ?: DEFAULT_PAGE_INDEX
            val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page.minus(1)
            val response = service.manufacturesList(page = page, pageSize = PAGE_SIZE)
            //-1 to start from 0
            val nextKey =
                if (response.totalPageCount - response.page - 1 > 0) response.page.plus(1) else null
            LoadResult.Page(
                data = response.manufacturers.map { ManufacturerItem(it.key, it.value) },
                prevKey = prevKey,
                nextKey = nextKey
            )
        }.getOrElse {
            errorLog.log(it)
            LoadResult.Error(it)
        }
}