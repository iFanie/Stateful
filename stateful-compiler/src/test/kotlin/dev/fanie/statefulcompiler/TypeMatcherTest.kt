package dev.fanie.statefulcompiler

import dev.fanie.statefulcompiler.util.boolean
import dev.fanie.statefulcompiler.util.byte
import dev.fanie.statefulcompiler.util.char
import dev.fanie.statefulcompiler.util.double
import dev.fanie.statefulcompiler.util.exception
import dev.fanie.statefulcompiler.util.float
import dev.fanie.statefulcompiler.util.int
import dev.fanie.statefulcompiler.util.jBoolean
import dev.fanie.statefulcompiler.util.jByte
import dev.fanie.statefulcompiler.util.jChar
import dev.fanie.statefulcompiler.util.jCharSequence
import dev.fanie.statefulcompiler.util.jDouble
import dev.fanie.statefulcompiler.util.jFloat
import dev.fanie.statefulcompiler.util.jInt
import dev.fanie.statefulcompiler.util.jLong
import dev.fanie.statefulcompiler.util.jShort
import dev.fanie.statefulcompiler.util.jString
import dev.fanie.statefulcompiler.util.list
import dev.fanie.statefulcompiler.util.long
import dev.fanie.statefulcompiler.util.map
import dev.fanie.statefulcompiler.util.nonStandard
import dev.fanie.statefulcompiler.util.set
import dev.fanie.statefulcompiler.util.short
import dev.fanie.statefulcompiler.util.throwable
import org.junit.Assert.assertEquals
import org.junit.Test

internal class TypeMatcherTest {
    @Test
    fun `when matching a boolean, then the result is the expected`() {
        val result = boolean().type
        assertEquals(Boolean::class.simpleName, result.value)
    }

    @Test
    fun `when matching a byte, then the result is the expected`() {
        val result = byte().type
        assertEquals(Byte::class.simpleName, result.value)
    }

    @Test
    fun `when matching a char, then the result is the expected`() {
        val result = char().type
        assertEquals(Char::class.simpleName, result.value)
    }

    @Test
    fun `when matching a short, then the result is the expected`() {
        val result = short().type
        assertEquals(Short::class.simpleName, result.value)
    }

    @Test
    fun `when matching a int, then the result is the expected`() {
        val result = int().type
        assertEquals(Int::class.simpleName, result.value)
    }

    @Test
    fun `when matching a long, then the result is the expected`() {
        val result = long().type
        assertEquals(Long::class.simpleName, result.value)
    }

    @Test
    fun `when matching a float, then the result is the expected`() {
        val result = float().type
        assertEquals(Float::class.simpleName, result.value)
    }

    @Test
    fun `when matching a double, then the result is the expected`() {
        val result = double().type
        assertEquals(Double::class.simpleName, result.value)
    }

    @Test
    fun `when matching a Boolean, then the result is the expected`() {
        val result = jBoolean().type
        assertEquals(Boolean::class.simpleName, result.value)
    }

    @Test
    fun `when matching a Byte, then the result is the expected`() {
        val result = jByte().type
        assertEquals(Byte::class.simpleName, result.value)
    }

    @Test
    fun `when matching a Char, then the result is the expected`() {
        val result = jChar().type
        assertEquals(Char::class.simpleName, result.value)
    }

    @Test
    fun `when matching a Short, then the result is the expected`() {
        val result = jShort().type
        assertEquals(Short::class.simpleName, result.value)
    }

    @Test
    fun `when matching a Int, then the result is the expected`() {
        val result = jInt().type
        assertEquals(Int::class.simpleName, result.value)
    }

    @Test
    fun `when matching a Long, then the result is the expected`() {
        val result = jLong().type
        assertEquals(Long::class.simpleName, result.value)
    }

    @Test
    fun `when matching a Float, then the result is the expected`() {
        val result = jFloat().type
        assertEquals(Float::class.simpleName, result.value)
    }

    @Test
    fun `when matching a Double, then the result is the expected`() {
        val result = jDouble().type
        assertEquals(Double::class.simpleName, result.value)
    }

    @Test
    fun `when matching a String, then the result is the expected`() {
        val result = jString().type
        assertEquals(String::class.simpleName, result.value)
    }

    @Test
    fun `when matching a CharSequence, then the result is the expected`() {
        val result = jCharSequence().type
        assertEquals(CharSequence::class.simpleName, result.value)
    }

    @Test
    fun `when matching a List, then the result is the expected`() {
        val result = list("java.lang.Integer").type
        assertEquals(setOf(List::class.qualifiedName, Int::class.qualifiedName), result.imports.toSet())
        assertEquals("List<Int>", result.value)
    }

    @Test
    fun `when matching a Set, then the result is the expected`() {
        val result = set("java.lang.String").type
        assertEquals(setOf(Set::class.qualifiedName, String::class.qualifiedName), result.imports.toSet())
        assertEquals("Set<String>", result.value)
    }

    @Test
    fun `when matching a Map, then the result is the expected`() {
        val result = map("java.lang.Float", "java.lang.Double").type
        assertEquals(setOf(Map::class.qualifiedName, Float::class.qualifiedName, Double::class.qualifiedName), result.imports.toSet())
        assertEquals("Map<Float, Double>", result.value)
    }

    @Test
    fun `when matching a Throwable, then the result is the expected`() {
        val result = throwable().type
        assertEquals(Throwable::class.simpleName, result.value)
    }

    @Test
    fun `when matching an Exception, then the result is the expected`() {
        val result = exception().type
        assertEquals(Exception::class.simpleName, result.value)
    }

    @Test
    fun `when matching a nullable Boolean, then the result is the expected`() {
        val result = jBoolean(nullable = true).type
        assertEquals(Boolean::class.simpleName + "?", result.value)
    }

    @Test
    fun `when matching a nullable List, then the result is the expected`() {
        val result = list("java.lang.Integer", nullable = true).type
        assertEquals("List<Int>?", result.value)
    }

    @Test
    fun `when matching a non-standard instance, then result is the expected`() {
        val type = "TestType"
        val result = nonStandard(returnType = type, nullable = false).type
        assertEquals(type, result.value)
    }

    @Test
    fun `when matching a non-standard nullable instance, then result is the expected`() {
        val type = "TestType"
        val result = nonStandard(returnType = type, nullable = true).type
        assertEquals("$type?", result.value)
    }
}
