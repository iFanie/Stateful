package dev.fanie.statefulcompiler

import dev.fanie.statefulcompiler.util.getter
import org.junit.Assert
import org.junit.Test

class ListenerBuilderTest {
    @Test
    fun `when reading the class package of a UpdateListenerBuilder, then the result is the expected`() {
        val packageName = "test.package"
        val result = ListenerBuilder(packageName, "irrelevant", listOf()).classPackage

        Assert.assertEquals("$packageName.stateful", result)
    }

    @Test
    fun `when reading the class name of a UpdateListenerBuilder, then the result is the expected`() {
        val className = "TestClass"
        val result = ListenerBuilder("irrelevant", className, listOf()).className

        Assert.assertEquals("Stateful${className}Listener", result)
    }

    @Test
    fun `when reading the source code of a UpdateListenerBuilder, then the result is the expected`() {
        val result =
            ListenerBuilder(
                "pkg",
                "Cls",
                listOf(getter("one", "int"), getter("two", "byte"))
            ).classSource

        Assert.assertEquals(
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
            |    fun onOneUpdated(newOne: kotlin.Int) {}
            |    fun onOneUpdated(currentOne: kotlin.Int?, newOne: kotlin.Int) {}
            |    fun onOneUpdated(newCls: Cls) {}
            |    fun onOneUpdated(currentCls: Cls?, newCls: Cls) {}
            |
            |    fun onTwoUpdated(newTwo: kotlin.Byte) {}
            |    fun onTwoUpdated(currentTwo: kotlin.Byte?, newTwo: kotlin.Byte) {}
            |    fun onTwoUpdated(newCls: Cls) {}
            |    fun onTwoUpdated(currentCls: Cls?, newCls: Cls) {}
            |}
            |
        """.trimMargin(), result
        )
    }
}
