package dev.fanie.statefulcompiler

import org.junit.Assert.assertEquals
import org.junit.Test
import javax.lang.model.element.*
import javax.lang.model.type.TypeMirror

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

private fun getter(name: String = "test") = object : ExecutableElement {
    override fun getDefaultValue(): AnnotationValue {
        WONTDO()
    }

    override fun getModifiers(): MutableSet<Modifier> {
        WONTDO()
    }

    override fun getSimpleName(): Name = object : Name {
        override fun get(index: Int): Char {
            WONTDO()
        }

        override fun contentEquals(p0: CharSequence?): Boolean {
            WONTDO()
        }

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            WONTDO()
        }

        override val length: Int
            get() = WONTDO()

        override fun toString(): String = name
    }

    override fun getKind(): ElementKind {
        WONTDO()
    }

    override fun asType(): TypeMirror {
        WONTDO()
    }

    override fun getReturnType(): TypeMirror {
        WONTDO()
    }

    override fun getReceiverType(): TypeMirror {
        WONTDO()
    }

    override fun getThrownTypes(): MutableList<out TypeMirror> {
        WONTDO()
    }

    override fun getTypeParameters(): MutableList<out TypeParameterElement> {
        WONTDO()
    }

    override fun getEnclosingElement(): Element {
        WONTDO()
    }

    override fun <R : Any?, P : Any?> accept(p0: ElementVisitor<R, P>?, p1: P): R {
        WONTDO()
    }

    override fun getParameters(): MutableList<out VariableElement> {
        WONTDO()
    }

    override fun isVarArgs(): Boolean {
        WONTDO()
    }

    override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
        WONTDO()
    }

    override fun isDefault(): Boolean {
        WONTDO()
    }

    override fun <A : Annotation?> getAnnotation(p0: Class<A>?): A {
        WONTDO()
    }

    override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
        WONTDO()
    }

    override fun getEnclosedElements(): MutableList<out Element> {
        WONTDO()
    }
}