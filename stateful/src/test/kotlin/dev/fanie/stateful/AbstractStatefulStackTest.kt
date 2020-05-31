package dev.fanie.stateful

import dev.fanie.stateful.util.Announcement
import dev.fanie.stateful.util.FakeStatefulStack
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AbstractStatefulStackTest {
    @Test
    fun `given an initial instance is provided, when initializing, then the initial instance is announced`() {
        val initialInstance = "test-instance"
        val underTest = FakeStatefulStack(initialInstance)

        assertEquals(Announcement(null, initialInstance), underTest.state.getLatest())
    }

    @Test
    fun `given an initial instance is not provided, when accepting a new instance, then the announcement is the expected`() {
        val underTest = FakeStatefulStack<String>()

        val newInstance = "new-test-instance"
        underTest.accept(newInstance)

        assertEquals(Announcement(null, newInstance), underTest.state.getLatest())
    }

    @Test
    fun `given an initial instance is provided, when accepting a new instance, then the announcement is the expected`() {
        val initialInstance = "test-instance"
        val underTest = FakeStatefulStack(initialInstance)

        val newInstance = "new-test-instance"
        underTest.accept(newInstance)

        assertEquals(Announcement(initialInstance, newInstance), underTest.state.getLatest())
    }

    @Test
    fun `given the state has been cleared, when accepting a new instance, then the announcement is the expected`() {
        val underTest = FakeStatefulStack("test-instance")
        underTest.clear()

        val newInstance = "new-test-instance"
        underTest.accept(newInstance)

        assertEquals(Announcement(null, newInstance), underTest.state.getLatest())
    }

    @Test
    fun `given stack is empty, when rolling back, then the rollback fails`() {
        val underTest = FakeStatefulStack<String>()

        assertFalse(underTest.rollback())
    }

    @Test
    fun `given stack has just one item, when rolling back, then the rollback fails`() {
        val underTest = FakeStatefulStack("test-instance")

        assertFalse(underTest.rollback())
    }

    @Test
    fun `given stack has more than one item, when rolling back, then the rollback succeeds`() {
        val underTest = FakeStatefulStack("test-instance")
        underTest.accept("new-test-instance")

        assertTrue(underTest.rollback())
    }

    @Test
    fun `given stack has more than one item, when rolling back, then the announcement is the expected`() {
        val firstInstance = "test-instance"
        val secondInstance = "new-test-instance"
        val underTest = FakeStatefulStack(firstInstance)
        underTest.accept(secondInstance)

        underTest.rollback()

        assertEquals(Announcement(secondInstance, firstInstance), underTest.state.getLatest())
    }
}
