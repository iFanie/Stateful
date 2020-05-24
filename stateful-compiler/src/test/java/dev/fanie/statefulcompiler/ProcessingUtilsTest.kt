package dev.fanie.statefulcompiler

import dev.fanie.statefulcompiler.util.elements
import dev.fanie.statefulcompiler.util.methodElement
import dev.fanie.statefulcompiler.util.typeElement
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import javax.lang.model.element.Modifier

internal class ProcessingUtilsTest {
    private lateinit var underTest: ProcessingUtils

    @Before
    fun setUp() {
        val elementUtils = elements()
        underTest = ProcessingUtils(elementUtils)
    }

    @Test
    fun `when reading the package of a typeElement, then the result is the expected`() {
        val packageName = "test.type.element"
        val typeElement = typeElement("Test", packageName)

        val result = underTest.getPackageOf(typeElement).toString()

        assertEquals(packageName, result)
    }

    @Test
    fun `when reading the class of a typeElement, then the result is the expected`() {
        val packageName = "test.package"
        val name = "TestClass"
        val className = "$packageName.$name"
        val typeElement = typeElement(name, packageName)

        val result = underTest.getClassOf(typeElement)

        assertEquals(className, result)
    }

    @Test
    fun `when reading the getters of a typeElement, then the class getter is ignored`() {
        val element = typeElement(
            "a",
            "b",
            methodElement("getA", Modifier.PUBLIC),
            methodElement("getClass", Modifier.PUBLIC),
            methodElement("getB", Modifier.PUBLIC)
        )

        val result = underTest.getGettersOf(element)

        result.forEach { getter ->
            if (getter.simpleName.startsWith(CLASS_GETTER_PREFIX)) {
                fail("Class getter was not ignored.")
            }
        }
    }

    @Test
    fun `when reading the getters of a typeElement, then the non public getters are ignored`() {
        val element = typeElement(
            "a",
            "b",
            methodElement("getA", Modifier.PRIVATE),
            methodElement("getB", Modifier.PROTECTED),
            methodElement("getC", Modifier.PUBLIC)
        )

        val result = underTest.getGettersOf(element)

        result.forEach { getter ->
            if (getter.modifiers.contains(Modifier.PRIVATE)
                || getter.modifiers.contains(Modifier.PROTECTED)
            ) {
                fail("Non public getter was not ignored.")
            }
        }
    }

    @Test
    fun `when reading the getters of a typeElement, then get and is getters are present`() {
        val element = typeElement(
            "a",
            "b",
            methodElement("getA", Modifier.PUBLIC),
            methodElement("isB", Modifier.PUBLIC),
            methodElement("somethingElse", Modifier.PUBLIC)
        )

        val result = underTest.getGettersOf(element)

        result.forEach { getter ->
            if (getter.simpleName.toString() != "getA" && getter.simpleName.toString() != "isB") {
                fail("Getter is not one of the expected.")
            }
        }
    }
}
