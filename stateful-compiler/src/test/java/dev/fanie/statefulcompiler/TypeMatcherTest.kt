package dev.fanie.statefulcompiler

import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.reflect.Proxy
import javax.lang.model.element.*
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVisitor

class TypeMatcherTest {
    @Test
    fun `when matching a boolean, then the result is the expected`() {
        val result = boolean().type
        assertEquals(Boolean::class.qualifiedName, result)
    }

    @Test
    fun `when matching a byte, then the result is the expected`() {
        val result = byte().type
        assertEquals(Byte::class.qualifiedName, result)
    }

    @Test
    fun `when matching a char, then the result is the expected`() {
        val result = char().type
        assertEquals(Char::class.qualifiedName, result)
    }

    @Test
    fun `when matching a short, then the result is the expected`() {
        val result = short().type
        assertEquals(Short::class.qualifiedName, result)
    }

    @Test
    fun `when matching a int, then the result is the expected`() {
        val result = int().type
        assertEquals(Int::class.qualifiedName, result)
    }

    @Test
    fun `when matching a long, then the result is the expected`() {
        val result = long().type
        assertEquals(Long::class.qualifiedName, result)
    }

    @Test
    fun `when matching a float, then the result is the expected`() {
        val result = float().type
        assertEquals(Float::class.qualifiedName, result)
    }

    @Test
    fun `when matching a double, then the result is the expected`() {
        val result = double().type
        assertEquals(Double::class.qualifiedName, result)
    }

    @Test
    fun `when matching a Boolean, then the result is the expected`() {
        val result = Boolean().type
        assertEquals(Boolean::class.qualifiedName, result)
    }

    @Test
    fun `when matching a Byte, then the result is the expected`() {
        val result = Byte().type
        assertEquals(Byte::class.qualifiedName, result)
    }

    @Test
    fun `when matching a Char, then the result is the expected`() {
        val result = Char().type
        assertEquals(Char::class.qualifiedName, result)
    }

    @Test
    fun `when matching a Short, then the result is the expected`() {
        val result = Short().type
        assertEquals(Short::class.qualifiedName, result)
    }

    @Test
    fun `when matching a Int, then the result is the expected`() {
        val result = Int().type
        assertEquals(Int::class.qualifiedName, result)
    }

    @Test
    fun `when matching a Long, then the result is the expected`() {
        val result = Long().type
        assertEquals(Long::class.qualifiedName, result)
    }

    @Test
    fun `when matching a Float, then the result is the expected`() {
        val result = Float().type
        assertEquals(Float::class.qualifiedName, result)
    }

    @Test
    fun `when matching a Double, then the result is the expected`() {
        val result = Double().type
        assertEquals(Double::class.qualifiedName, result)
    }

    @Test
    fun `when matching a String, then the result is the expected`() {
        val result = String().type
        assertEquals(String::class.qualifiedName, result)
    }

    @Test
    fun `when matching a CharSequence, then the result is the expected`() {
        val result = CharSequence().type
        assertEquals(CharSequence::class.qualifiedName, result)
    }

    @Test
    fun `when matching a nullable Boolean, then the result is the expected`() {
        val result = Boolean(nullable = true).type
        assertEquals(Boolean::class.qualifiedName + "?", result)
    }

    @Test
    fun `when matching a nullable Byte, then the result is the expected`() {
        val result = Byte(nullable = true).type
        assertEquals(Byte::class.qualifiedName + "?", result)
    }

    @Test
    fun `when matching a nullable Char, then the result is the expected`() {
        val result = Char(nullable = true).type
        assertEquals(Char::class.qualifiedName + "?", result)
    }

    @Test
    fun `when matching a nullable Short, then the result is the expected`() {
        val result = Short(nullable = true).type
        assertEquals(Short::class.qualifiedName + "?", result)
    }

    @Test
    fun `when matching a nullable Int, then the result is the expected`() {
        val result = Int(nullable = true).type
        assertEquals(Int::class.qualifiedName + "?", result)
    }

    @Test
    fun `when matching a nullable Long, then the result is the expected`() {
        val result = Long(nullable = true).type
        assertEquals(Long::class.qualifiedName + "?", result)
    }

    @Test
    fun `when matching a nullable Float, then the result is the expected`() {
        val result = Float(nullable = true).type
        assertEquals(Float::class.qualifiedName + "?", result)
    }

    @Test
    fun `when matching a nullable Double, then the result is the expected`() {
        val result = Double(nullable = true).type
        assertEquals(Double::class.qualifiedName + "?", result)
    }

    @Test
    fun `when matching a nullable String, then the result is the expected`() {
        val result = String(nullable = true).type
        assertEquals(String::class.qualifiedName + "?", result)
    }

    @Test
    fun `when matching a nullable CharSequence, then the result is the expected`() {
        val result = CharSequence(nullable = true).type
        assertEquals(CharSequence::class.qualifiedName + "?", result)
    }

    @Test
    fun `when matching a non-standard instance, then result is the expected`() {
        val type = "TestType"
        val result = executableElement(returnType = type, nullable = false).type
        assertEquals(type, result)
    }

    @Test
    fun `when matching a non-standard nullable instance, then result is the expected`() {
        val type = "TestType"
        val result = executableElement(returnType = type, nullable = true).type
        assertEquals("$type?", result)
    }
}

fun boolean() = executableElement("boolean", false)
fun byte() = executableElement("byte", false)
fun char() = executableElement("char", false)
fun short() = executableElement("short", false)
fun int() = executableElement("int", false)
fun long() = executableElement("long", false)
fun float() = executableElement("float", false)
fun double() = executableElement("double", false)

fun Boolean(nullable: Boolean = false) = executableElement("java.lang.Boolean", nullable)
fun Byte(nullable: Boolean = false) = executableElement("java.lang.Byte", nullable)
fun Char(nullable: Boolean = false) = executableElement("java.lang.Char", nullable)
fun Short(nullable: Boolean = false) = executableElement("java.lang.Short", nullable)
fun Int(nullable: Boolean = false) = executableElement("java.lang.Integer", nullable)
fun Long(nullable: Boolean = false) = executableElement("java.lang.Long", nullable)
fun Float(nullable: Boolean = false) = executableElement("java.lang.Float", nullable)
fun Double(nullable: Boolean = false) = executableElement("java.lang.Double", nullable)
fun String(nullable: Boolean = false) = executableElement("java.lang.String", nullable)
fun CharSequence(nullable: Boolean = false) = executableElement("java.lang.CharSequence", nullable)

private fun executableElement(returnType: String = "test", nullable: Boolean) =
    object : ExecutableElement {
        override fun getDefaultValue(): AnnotationValue {
            WONTDO()
        }

        override fun getModifiers(): MutableSet<Modifier> {
            WONTDO()
        }

        override fun getSimpleName(): Name {
            WONTDO()
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
            if (p0?.equals(org.jetbrains.annotations.NotNull::class.java) == true && !nullable) {
                Proxy.newProxyInstance(
                    org.jetbrains.annotations.NotNull::class.java.classLoader,
                    arrayOf(org.jetbrains.annotations.NotNull::class.java)
                ) { _, _, _ -> WONTDO() } as A
            } else {
                null
            }

        override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
            WONTDO()
        }

        override fun getEnclosedElements(): MutableList<out Element> {
            WONTDO()
        }
    }