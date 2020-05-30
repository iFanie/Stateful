package dev.fanie.statefulcompiler

import dev.fanie.stateful.StatefulType
import dev.fanie.statefulcompiler.util.getter
import org.junit.Assert.assertEquals
import org.junit.Test

class WrapperBuilderTest {
    @Test
    fun `when reading the class package of a WrapperBuilder, then the result is the expected`() {
        val packageName = "test.package"
        val result = WrapperBuilder(packageName, "irrelevant", listOf(), StatefulType.INSTANCE).classPackage

        assertEquals("$packageName.stateful", result)
    }

    @Test
    fun `when reading the class name of a WrapperBuilder, then the result is the expected`() {
        val className = "TestClass"
        val result = WrapperBuilder("irrelevant", className, listOf(), StatefulType.INSTANCE).className

        assertEquals("Stateful$className", result)
    }

    @Test
    fun `given type is INSTANCE, when reading the source code of a WrapperBuilder, then the result is the expected`() {
        val result = WrapperBuilder("pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.INSTANCE).classSource

        assertEquals(
            """
            |package pkg.stateful
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulInstance
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |
            |/**
            | * Implementation of the [AbstractStatefulInstance] for the [Cls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |class StatefulCls(
            |    private val listener: StatefulClsListener,
            |    initialCls: Cls? = null
            |) : AbstractStatefulInstance<Cls>(initialCls) {
            |    override fun announce(currentInstance: Cls?, newInstance: Cls) {
            |        if (!equals(currentInstance?.one, newInstance.one)) {
            |            listener.onOneUpdated(newInstance.one)
            |            listener.onOneUpdated(currentInstance?.one, newInstance.one)
            |            listener.onOneUpdated(newInstance)
            |            listener.onOneUpdated(currentInstance, newInstance)
            |        }
            |
            |        if (!equals(currentInstance?.two, newInstance.two)) {
            |            listener.onTwoUpdated(newInstance.two)
            |            listener.onTwoUpdated(currentInstance?.two, newInstance.two)
            |            listener.onTwoUpdated(newInstance)
            |            listener.onTwoUpdated(currentInstance, newInstance)
            |        }
            |    }
            |}
            |
        """.trimMargin(), result
        )
    }

    @Test
    fun `given type is STACK, when reading the source code of a WrapperBuilder, then the result is the expected`() {
        val result = WrapperBuilder("pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.STACK).classSource

        assertEquals(
            """
            |package pkg.stateful
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulStack
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |
            |/**
            | * Implementation of the [AbstractStatefulStack] for the [Cls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |class StatefulCls(
            |    private val listener: StatefulClsListener,
            |    initialCls: Cls? = null
            |) : AbstractStatefulStack<Cls>(initialCls) {
            |    override fun announce(currentInstance: Cls?, newInstance: Cls) {
            |        if (!equals(currentInstance?.one, newInstance.one)) {
            |            listener.onOneUpdated(newInstance.one)
            |            listener.onOneUpdated(currentInstance?.one, newInstance.one)
            |            listener.onOneUpdated(newInstance)
            |            listener.onOneUpdated(currentInstance, newInstance)
            |        }
            |
            |        if (!equals(currentInstance?.two, newInstance.two)) {
            |            listener.onTwoUpdated(newInstance.two)
            |            listener.onTwoUpdated(currentInstance?.two, newInstance.two)
            |            listener.onTwoUpdated(newInstance)
            |            listener.onTwoUpdated(currentInstance, newInstance)
            |        }
            |    }
            |}
            |
        """.trimMargin(), result
        )
    }
}
