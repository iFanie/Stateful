package dev.fanie.statefulcompiler

import dev.fanie.stateful.StatefulExtra
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ExtrasBuilderTest {
    @Test
    fun `when reading the class package of an ExtrasBuilder, then the result is the expected`() {
        val packageName = "test.package"
        val result = ExtrasBuilder(packageName, "irrelevant", setOf()).classPackage

        Assert.assertEquals("$packageName.stateful", result)
    }

    @Test
    fun `when reading the class name of an ExtrasBuilder, then the result is the expected`() {
        val className = "TestClass"
        val result = ExtrasBuilder("irrelevant", className, setOf()).className

        Assert.assertEquals("Stateful${className}Extras", result)
    }

    @Test
    fun `given lazy initializers should be built, when reading the source code of an ExtrasBuilder, then the result is the expected`() {
        val result = ExtrasBuilder("pkg", "Cls", setOf(StatefulExtra.LAZY_INIT)).classSource

        assertEquals(
            """
                |package pkg.stateful
                |
                |import Cls
                |import javax.annotation.Generated
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
                |) = lazy(lazyMode) { StatefulCls(listener, initialCls) }
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
            """.trimMargin(), result
        )
    }
}