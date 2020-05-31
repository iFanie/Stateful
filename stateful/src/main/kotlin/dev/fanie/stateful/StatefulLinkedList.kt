package dev.fanie.stateful

import java.util.LinkedList

/**
 * Stateful wrapper for a given [Model] type, operating with a linked list of instances.
 */
interface StatefulLinkedList<Model : Any> : StatefulUpdateReceiver<Model>, StatefulUpdateNotifier<Model> {
    /**
     * Rolls back to the immediate previous instance of the [Model] type in the linked list, if available.
     * @return {@code true} if an immediate previous instance was available and announced, {@code false} otherwise.
     */
    fun back(): Boolean

    /**
     * Rolls forth to the immediate next instance of the [Model] type in the linked list, if available.
     * @return {@code true} if an immediate next instance was available and announced, {@code false} otherwise.
     */
    fun forth(): Boolean
}

/**
 * Partial implementation of the [StatefulLinkedList] interface, without an implementation for the
 * [StatefulUpdateNotifier.announce] function.
 */
abstract class AbstractStatefulLinkedList<Model : Any>(initialInstance: Model? = null) : StatefulLinkedList<Model> {
    private val linkedList: LinkedList<Model> = LinkedList()
    private var currentIndex = -1

    init {
        if (initialInstance != null) {
            accept(initialInstance)
        }
    }

    private fun currentInstance() = if (currentIndex < 0) null else linkedList[currentIndex]

    final override fun accept(newInstance: Model) {
        linkedList.addLast(newInstance)
        val newInstanceIndex = linkedList.lastIndex
        if (newInstanceIndex - currentIndex == 1) {
            val currentInstance = currentInstance()
            announce(currentInstance, newInstance)
            currentIndex = newInstanceIndex
        }
    }

    final override fun clear() {
        linkedList.clear()
        currentIndex = -1
    }

    final override fun back(): Boolean {
        return if (currentIndex - 1 >= 0) {
            announceImmediate(-1)
            true
        } else {
            false
        }
    }

    final override fun forth(): Boolean {
        return if (currentIndex + 1 <= linkedList.lastIndex) {
            announceImmediate(1)
            true
        } else {
            false
        }
    }

    private fun announceImmediate(step: Int) {
        val currentInstance = currentInstance()
        currentIndex += step
        val newInstance = requireNotNull(currentInstance())
        announce(currentInstance, newInstance)
    }
}
