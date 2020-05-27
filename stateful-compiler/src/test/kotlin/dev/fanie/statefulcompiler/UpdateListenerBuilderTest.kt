package dev.fanie.statefulcompiler

import dev.fanie.statefulcompiler.util.getter
import org.junit.Assert
import org.junit.Test

class UpdateListenerBuilderTest {
    @Test
    fun `when reading the class package of a UpdateListenerBuilder, then the result is the expected`() {
        val packageName = "test.package"
        val result = UpdateListenerBuilder(packageName, "irrelevant", listOf()).classPackage

        Assert.assertEquals("$packageName.stateful", result)
    }

    @Test
    fun `when reading the class name of a UpdateListenerBuilder, then the result is the expected`() {
        val className = "TestClass"
        val result = UpdateListenerBuilder("irrelevant", className, listOf()).className

        Assert.assertEquals("Stateful${className}UpdateListener", result)
    }

    @Test
    fun `when reading the source code of a UpdateListenerBuilder, then the result is the expected`() {
        val result =
            UpdateListenerBuilder(
                "pkg",
                "Cls",
                listOf(getter("one", "int"), getter("two", "byte"))
            ).classSource

        Assert.assertEquals(
            """
            |package pkg.stateful
            |
            |interface StatefulClsUpdateListener {
            |    
            |    fun onOneUpdated(newOne: kotlin.Int) {}
            |    fun onOneUpdated(oldOne: kotlin.Int?, newOne: kotlin.Int) {}
            |    fun onOneUpdated(newCls: Cls) {}
            |    fun onOneUpdated(oldCls: Cls?, newCls: Cls) {}
            |
            |    fun onTwoUpdated(newTwo: kotlin.Byte) {}
            |    fun onTwoUpdated(oldTwo: kotlin.Byte?, newTwo: kotlin.Byte) {}
            |    fun onTwoUpdated(newCls: Cls) {}
            |    fun onTwoUpdated(oldCls: Cls?, newCls: Cls) {}
            |
            |}
            |
        """.trimMargin(), result
        )
    }
}
