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
            |class StatefulCls(
            |    private val clsUpdateListener: ClsUpdateListener,
            |    initialCls: Cls? = null
            |) {
            |    private var currentCls: Cls? = null
            |    
            |    init {
            |        initialCls?.let {
            |            accept(it)
            |        }
            |    }
            |
            |    fun accept(newCls: Cls) {
            |        
            |        if (!java.util.Objects.equals(currentCls?.one, newCls.one)) {
            |            clsUpdateListener.onOneUpdated(newCls.one)
            |            clsUpdateListener.onOneUpdated(currentCls?.one, newCls.one)
            |            clsUpdateListener.onOneUpdated(newCls)
            |            clsUpdateListener.onOneUpdated(currentCls, newCls)
            |        }
            |
            |        if (!java.util.Objects.equals(currentCls?.two, newCls.two)) {
            |            clsUpdateListener.onTwoUpdated(newCls.two)
            |            clsUpdateListener.onTwoUpdated(currentCls?.two, newCls.two)
            |            clsUpdateListener.onTwoUpdated(newCls)
            |            clsUpdateListener.onTwoUpdated(currentCls, newCls)
            |        }
            |
            |        currentCls = newCls
            |    }
            |    
            |    fun clear() {
            |        currentCls = null
            |    }
            |}
            |
        """.trimMargin(), result
        )
    }
}
