package dev.fanie.stateful

import java.util.Stack

/**
 * Stateful wrapper for a given [Model] type, operating with a stack of instances.
 */
abstract class StatefulStack<Model : Any>(
    initialInstance: Model? = null
) : StatefulUpdateReceiver<Model>, StatefulUpdateNotifier<Model> {
    private val stack: Stack<Model> = Stack()

    init {
        if (initialInstance != null) {
            accept(initialInstance)
        }
    }

    private fun peekInstance() = if (stack.empty()) null else stack.peek()

    final override fun accept(newInstance: Model) {
        val currentInstance = peekInstance()
        announce(currentInstance, newInstance)
        stack.push(newInstance)
    }

    final override fun clear() {
        stack.clear()
    }

    /**
     * Reverts to the previous instance of the [Model] type in the stack, if available.
     * @return {@code true} if a previous instance was available and announced, {@code false} otherwise.
     */
    fun rollback(): Boolean {
        return if (stack.size < 2) {
            false
        } else {
            val currentInstance = stack.pop()
            val rollbackInstance = requireNotNull(peekInstance())
            announce(currentInstance, rollbackInstance)
            true
        }
    }
}
