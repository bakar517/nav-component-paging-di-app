package com.bakar.carinfo.cartype

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

class CarTypeViewModel @AssistedInject constructor(
    private val service: AutoService,
    private val navigator: Navigator,
    private val errorLog: ErrorLog,
    @Assisted private val args: ManufactureArgs
) : ViewModel() {

    private val _state = MutableLiveData(ViewState(
        onTapBackButton = { navigator.popBackStack() }
    ))
    val state: LiveData<ViewState>
        get() = _state

    init {
        loadCarTypes(args.id)
    }

    internal fun performQuery(query: String) {
        _state.value = _state.value!!.copy(query = query)
    }

    private fun loadCarTypes(manufacture: String) {
        _state.value = _state.value!!.copy(state = ViewState.RequestState.Loading)

        viewModelScope.launch {
            runCatching {
                service.carTypeList(manufacture)
            }.onFailure {
                _state.value = _state.value!!.copy(state = ViewState.RequestState.Error)
                errorLog.log(it)
            }.onSuccess {
                _state.value =
                    _state.value!!.copy(
                        state = ViewState.RequestState.Data(
                            it.map { entry -> CarTypeItem(entry.key, entry.value) },
                        )
                    )
            }
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(args: ManufactureArgs): CarTypeViewModel
    }

}

data class ViewState(
    val state: RequestState = RequestState.Loading,
    val query: String = "",
    val onTapBackButton: () -> Unit
) {
    sealed class RequestState {
        object Loading : RequestState()
        object Error : RequestState()
        data class Data(val list: List<CarTypeItem>) : RequestState()
    }
}

data class CarTypeItem(
    val key: String,
    val name: String,
)