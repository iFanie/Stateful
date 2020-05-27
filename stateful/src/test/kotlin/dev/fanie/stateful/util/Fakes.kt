package dev.fanie.stateful.util

import dev.fanie.stateful.StatefulInstance
import dev.fanie.stateful.StatefulStack

internal data class Announcement<Model>(
    val currentInstance: Model?,
    val newInstance: Model
)

internal class FakeStatefulInstanceState<Model> {
    private val announcements: MutableList<Announcement<Model>> = mutableListOf()

    fun add(announcement: Announcement<Model>) {
        announcements.add(announcement)
    }

    fun getLatest() = announcements.last()
}

internal class FakeStatefulInstance<Model : Any>(initialInstance: Model? = null) : StatefulInstance<Model>(initialInstance) {
    private lateinit var internalState: FakeStatefulInstanceState<Model>
    val state get() = internalState

    override fun announce(currentInstance: Model?, newInstance: Model) {
        if (!this::internalState.isInitialized) internalState = FakeStatefulInstanceState()
        internalState.add(Announcement(currentInstance, newInstance))
    }
}

internal class FakeStatefulStack<Model : Any>(initialInstance: Model? = null) : StatefulStack<Model>(initialInstance) {
    private lateinit var internalState: FakeStatefulInstanceState<Model>
    val state get() = internalState

    override fun announce(currentInstance: Model?, newInstance: Model) {
        if (!this::internalState.isInitialized) internalState = FakeStatefulInstanceState()
        internalState.add(Announcement(currentInstance, newInstance))
    }
}
