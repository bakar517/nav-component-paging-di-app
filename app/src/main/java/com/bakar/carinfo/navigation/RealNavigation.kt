package com.bakar.carinfo.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import com.bakar.baselib.Navigator
import com.bakar.carinfo.di.Retained
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import javax.inject.Inject

const val NO_ACTION = -1

@Retained
class RealNavigation @Inject constructor() : Navigator {

    val channel = Channel<NavDirections>(UNLIMITED)

    override fun navigate(direction: NavDirections) {
        channel.trySend(direction)
    }

}

class PopBackStack : NavDirections {
    override fun getActionId() = NO_ACTION

    override fun getArguments() = bundleOf()
}

fun Navigator.popBackStack() = navigate(PopBackStack())