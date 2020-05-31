package dev.fanie.stateful

import dev.fanie.stateful.util.Announcement
import dev.fanie.stateful.util.FakeStatefulInstance
import org.junit.Assert.assertEquals
import org.junit.Test

internal class AbstractStatefulInstanceTest {
    @Test
    fun `given an initial instance is provided, when initializing, then the initial instance is announced`() {
        val initialInstance = "test-instance"
        val underTest = FakeStatefulInstance(initialInstance)

        assertEquals(Announcement(null, initialInstance), underTest.state.getLatest())
    }

    @Test
    fun `given an initial instance is not provided, when accepting a new instance, then the announcement is the expected`() {
        val underTest = FakeStatefulInstance<String>()

        val newInstance = "new-test-instance"
        underTest.accept(newInstance)

        assertEquals(Announcement(null, newInstance), underTest.state.getLatest())
    }

    @Test
    fun `given an initial instance is provided, when accepting a new instance, then the announcement is the expected`() {
        val initialInstance = "test-instance"
        val underTest = FakeStatefulInstance(initialInstance)

        val newInstance = "new-test-instance"
        underTest.accept(newInstance)

        assertEquals(Announcement(initialInstance, newInstance), underTest.state.getLatest())
    }

    @Test
    fun `given the state has been cleared, when accepting a new instance, then the announcement is the expected`() {
        val underTest = FakeStatefulInstance("test-instance")
        underTest.clear()

        val newInstance = "new-test-instance"
        underTest.accept(newInstance)

        assertEquals(Announcement(null, newInstance), underTest.state.getLatest())
    }
}