package com.bakar.carinfo.util.ext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

inline fun <reified T : ViewBinding> Fragment.viewBinding(
    @LayoutRes id: Int,
    container: ViewGroup?
): T = DataBindingUtil.inflate(
    LayoutInflater.from(requireContext()),
    id, container, false
)