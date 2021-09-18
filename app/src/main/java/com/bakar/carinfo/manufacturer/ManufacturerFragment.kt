package com.bakar.carinfo.manufacturer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakar.carinfo.R
import com.bakar.carinfo.databinding.FragmentManufacturerBinding
import com.bakar.carinfo.util.ext.showOrHide
import com.bakar.carinfo.util.ext.viewBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
class ManufacturerFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory
) : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this, factory).get(ManufacturerViewModel::class.java)
    }
    private val adapter by lazy { ManufactureListAdapter(viewModel.state.onItemClick) }
    private val loadingErrorStateAdapter by lazy { LoadingErrorStateAdapter() }

    private lateinit var binding: FragmentManufacturerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = viewBinding(
            R.layout.fragment_manufacturer,
            container
        )
        binding.toolbarLayout.toolbar.navigationIcon = null
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()

        lifecycleScope.launch {
            viewModel.data.collect {
                adapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                val isInitialLoading =
                    loadState.source.refresh is LoadState.Loading && adapter.itemCount == 0
                binding.progress.showOrHide(isInitialLoading)
                val isInitialError =
                    loadState.source.refresh is LoadState.Error && adapter.itemCount == 0
                binding.lblError.showOrHide(isInitialError)
            }

        }
    }

    private fun setupRecyclerview() {
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ManufacturerFragment.adapter.withLoadStateFooter(
                footer = loadingErrorStateAdapter
            )
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }
}

