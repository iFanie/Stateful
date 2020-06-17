package dev.fanie.statefulcompiler

import dev.fanie.stateful.StatefulType
import dev.fanie.statefulcompiler.util.getter
import org.junit.Assert.assertEquals
import org.junit.Test

internal class WrapperBuilderTest {
    @Test
    fun `when reading the class package of a WrapperBuilder, then the result is the expected`() {
        val packageName = "test.package"
        val className = "test"
        val result = WrapperBuilder(
            packageName, className, listOf(), StatefulType.INSTANCE,
            noLazyInit = false,
            noDiffing = false,
            withListener = true,
            allowMissingRenderers = false
        ).classPackage

        assertEquals("$packageName.stateful.$className", result)
    }

    @Test
    fun `when reading the class name of a WrapperBuilder, then the result is the expected`() {
        val className = "TestClass"
        val result = WrapperBuilder(
            "irrelevant", className, listOf(), StatefulType.INSTANCE,
            noLazyInit = false,
            noDiffing = false,
            withListener = true,
            allowMissingRenderers = false
        ).className

        assertEquals("Stateful$className", result)
    }

    @Test
    fun `given type is INSTANCE, when reading the source code of a WrapperBuilder, then the result is the expected`() {
        val result =
            WrapperBuilder(
                "pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.INSTANCE,
                noLazyInit = false,
                noDiffing = false,
                withListener = true,
                allowMissingRenderers = false
            ).classSource

        assertEquals(
            """
            |package pkg.stateful.cls
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulInstance
            |import dev.fanie.stateful.StatefulInstance
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |import test
            |
            |/**
            | * Creates a new instance of the [StatefulCls] type.
            | * @param listener The [StatefulClsListener] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is null.
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
            | * @param initialCls The initial cls to be provided. Default value is null.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
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
            | * @param initialCls The initial  cls  to be provided. Default value is null.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
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
            WrapperBuilder(
                "pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.STACK,
                noLazyInit = false,
                noDiffing = false,
                withListener = true,
                allowMissingRenderers = false
            ).classSource

        assertEquals(
            """
            |package pkg.stateful.cls
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulStack
            |import dev.fanie.stateful.StatefulStack
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |import test
            |
            |/**
            | * Creates a new instance of the [StatefulCls] type.
            | * @param listener The [StatefulClsListener] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is null.
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
            | * @param initialCls The initial cls to be provided. Default value is null.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
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
            | * @param initialCls The initial  cls  to be provided. Default value is null.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
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
            WrapperBuilder(
                "pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.LINKED_LIST,
                noLazyInit = false,
                noDiffing = false,
                withListener = true,
                allowMissingRenderers = false
            ).classSource

        assertEquals(
            """
            |package pkg.stateful.cls
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulLinkedList
            |import dev.fanie.stateful.StatefulLinkedList
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |import test
            |
            |/**
            | * Creates a new instance of the [StatefulCls] type.
            | * @param listener The [StatefulClsListener] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is null.
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
            | * @param initialCls The initial cls to be provided. Default value is null.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
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
            | * @param initialCls The initial  cls  to be provided. Default value is null.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
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
            WrapperBuilder(
                "pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.INSTANCE,
                noLazyInit = true,
                noDiffing = false,
                withListener = true,
                allowMissingRenderers = false
            ).classSource

        assertEquals(
            """
            |package pkg.stateful.cls
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulInstance
            |import dev.fanie.stateful.StatefulInstance
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |import test
            |
            |/**
            | * Creates a new instance of the [StatefulCls] type.
            | * @param listener The [StatefulClsListener] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is null.
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

    @Test
    fun `given no diffing should be performed, when reading the source code of a WrapperBuilder, then the result is the expected`() {
        val result =
            WrapperBuilder(
                "pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.INSTANCE,
                noLazyInit = false,
                noDiffing = true,
                withListener = true,
                allowMissingRenderers = false
            ).classSource

        assertEquals(
            """
            |package pkg.stateful.cls
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulInstance
            |import dev.fanie.stateful.StatefulInstance
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |import test
            |
            |/**
            | * Creates a new instance of the [StatefulCls] type.
            | * @param listener The [StatefulClsListener] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is null.
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
            | * @param initialCls The initial cls to be provided. Default value is null.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
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
            | * @param initialCls The initial  cls  to be provided. Default value is null.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
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
            |        listener.onOne(newInstance.one)
            |        listener.onTwo(newInstance.two)
            |    }
            |}
            |
        """.trimMargin(), result
        )
    }

    @Test
    fun `given no listener should be built, when reading the source code of a WrapperBuilder, then the result is the expected`() {
        val result =
            WrapperBuilder(
                "pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.INSTANCE,
                noLazyInit = false,
                noDiffing = false,
                withListener = false,
                allowMissingRenderers = false
            ).classSource

        assertEquals(
            """
            |package pkg.stateful.cls
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulInstance
            |import dev.fanie.stateful.StatefulInstance
            |import dev.fanie.stateful.StatefulProperty
            |import dev.fanie.stateful.StatefulRendererCache
            |import dev.fanie.stateful.buildPropertyRenderers
            |import dev.fanie.stateful.statefulRendererCache
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |import kotlin.reflect.KClass
            |import test
            |
            |/**
            | * Creates a new instance of the [StatefulCls] type.
            | * @param listener The [Any] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is null.
            | * @return A new instance of the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |fun statefulCls(
            |    listener: Any,
            |    initialCls: Cls? = null
            |) : StatefulInstance<Cls> = StatefulCls(listener, initialCls)
            |
            |/**
            | * Provides a lazy initializer for the [StatefulCls] type.
            | * @param listener The [Any] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is null.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
            | * @return A lazy initializer for the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |fun stateful(
            |    listener: Any,
            |    initialCls: Cls? = null,
            |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
            |) = lazy(lazyMode) { statefulCls(listener, initialCls) }
            |
            |/**
            | * Provides a lazy initializer for the [StatefulCls] type, invoking the receiving [StatefulClsListener] instance.
            | * @param initialCls The initial  cls  to be provided. Default value is null.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
            | * @return A lazy initializer for the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |@JvmName("extensionStateful")
            |fun Any.stateful(
            |    initialCls: Cls? = null,
            |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
            |) = stateful(this, initialCls, lazyMode)
            |
            |/**
            | * Implementation of the [AbstractStatefulInstance] for the [Cls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |class StatefulCls(
            |    private val listener: Any,
            |    initialCls: Cls? = null
            |) : AbstractStatefulInstance<Cls>(initialCls) {
            |    private val rendererCache by statefulRendererCache<Cls, Property<*>>(listener, allowMissingRenderers = false)
            |
            |    override fun announce(currentInstance: Cls?, newInstance: Cls) {
            |        if (!equals(currentInstance?.one, newInstance.one)) {
            |            rendererCache.getRenderers(Property.ONE)?.forEach { it(currentInstance, newInstance) }
            |        }
            |
            |        if (!equals(currentInstance?.two, newInstance.two)) {
            |            rendererCache.getRenderers(Property.TWO)?.forEach { it(currentInstance, newInstance) }
            |        }
            |    }
            |
            |    /**
            |     * Enumerates the available [StatefulProperty] object implementations for the [Cls] type.
            |     * @param Type The type of each respective property of the [Cls] type.
            |     */
            |    sealed class Property<Type : Any> : StatefulProperty<Type, Cls> {
            |        object ONE : Property<test>() {
            |            override val getter: Function1<Cls, test> = { model -> model.one }
            |            override val type: KClass<test> = test::class
            |            override val isOptional: Boolean = false
            |            override val modelType: KClass<Cls> = Cls::class
            |        }
            |
            |        object TWO : Property<test>() {
            |            override val getter: Function1<Cls, test> = { model -> model.two }
            |            override val type: KClass<test> = test::class
            |            override val isOptional: Boolean = false
            |            override val modelType: KClass<Cls> = Cls::class
            |        }
            |    }
            |}
            |
        """.trimMargin(), result
        )
    }

    @Test
    fun `given missing renderers are allowed, when reading the source code of a WrapperBuilder, then the result is the expected`() {
        val result =
            WrapperBuilder(
                "pkg", "Cls", listOf(getter("one"), getter("two")), StatefulType.INSTANCE,
                noLazyInit = false,
                noDiffing = false,
                withListener = false,
                allowMissingRenderers = true
            ).classSource

        assertEquals(
            """
            |package pkg.stateful.cls
            |
            |import Cls
            |import dev.fanie.stateful.AbstractStatefulInstance
            |import dev.fanie.stateful.StatefulInstance
            |import dev.fanie.stateful.StatefulProperty
            |import dev.fanie.stateful.StatefulRendererCache
            |import dev.fanie.stateful.buildPropertyRenderers
            |import dev.fanie.stateful.statefulRendererCache
            |import java.util.Objects.equals
            |import javax.annotation.Generated
            |import kotlin.reflect.KClass
            |import test
            |
            |/**
            | * Creates a new instance of the [StatefulCls] type.
            | * @param listener The [Any] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is null.
            | * @return A new instance of the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |fun statefulCls(
            |    listener: Any,
            |    initialCls: Cls? = null
            |) : StatefulInstance<Cls> = StatefulCls(listener, initialCls)
            |
            |/**
            | * Provides a lazy initializer for the [StatefulCls] type.
            | * @param listener The [Any] instance to be invoked upon updates.
            | * @param initialCls The initial cls to be provided. Default value is null.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
            | * @return A lazy initializer for the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |fun stateful(
            |    listener: Any,
            |    initialCls: Cls? = null,
            |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
            |) = lazy(lazyMode) { statefulCls(listener, initialCls) }
            |
            |/**
            | * Provides a lazy initializer for the [StatefulCls] type, invoking the receiving [StatefulClsListener] instance.
            | * @param initialCls The initial  cls  to be provided. Default value is null.
            | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
            | * @return A lazy initializer for the [StatefulCls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |@JvmName("extensionStateful")
            |fun Any.stateful(
            |    initialCls: Cls? = null,
            |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
            |) = stateful(this, initialCls, lazyMode)
            |
            |/**
            | * Implementation of the [AbstractStatefulInstance] for the [Cls] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |class StatefulCls(
            |    private val listener: Any,
            |    initialCls: Cls? = null
            |) : AbstractStatefulInstance<Cls>(initialCls) {
            |    private val rendererCache by statefulRendererCache<Cls, Property<*>>(listener, allowMissingRenderers = true)
            |
            |    override fun announce(currentInstance: Cls?, newInstance: Cls) {
            |        if (!equals(currentInstance?.one, newInstance.one)) {
            |            rendererCache.getRenderers(Property.ONE)?.forEach { it(currentInstance, newInstance) }
            |        }
            |
            |        if (!equals(currentInstance?.two, newInstance.two)) {
            |            rendererCache.getRenderers(Property.TWO)?.forEach { it(currentInstance, newInstance) }
            |        }
            |    }
            |
            |    /**
            |     * Enumerates the available [StatefulProperty] object implementations for the [Cls] type.
            |     * @param Type The type of each respective property of the [Cls] type.
            |     */
            |    sealed class Property<Type : Any> : StatefulProperty<Type, Cls> {
            |        object ONE : Property<test>() {
            |            override val getter: Function1<Cls, test> = { model -> model.one }
            |            override val type: KClass<test> = test::class
            |            override val isOptional: Boolean = false
            |            override val modelType: KClass<Cls> = Cls::class
            |        }
            |
            |        object TWO : Property<test>() {
            |            override val getter: Function1<Cls, test> = { model -> model.two }
            |            override val type: KClass<test> = test::class
            |            override val isOptional: Boolean = false
            |            override val modelType: KClass<Cls> = Cls::class
            |        }
            |    }
            |}
            |
        """.trimMargin(), result
        )
    }
}
