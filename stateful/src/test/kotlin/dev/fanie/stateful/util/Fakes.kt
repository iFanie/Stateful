package dev.fanie.stateful.util

import dev.fanie.stateful.AbstractStatefulInstance
import dev.fanie.stateful.AbstractStatefulLinkedList
import dev.fanie.stateful.AbstractStatefulStack

internal data class Announcement<Model>(
    val currentInstance: Model?,
    val newInstance: Model
)

internal class StatefulFakeState<Model> {
    private val announcements: MutableList<Announcement<Model>> = mutableListOf()

    fun add(announcement: Announcement<Model>) {
        announcements.add(announcement)
    }

    fun getLatest() = announcements.last()

    fun all() = announcements.toList()
}

internal class FakeStatefulInstance<Model : Any>(
    initialInstance: Model? = null
) : AbstractStatefulInstance<Model>(initialInstance) {
    private lateinit var internalState: StatefulFakeState<Model>
    val state get() = internalState

    override fun announce(currentInstance: Model?, newInstance: Model) {
        if (!this::internalState.isInitialized) internalState = StatefulFakeState()
        internalState.add(Announcement(currentInstance, newInstance))
    }
}

internal class FakeStatefulStack<Model : Any>(
    initialInstance: Model? = null
) : AbstractStatefulStack<Model>(initialInstance) {
    private lateinit var internalState: StatefulFakeState<Model>
    val state get() = internalState

    override fun announce(currentInstance: Model?, newInstance: Model) {
        if (!this::internalState.isInitialized) internalState = StatefulFakeState()
        internalState.add(Announcement(currentInstance, newInstance))
    }
}

internal class FakeStatefulLinkedList<Model : Any>(
    initialInstance: Model? = null
) : AbstractStatefulLinkedList<Model>(initialInstance) {
    private lateinit var internalState: StatefulFakeState<Model>
    val state get() = internalState

    override fun announce(currentInstance: Model?, newInstance: Model) {
        if (!this::internalState.isInitialized) internalState = StatefulFakeState()
        internalState.add(Announcement(currentInstance, newInstance))
    }
}
