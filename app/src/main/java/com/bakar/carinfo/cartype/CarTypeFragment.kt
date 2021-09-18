package com.bakar.carinfo.cartype


import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakar.carinfo.R
import com.bakar.carinfo.builtdate.CarTypeArgs
import com.bakar.carinfo.cartype.SearchManager.filterByName
import com.bakar.carinfo.databinding.FragmentCarTypeBinding
import com.bakar.carinfo.util.ext.append
import com.bakar.carinfo.util.ext.showOrHide
import com.bakar.carinfo.util.ext.viewBinding
import javax.inject.Inject


class CarTypeFragment @Inject constructor(
    val factory: CarTypeViewModel.Factory
) : Fragment() {
    private lateinit var binding: FragmentCarTypeBinding
    private val args by navArgs<CarTypeFragmentArgs>()
    private lateinit var viewModel: CarTypeViewModel
    private val adapter = CarTypeAdapter {
        findNavController().navigate(
            CarTypeFragmentDirections.actionGoToCarBuiltDates(it.toNavArgs(args.manufactureInfo))
        )

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = factory.create(args.manufactureInfo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = viewBinding(
            R.layout.fragment_car_type,
            container
        )

        binding.lblManufacturerInfo.text =
            getString(R.string.manufacturer_colon).append(args.manufactureInfo.name)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupToolbarMenu()
        viewModel.state.observe(viewLifecycleOwner) {
            binding.toolbarLayout.toolbar.setNavigationOnClickListener { _ -> it.onTapBackButton() }
            binding.progress.showOrHide(it.state == ViewState.RequestState.Loading)
            binding.lblError.showOrHide(it.state == ViewState.RequestState.Error)
            when (it.state) {
                is ViewState.RequestState.Data -> {
                    val list = if (it.query.isNotEmpty())
                        filterByName(it.query, it.state.list)
                    else it.state.list

                    adapter.submitList(list)
                }
                else -> {
                }
            }
        }

    }

    private fun setupToolbarMenu() {
        binding.toolbarLayout.toolbar.inflateMenu(R.menu.search_menu)
        val menu = binding.toolbarLayout.toolbar.menu
        val menuItem = menu.findItem(R.id.search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //perform the default action
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.performQuery(newText)
                return false
            }

        })
    }

    private fun setupRecyclerView() {
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CarTypeFragment.adapter
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }
}

private fun CarTypeItem.toNavArgs(manufacture: ManufactureArgs) =
    CarTypeArgs(carTypeId = key, carTypeName = name, manufacturer = manufacture)