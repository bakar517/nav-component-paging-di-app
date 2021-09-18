package com.bakar.carinfo.builtdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakar.baselib.ErrorLog
import com.bakar.baselib.Navigator
import com.bakar.carinfo.navigation.popBackStack
import com.bakar.carinfo.service.AutoService
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class CarBuiltDateViewModel @AssistedInject constructor(
    private val service: AutoService,
    private val errorLog: ErrorLog,
    private val navigator: Navigator,
    @Assisted private val args: CarTypeArgs
) : ViewModel() {

    private val _state = MutableLiveData(ViewState(
        onTapBackButton = { navigator.popBackStack() }
    ))
    val state: LiveData<ViewState>
        get() = _state

    init {
        loadCarTypes(manufacturerId = args.manufacturer.id, carTypeId = args.carTypeId)
    }

    private fun loadCarTypes(manufacturerId: String, carTypeId: String) {
        _state.value = _state.value!!.copy(requestState = ViewState.RequestState.Loading)

        viewModelScope.launch {
            runCatching {
                service.carBuiltDates(manufacturerId = manufacturerId, carTypeId = carTypeId)
            }.onFailure {
                _state.value = _state.value!!.copy(requestState = ViewState.RequestState.Error)
                errorLog.log(it)
            }.onSuccess {
                _state.value =
                    _state.value!!.copy(
                        requestState = ViewState.RequestState.Data(
                            it.map { entry -> CarBuiltDateItem(entry.key, entry.value) },
                        ),
                        onTapBackButton = {
                            navigator.popBackStack()
                        }
                    )
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(args: CarTypeArgs): CarBuiltDateViewModel
    }

}

data class ViewState(
    val requestState: RequestState = RequestState.Loading,
    val onTapBackButton: () -> Unit
) {
    sealed class RequestState {
        object Loading : RequestState()
        object Error : RequestState()
        data class Data(val list: List<CarBuiltDateItem>) : RequestState()
    }
}

data class CarBuiltDateItem(
    val key: String,
    val date: String,
)