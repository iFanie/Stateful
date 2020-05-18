package dev.fanie.statefulcompiler

import org.junit.Assert
import org.junit.Test
import javax.lang.model.element.*
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVisitor

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

        Assert.assertEquals("${className}UpdateListener", result)
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
            |interface ClsUpdateListener {
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

private fun getter(name: String = "test", type: String = "test") = object : ExecutableElement {
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

    override fun getReturnType(): TypeMirror = object : TypeMirror {
        override fun getKind(): TypeKind {
            WONTDO()
        }

        override fun <R : Any?, P : Any?> accept(p0: TypeVisitor<R, P>?, p1: P): R {
            WONTDO()
        }

        override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
            WONTDO()
        }

        override fun <A : Annotation?> getAnnotation(p0: Class<A>?): A {
            WONTDO()
        }

        override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
            WONTDO()
        }

        override fun toString(): String = type
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