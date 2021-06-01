package com.github.maxfie1d.delayed_action

import java.io.Serializable
import java.util.*


class DelayedActions<T : Serializable> : Serializable {
    private val actions = mutableMapOf<DelayedActionId, T>()

    fun new(action: T): DelayedActionId {
        val id = genId()
        actions[id] = action
        return id
    }

    fun pop(actionId: DelayedActionId): T? {
        val action = actions[actionId]
        if (action != null) {
            actions.remove(actionId)
        }
        return action
    }

    fun clear() {
        actions.clear()
    }

    fun count(): Int {
        return actions.size
    }

    private fun genId(): DelayedActionId {
        val id = UUID.randomUUID().toString()
        return DelayedActionId(id)
    }
}
