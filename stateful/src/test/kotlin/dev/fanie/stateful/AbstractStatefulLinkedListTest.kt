package dev.fanie.stateful

import dev.fanie.stateful.util.Announcement
import dev.fanie.stateful.util.FakeStatefulLinkedList
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AbstractStatefulLinkedListTest {
    @Test
    fun `given an initial instance is provided, when initializing, then the initial instance is announced`() {
        val initialInstance = "test-instance"
        val underTest = FakeStatefulLinkedList(initialInstance)

        assertEquals(Announcement(null, initialInstance), underTest.state.getLatest())
    }

    @Test
    fun `given an initial instance is not provided, when accepting a new instance, then the announcement is the expected`() {
        val underTest = FakeStatefulLinkedList<String>()

        val newInstance = "new-test-instance"
        underTest.accept(newInstance)

        assertEquals(Announcement(null, newInstance), underTest.state.getLatest())
    }

    @Test
    fun `given an initial instance is provided, when accepting a new instance, then the announcement is the expected`() {
        val initialInstance = "test-instance"
        val underTest = FakeStatefulLinkedList(initialInstance)

        val newInstance = "new-test-instance"
        underTest.accept(newInstance)

        assertEquals(Announcement(initialInstance, newInstance), underTest.state.getLatest())
    }

    @Test
    fun `given the state has been cleared, when accepting a new instance, then the announcement is the expected`() {
        val underTest = FakeStatefulLinkedList("test-instance")
        underTest.clear()

        val newInstance = "new-test-instance"
        underTest.accept(newInstance)

        assertEquals(Announcement(null, newInstance), underTest.state.getLatest())
    }

    @Test
    fun `given linked list is empty, when rolling back, then the move fails`() {
        val underTest = FakeStatefulLinkedList<String>()

        assertFalse(underTest.back())
    }

    @Test
    fun `given linked list has just one item, when rolling back, then the move fails`() {
        val underTest = FakeStatefulLinkedList("test-instance")

        assertFalse(underTest.back())
    }

    @Test
    fun `given linked list has more than one item, when rolling back, then the move succeeds`() {
        val underTest = FakeStatefulLinkedList("test-instance")
        underTest.accept("new-test-instance")

        assertTrue(underTest.back())
    }

    @Test
    fun `given linked list has more than one item, when rolling back, then the move is the expected`() {
        val firstInstance = "test-instance"
        val secondInstance = "new-test-instance"
        val underTest = FakeStatefulLinkedList(firstInstance)
        underTest.accept(secondInstance)

        underTest.back()

        assertEquals(Announcement(secondInstance, firstInstance), underTest.state.getLatest())
    }


    @Test
    fun `given linked list is empty, when rolling forth, then the move fails`() {
        val underTest = FakeStatefulLinkedList<String>()

        assertFalse(underTest.forth())
    }

    @Test
    fun `given linked list has just one item, when rolling forth, then the move fails`() {
        val underTest = FakeStatefulLinkedList("test-instance")

        assertFalse(underTest.forth())
    }

    @Test
    fun `given linked list has no items ahead, when rolling forth, then the move fails`() {
        val underTest = FakeStatefulLinkedList("test-instance")
        underTest.accept("new-test-instance")

        assertFalse(underTest.forth())
    }

    @Test
    fun `given linked list has at least one item ahead, when rolling forth, then the move succeeds`() {
        val underTest = FakeStatefulLinkedList("test-instance")
        underTest.accept("new-test-instance")
        underTest.back()

        assertTrue(underTest.forth())
    }

    @Test
    fun `given linked list has more than one item, when rolling back and forth, then the announcements are the expected`() {
        val firstInstance = "test-instance"
        val secondInstance = "new-test-instance"
        val underTest = FakeStatefulLinkedList(firstInstance)
        underTest.accept(secondInstance)

        underTest.back()
        underTest.forth()

        assertEquals(
            listOf(
                Announcement(null, firstInstance),
                Announcement(firstInstance, secondInstance),
                Announcement(secondInstance, firstInstance),
                Announcement(firstInstance, secondInstance)
            ), underTest.state.all()
        )
    }
}