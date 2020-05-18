package dev.fanie.statefulcompiler

import org.junit.Assert.assertEquals
import org.junit.Test
import javax.lang.model.element.*
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVisitor
import kotlin.reflect.full.createInstance

class ExtensionsKtTest {
    @Test
    fun `given name starts with 'get', when requesting the name extension, then the response is the expected`() {
        val input = executableElement("getSomething")

        val result = input.name

        assertEquals("something", result)
    }

    @Test
    fun `given name does not start with 'get', when requesting the name extension, then the response is the expected`() {
        val input = executableElement("isSomething")

        val result = input.name

        assertEquals("isSomething", result)
    }

    @Test
    fun `when requesting the type extension, the the response is the expected`() {
        val input = executableElement(returnType = "java.lang.String")

        val result = input.type

        assertEquals("kotlin.String", result)
    }
}

private fun executableElement(
    name: String = "test",
    returnType: String = "test"
) =
    object : ExecutableElement {
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
                get() {
                    WONTDO()
                }

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

            override fun toString(): String = returnType
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

        override fun <A : Annotation?> getAnnotation(p0: Class<A>?): A? =
            org.jetbrains.annotations.NotNull::class.createInstance() as A

        override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
            WONTDO()
        }

        override fun getEnclosedElements(): MutableList<out Element> {
            WONTDO()
        }

    }