package com.bakar.carinfo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bakar.baselib.Navigator
import com.bakar.carinfo.R
import com.bakar.carinfo.databinding.FragmentCarDetailBinding
import com.bakar.carinfo.navigation.popBackStack
import com.bakar.carinfo.util.ext.viewBinding
import javax.inject.Inject

class CarDetailFragment @Inject constructor(
    val navigator: Navigator
) : Fragment() {

    private val args by navArgs<CarDetailFragmentArgs>()

    private lateinit var binding: FragmentCarDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = viewBinding(
            R.layout.fragment_car_detail,
            container
        )
        binding.updateCarDetails()
        return binding.root
    }

    private fun FragmentCarDetailBinding.updateCarDetails() {
        toolbarLayout.toolbar.setNavigationOnClickListener { navigator.popBackStack() }

        with(args.carDetail) {
            lblManufacturerName.text = carType.manufacturer.name
            lblCarTypeName.text = carType.carTypeName
            lblYear.text = name
        }
    }

}