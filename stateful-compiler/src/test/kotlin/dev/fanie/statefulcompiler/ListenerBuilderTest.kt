package dev.fanie.statefulcompiler

import dev.fanie.statefulcompiler.util.getter
import org.junit.Assert.assertEquals
import org.junit.Test

class ListenerBuilderTest {
    @Test
    fun `when reading the class package of a ListenerBuilder, then the result is the expected`() {
        val packageName = "test.package"
        val result = ListenerBuilder(packageName, "irrelevant", listOf()).classPackage

        assertEquals("$packageName.stateful", result)
    }

    @Test
    fun `when reading the class name of a ListenerBuilder, then the result is the expected`() {
        val className = "TestClass"
        val result = ListenerBuilder("irrelevant", className, listOf()).className

        assertEquals("Stateful${className}Listener", result)
    }

    @Test
    fun `when reading the source code of a ListenerBuilder, then the result is the expected`() {
        val result =
            ListenerBuilder(
                "pkg",
                "Cls",
                listOf(getter("one", "int"), getter("two", "byte"))
            ).classSource

        assertEquals(
            """
            |package pkg.stateful
            |
            |import Cls
            |import javax.annotation.Generated
            |
            |/**
            | * Contains callbacks to be invoked on updates of each individual public property
            | * of an instance of the [Cls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |interface StatefulClsListener {
            |    /**
            |     * Invoked on updates of the [Cls.one] property.
            |     * @param newOne The new one to be rendered.
            |     */
            |    fun onOneUpdated(newOne: kotlin.Int) {}
            |
            |    /**
            |     * Invoked on updates of the [Cls.one] property.
            |     * @param currentOne The currently rendered one, if any.
            |     * @param newOne The new one to be rendered.
            |     */
            |     fun onOneUpdated(currentOne: kotlin.Int?, newOne: kotlin.Int) {}
            |
            |    /**
            |     * Invoked on updates of the [Cls.one] property.
            |     * @param newCls The new cls to be rendered.
            |     */
            |     fun onOneUpdated(newCls: Cls) {}
            |
            |    /**
            |     * Invoked on updates of the [Cls.one] property.
            |     * @param currentCls The currently rendered cls, if any.
            |     * @param newCls The new cls to be rendered.
            |     */
            |     fun onOneUpdated(currentCls: Cls?, newCls: Cls) {}
            |
            |    /**
            |     * Invoked on updates of the [Cls.two] property.
            |     * @param newTwo The new two to be rendered.
            |     */
            |    fun onTwoUpdated(newTwo: kotlin.Byte) {}
            |
            |    /**
            |     * Invoked on updates of the [Cls.two] property.
            |     * @param currentTwo The currently rendered two, if any.
            |     * @param newTwo The new two to be rendered.
            |     */
            |     fun onTwoUpdated(currentTwo: kotlin.Byte?, newTwo: kotlin.Byte) {}
            |
            |    /**
            |     * Invoked on updates of the [Cls.two] property.
            |     * @param newCls The new cls to be rendered.
            |     */
            |     fun onTwoUpdated(newCls: Cls) {}
            |
            |    /**
            |     * Invoked on updates of the [Cls.two] property.
            |     * @param currentCls The currently rendered cls, if any.
            |     * @param newCls The new cls to be rendered.
            |     */
            |     fun onTwoUpdated(currentCls: Cls?, newCls: Cls) {}
            |}
            |
        """.trimMargin(), result
        )
    }
}
