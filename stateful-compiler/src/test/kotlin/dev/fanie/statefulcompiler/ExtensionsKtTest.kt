package dev.fanie.statefulcompiler

import dev.fanie.statefulcompiler.util.executableElement
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ExtensionsKtTest {
    @Test
    fun `given name starts with 'get', when requesting the name extension, then the response is the expected`() {
        val input = executableElement("getSomething")

        val result = input.name

        assertEquals("something", result)
    }

    @Test
    fun `given name does not start with 'get', when requesting the name extension, then the response is the expected`() {
        val input = executableElement("isSomething")

        val result = input.name

        assertEquals("isSomething", result)
    }

    @Test
    fun `when requesting the type extension, the the response is the expected`() {
        val input = executableElement(returnType = "java.lang.String")

        val result = input.type.value

        assertEquals("String", result)
    }
}
