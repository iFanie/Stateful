package dev.fanie.statefulcompiler

import dev.fanie.statefulcompiler.util.getter
import org.junit.Assert.assertEquals
import org.junit.Test

class StatefulBuilderTest {
    @Test
    fun `when reading the class package of a StatefulBuilder, then the result is the expected`() {
        val packageName = "test.package"
        val result = StatefulBuilder(packageName, "irrelevant", listOf()).classPackage

        assertEquals("$packageName.stateful", result)
    }

    @Test
    fun `when reading the class name of a StatefulBuilder, then the result is the expected`() {
        val className = "TestClass"
        val result = StatefulBuilder("irrelevant", className, listOf()).className

        assertEquals("Stateful$className", result)
    }

    @Test
    fun `when reading the source code of a StatefulBuilder, then the result is the expected`() {
        val result = StatefulBuilder("pkg", "Cls", listOf(getter("one"), getter("two"))).classSource

        assertEquals(
            """
            |package pkg.stateful
            |
            |import dev.fanie.stateful.AbstractStatefulInstance
            |import java.util.Objects.equals
            |import Cls
            |
            |class StatefulCls(
            |    private val clsUpdateListener: StatefulClsListener,
            |    initialCls: Cls? = null
            |) : AbstractStatefulInstance<Cls>(initialCls) {
            |    final override fun announce(currentCls: Cls?, newCls: Cls) {
            |        if (!equals(currentCls?.one, newCls.one)) {
            |            clsUpdateListener.onOneUpdated(newCls.one)
            |            clsUpdateListener.onOneUpdated(currentCls?.one, newCls.one)
            |            clsUpdateListener.onOneUpdated(newCls)
            |            clsUpdateListener.onOneUpdated(currentCls, newCls)
            |        }
            |
            |        if (!equals(currentCls?.two, newCls.two)) {
            |            clsUpdateListener.onTwoUpdated(newCls.two)
            |            clsUpdateListener.onTwoUpdated(currentCls?.two, newCls.two)
            |            clsUpdateListener.onTwoUpdated(newCls)
            |            clsUpdateListener.onTwoUpdated(currentCls, newCls)
            |        }
            |    }
            |}
            |
        """.trimMargin(), result
        )
    }
}
