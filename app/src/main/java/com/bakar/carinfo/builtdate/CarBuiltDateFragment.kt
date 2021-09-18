package com.bakar.carinfo.builtdate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakar.carinfo.R
import com.bakar.carinfo.databinding.FragmentCarBuiltDateBinding
import com.bakar.carinfo.detail.CarDetailArgs
import com.bakar.carinfo.util.ext.append
import com.bakar.carinfo.util.ext.showOrHide
import com.bakar.carinfo.util.ext.viewBinding
import javax.inject.Inject

class CarBuiltDateFragment @Inject constructor(
    val factory: CarBuiltDateViewModel.Factory
) : Fragment() {

    private val args by navArgs<CarBuiltDateFragmentArgs>()

    private lateinit var binding: FragmentCarBuiltDateBinding

    private lateinit var viewModel: CarBuiltDateViewModel

    private val adapter = CarBuiltDateAdapter {
        findNavController().navigate(
            CarBuiltDateFragmentDirections.actionGoToCarDetail(
                it.toNavArgs(args.carTypeInfo)
            )
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = factory.create(args.carTypeInfo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = viewBinding(
            R.layout.fragment_car_built_date,
            container
        )
        binding.lblManufacturerInfo.text =
            getString(R.string.manufacturer_colon).append(args.carTypeInfo.manufacturer.name)

        binding.lblCarTypeInfo.text =
            getString(R.string.choose_car_type).append(args.carTypeInfo.carTypeName)

        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            overScrollMode = View.OVER_SCROLL_NEVER
            adapter = this@CarBuiltDateFragment.adapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {

            binding.toolbarLayout.toolbar.setNavigationOnClickListener { _ -> it.onTapBackButton() }
            binding.progress.showOrHide(it.requestState == ViewState.RequestState.Loading)
            binding.lblError.showOrHide(it.requestState == ViewState.RequestState.Error)
            when (it.requestState) {
                is ViewState.RequestState.Data -> {
                    adapter.submitList(it.requestState.list)
                }
                else -> {
                }
            }
        }
    }

}

private fun CarBuiltDateItem.toNavArgs(carTypeArgs: CarTypeArgs) =
    CarDetailArgs(id = key, name = date, carType = carTypeArgs)