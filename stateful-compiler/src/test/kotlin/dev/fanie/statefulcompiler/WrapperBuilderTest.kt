package dev.fanie.statefulcompiler

import dev.fanie.stateful.StatefulType
import dev.fanie.statefulcompiler.util.getter
import org.junit.Assert.assertEquals
import org.junit.Test

internal class WrapperBuilderTest {
    @Test
    fun `when reading the class package of a WrapperBuilder, then the result is the expected`() {
        val packageName = "test.package"
        val result = WrapperBuilder(packageName, "irrelevant", listOf(), StatefulType.INSTANCE, false).classPackage

        assertEquals("$packageName.stateful", result)
    }

    @Test
    fun `when reading the class name of a WrapperBuilder, then the result is the expected`() {
        val className = "TestClass"
        val result = WrapperBuilder("irrelevant", className, listOf(), StatefulType.INSTANCE, false).className

        assertEquals("Stateful$className", result)
    }

    @Test
    fun `given type is INSTANCE, when reading the source code of a WrapperBuilder, then the result is the expected`() {
        val result =
            WrapperBuilder("pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.INSTANCE, false).classSource

        assertEquals(
            """
            |package pkg.stateful
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulInstance
            |import dev.fanie.stateful.StatefulInstance
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |
            |/**
            | * Creates a new instance of the [StatefulCls] type.
            | * @param listener The [StatefulClsListener] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is {@code null}.
            | * @return A new instance of the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |fun statefulCls(
            |    listener: StatefulClsListener,
            |    initialCls: Cls? = null
            |) : StatefulInstance<Cls> = StatefulCls(listener, initialCls)
            |
            |/**
            | * Provides a lazy initializer for the [StatefulCls] type.
            | * @param listener The [StatefulClsListener] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is {@code null}.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is {@code LazyThreadSafetyMode.SYNCHRONIZED}.
            | * @return A lazy initializer for the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |fun stateful(
            |    listener: StatefulClsListener,
            |    initialCls: Cls? = null,
            |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
            |) = lazy(lazyMode) { statefulCls(listener, initialCls) }
            |
            |/**
            | * Provides a lazy initializer for the [StatefulCls] type, invoking the receiving [StatefulClsListener] instance.
            | * @param initialCls The initial  cls  to be provided. Default value is {@code null}.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is {@code LazyThreadSafetyMode.SYNCHRONIZED}.
            | * @return A lazy initializer for the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |@JvmName("extensionStateful")
            |fun StatefulClsListener.stateful(
            |    initialCls: Cls? = null,
            |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
            |) = stateful(this, initialCls, lazyMode)
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
        val result =
            WrapperBuilder("pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.STACK, false).classSource

        assertEquals(
            """
            |package pkg.stateful
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulStack
            |import dev.fanie.stateful.StatefulStack
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |
            |/**
            | * Creates a new instance of the [StatefulCls] type.
            | * @param listener The [StatefulClsListener] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is {@code null}.
            | * @return A new instance of the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |fun statefulCls(
            |    listener: StatefulClsListener,
            |    initialCls: Cls? = null
            |) : StatefulStack<Cls> = StatefulCls(listener, initialCls)
            |
            |/**
            | * Provides a lazy initializer for the [StatefulCls] type.
            | * @param listener The [StatefulClsListener] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is {@code null}.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is {@code LazyThreadSafetyMode.SYNCHRONIZED}.
            | * @return A lazy initializer for the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |fun stateful(
            |    listener: StatefulClsListener,
            |    initialCls: Cls? = null,
            |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
            |) = lazy(lazyMode) { statefulCls(listener, initialCls) }
            |
            |/**
            | * Provides a lazy initializer for the [StatefulCls] type, invoking the receiving [StatefulClsListener] instance.
            | * @param initialCls The initial  cls  to be provided. Default value is {@code null}.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is {@code LazyThreadSafetyMode.SYNCHRONIZED}.
            | * @return A lazy initializer for the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |@JvmName("extensionStateful")
            |fun StatefulClsListener.stateful(
            |    initialCls: Cls? = null,
            |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
            |) = stateful(this, initialCls, lazyMode)
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

    @Test
    fun `given type is LINKED_LIST, when reading the source code of a WrapperBuilder, then the result is the expected`() {
        val result =
            WrapperBuilder("pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.LINKED_LIST, false).classSource

        assertEquals(
            """
            |package pkg.stateful
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulLinkedList
            |import dev.fanie.stateful.StatefulLinkedList
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |
            |/**
            | * Creates a new instance of the [StatefulCls] type.
            | * @param listener The [StatefulClsListener] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is {@code null}.
            | * @return A new instance of the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |fun statefulCls(
            |    listener: StatefulClsListener,
            |    initialCls: Cls? = null
            |) : StatefulLinkedList<Cls> = StatefulCls(listener, initialCls)
            |
            |/**
            | * Provides a lazy initializer for the [StatefulCls] type.
            | * @param listener The [StatefulClsListener] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is {@code null}.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is {@code LazyThreadSafetyMode.SYNCHRONIZED}.
            | * @return A lazy initializer for the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |fun stateful(
            |    listener: StatefulClsListener,
            |    initialCls: Cls? = null,
            |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
            |) = lazy(lazyMode) { statefulCls(listener, initialCls) }
            |
            |/**
            | * Provides a lazy initializer for the [StatefulCls] type, invoking the receiving [StatefulClsListener] instance.
            | * @param initialCls The initial  cls  to be provided. Default value is {@code null}.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is {@code LazyThreadSafetyMode.SYNCHRONIZED}.
            | * @return A lazy initializer for the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |@JvmName("extensionStateful")
            |fun StatefulClsListener.stateful(
            |    initialCls: Cls? = null,
            |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
            |) = stateful(this, initialCls, lazyMode)
            |
            |/**
            | * Implementation of the [AbstractStatefulLinkedList] for the [Cls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |class StatefulCls(
            |    private val listener: StatefulClsListener,
            |    initialCls: Cls? = null
            |) : AbstractStatefulLinkedList<Cls>(initialCls) {
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
    fun `given lazy initializers should not be built, when reading the source code of a WrapperBuilder, then the result is the expected`() {
        val result =
            WrapperBuilder("pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.INSTANCE, true).classSource

        assertEquals(
            """
            |package pkg.stateful
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulInstance
            |import dev.fanie.stateful.StatefulInstance
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |
            |/**
            | * Creates a new instance of the [StatefulCls] type.
            | * @param listener The [StatefulClsListener] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is {@code null}.
            | * @return A new instance of the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |fun statefulCls(
            |    listener: StatefulClsListener,
            |    initialCls: Cls? = null
            |) : StatefulInstance<Cls> = StatefulCls(listener, initialCls)
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

}
