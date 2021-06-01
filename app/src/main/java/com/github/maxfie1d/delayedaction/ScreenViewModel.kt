package com.github.maxfie1d.delayedaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.maxfie1d.delayed_action.DelayedActionId
import com.github.maxfie1d.delayed_action.DelayedActions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.Serializable

class ScreenViewModel : ViewModel() {
    sealed class DelayedAction : Serializable {
        data class Op1(val data1: Int) : DelayedAction()
        data class Op2(val data2: Int) : DelayedAction()
    }

    private val _openDialog = MutableSharedFlow<DelayedActionId>()
    val a = _openDialog.asSharedFlow()

    private val actions = DelayedActions<DelayedAction>()

    fun executeDelayedAction(id: DelayedActionId) {
        actions.pop(id)?.let(this::execute)
    }

    fun onClickButton() = viewModelScope.launch {
        val id = actions.new(DelayedAction.Op1(123))
        _openDialog.emit(id)
    }

    private fun execute(action: DelayedAction): Unit = when (action) {
        is DelayedAction.Op1 -> {
            println(action.data1)
        }
        is DelayedAction.Op2 -> {
            println(action.data2)
        }
    }
}
