package dev.fanie.statefulcompiler

import dev.fanie.statefulcompiler.util.getter
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ListenerBuilderTest {
    @Test
    fun `when reading the class package of a ListenerBuilder, then the result is the expected`() {
        val packageName = "test.package"
        val className = "test"
        val result =
            ListenerBuilder(packageName, className, listOf(), nonCascading = false, noDiffing = false).classPackage

        assertEquals("$packageName.stateful.$className", result)
    }

    @Test
    fun `when reading the class name of a ListenerBuilder, then the result is the expected`() {
        val className = "TestClass"
        val result = ListenerBuilder("irrelevant", className, listOf(), nonCascading = false, noDiffing = false).className

        assertEquals("Stateful${className}Listener", result)
    }

    @Test
    fun `given listener should cascade, when reading the source code of a ListenerBuilder, then the result is the expected`() {
        val result =
            ListenerBuilder(
                "pkg",
                "Cls",
                listOf(getter("one", "int"), getter("two", "byte")),
                nonCascading = false,
                noDiffing = false
            ).classSource

        assertEquals(
            """
            |package pkg.stateful.cls
            |
            |import Cls
            |import javax.annotation.Generated
            |import kotlin.Byte
            |import kotlin.Int
            |
            |/**
            | * Contains callbacks to be invoked on updates of each individual public property
            | * of an instance of the [Cls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |interface StatefulClsListener : StatefulClsOneListener, 
            |    StatefulClsTwoListener
            |    
            |/**
            | * Contains callbacks to be invoked on updates of the [Cls.one] property.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |interface StatefulClsOneListener {
            |    /**
            |     * Invoked on updates of the [Cls.one] property.
            |     * @param newOne The new one to be rendered.
            |     */
            |    fun onOneUpdated(newOne: Int) {}
            |
            |    /**
            |     * Invoked on updates of the [Cls.one] property.
            |     * @param currentOne The currently rendered one, if any.
            |     * @param newOne The new one to be rendered.
            |     */
            |     fun onOneUpdated(currentOne: Int?, newOne: Int) {}
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
            |}
            |
            |/**
            | * Contains callbacks to be invoked on updates of the [Cls.two] property.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |interface StatefulClsTwoListener {
            |    /**
            |     * Invoked on updates of the [Cls.two] property.
            |     * @param newTwo The new two to be rendered.
            |     */
            |    fun onTwoUpdated(newTwo: Byte) {}
            |
            |    /**
            |     * Invoked on updates of the [Cls.two] property.
            |     * @param currentTwo The currently rendered two, if any.
            |     * @param newTwo The new two to be rendered.
            |     */
            |     fun onTwoUpdated(currentTwo: Byte?, newTwo: Byte) {}
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

    @Test
    fun `given listener should not cascade, when reading the source code of a ListenerBuilder, then the result is the expected`() {
        val result =
            ListenerBuilder(
                "pkg",
                "Cls",
                listOf(getter("one", "int"), getter("two", "byte")),
                nonCascading = true,
                noDiffing = false
            ).classSource

        assertEquals(
            """
            |package pkg.stateful.cls
            |
            |import Cls
            |import javax.annotation.Generated
            |import kotlin.Byte
            |import kotlin.Int
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
            |    fun onOneUpdated(newOne: Int) {}
            |
            |    /**
            |     * Invoked on updates of the [Cls.one] property.
            |     * @param currentOne The currently rendered one, if any.
            |     * @param newOne The new one to be rendered.
            |     */
            |     fun onOneUpdated(currentOne: Int?, newOne: Int) {}
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
            |    fun onTwoUpdated(newTwo: Byte) {}
            |
            |    /**
            |     * Invoked on updates of the [Cls.two] property.
            |     * @param currentTwo The currently rendered two, if any.
            |     * @param newTwo The new two to be rendered.
            |     */
            |     fun onTwoUpdated(currentTwo: Byte?, newTwo: Byte) {}
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

    @Test
    fun `given no diffing should be performed, when reading the source code of a ListenerBuilder, then the result is the expected`() {
        val result =
            ListenerBuilder(
                "pkg",
                "Cls",
                listOf(getter("one", "int"), getter("two", "byte")),
                nonCascading = false,
                noDiffing = true
            ).classSource

        assertEquals(
            """
            |package pkg.stateful.cls
            |
            |import Cls
            |import javax.annotation.Generated
            |import kotlin.Byte
            |import kotlin.Int
            |
            |/**
            | * Contains callbacks to be invoked on updates of each individual public property
            | * of an instance of the [Cls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |interface StatefulClsListener : StatefulClsOneListener, 
            |    StatefulClsTwoListener
            |    
            |/**
            | * Contains callbacks to be invoked on updates of the [Cls.one] property.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |interface StatefulClsOneListener {
            |    /**
            |     * Invoked on updates of the [Cls.one] property.
            |     * @param one The one to be rendered.
            |     */
            |    fun onOne(one: Int) {}
            |}
            |
            |/**
            | * Contains callbacks to be invoked on updates of the [Cls.two] property.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |interface StatefulClsTwoListener {
            |    /**
            |     * Invoked on updates of the [Cls.two] property.
            |     * @param two The two to be rendered.
            |     */
            |    fun onTwo(two: Byte) {}
            |}
            |
        """.trimMargin(), result
        )
    }
}
