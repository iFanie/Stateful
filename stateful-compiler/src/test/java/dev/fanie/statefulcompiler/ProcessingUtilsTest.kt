package dev.fanie.statefulcompiler

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import java.io.Writer
import javax.lang.model.element.*
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVisitor
import javax.lang.model.util.Elements

class ProcessingUtilsTest {
    private lateinit var underTest: ProcessingUtils

    @Before
    fun setUp() {
        val elementUtils = elements()
        underTest = ProcessingUtils(elementUtils)
    }

    @Test
    fun `when reading the package of a typeElement, then the result is the expected`() {
        val packageName = "test.type.element"
        val typeElement = typeElement("Test", packageName)

        val result = underTest.getPackageOf(typeElement).toString()

        assertEquals(packageName, result)
    }

    @Test
    fun `when reading the class of a typeElement, then the result is the expected`() {
        val packageName = "test.package"
        val name = "TestClass"
        val className = "$packageName.$name"
        val typeElement = typeElement(name, packageName)

        val result = underTest.getClassOf(typeElement)

        assertEquals(className, result)
    }

    @Test
    fun `when reading the getters of a typeElement, then the class getter is ignored`() {
        val element = typeElement(
            "a",
            "b",
            methodElement("getA", Modifier.PUBLIC),
            methodElement("getClass", Modifier.PUBLIC),
            methodElement("getB", Modifier.PUBLIC)
        )

        val result = underTest.getGettersOf(element)

        result.forEach { getter ->
            if (getter.simpleName.startsWith(CLASS_GETTER_PREFIX)) {
                fail("Class getter was not ignored.")
            }
        }
    }

    @Test
    fun `when reading the getters of a typeElement, then the non public getters are ignored`() {
        val element = typeElement(
            "a",
            "b",
            methodElement("getA", Modifier.PRIVATE),
            methodElement("getB", Modifier.PROTECTED),
            methodElement("getC", Modifier.PUBLIC)
        )

        val result = underTest.getGettersOf(element)

        result.forEach { getter ->
            if (getter.modifiers.contains(Modifier.PRIVATE)
                || getter.modifiers.contains(Modifier.PROTECTED)
            ) {
                fail("Non public getter was not ignored.")
            }
        }
    }

    @Test
    fun `when reading the getters of a typeElement, then get and is getters are present`() {
        val element = typeElement(
            "a",
            "b",
            methodElement("getA", Modifier.PUBLIC),
            methodElement("isB", Modifier.PUBLIC),
            methodElement("somethingElse", Modifier.PUBLIC)
        )

        val result = underTest.getGettersOf(element)

        result.forEach { getter ->
            if (getter.simpleName.toString() != "getA" && getter.simpleName.toString() != "isB") {
                fail("Getter is not one of the expected.")
            }
        }
    }
}

private fun elements() = object : Elements {
    override fun hides(p0: Element?, p1: Element?): Boolean {
        WONTDO()
    }

    override fun overrides(
        p0: ExecutableElement?,
        p1: ExecutableElement?,
        p2: TypeElement?
    ): Boolean {
        WONTDO()
    }

    override fun getName(p0: CharSequence?): Name {
        WONTDO()
    }

    override fun isFunctionalInterface(p0: TypeElement?): Boolean {
        WONTDO()
    }

    override fun getElementValuesWithDefaults(p0: AnnotationMirror?): MutableMap<out ExecutableElement, out AnnotationValue> {
        WONTDO()
    }

    override fun getBinaryName(p0: TypeElement?): Name {
        WONTDO()
    }

    override fun getDocComment(p0: Element?): String {
        WONTDO()
    }

    override fun isDeprecated(p0: Element?): Boolean {
        WONTDO()
    }

    override fun getAllMembers(p0: TypeElement): MutableList<out Element> = p0.enclosedElements

    override fun printElements(p0: Writer?, vararg p1: Element?) {
        WONTDO()
    }

    override fun getPackageElement(p0: CharSequence?): PackageElement {
        WONTDO()
    }

    override fun getTypeElement(p0: CharSequence?): TypeElement {
        WONTDO()
    }

    override fun getConstantExpression(p0: Any?): String {
        WONTDO()
    }

    override fun getPackageOf(p0: Element): PackageElement = object : PackageElement {
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

        override fun isUnnamed(): Boolean {
            WONTDO()
        }

        override fun getQualifiedName(): Name {
            WONTDO()
        }

        override fun getEnclosingElement(): Element {
            WONTDO()
        }

        override fun <R : Any?, P : Any?> accept(p0: ElementVisitor<R, P>?, p1: P): R {
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

        override fun getEnclosedElements(): MutableList<out Element> {
            WONTDO()
        }

        override fun toString(): String {
            val simpleName = p0.simpleName.toString()
            val packageAndName = simpleName.split(".")
            return simpleName.replaceFirst(".${packageAndName[packageAndName.size - 1]}", "")
        }
    }

    override fun getAllAnnotationMirrors(p0: Element?): MutableList<out AnnotationMirror> {
        WONTDO()
    }
}

private fun typeElement(
    name: String = "test",
    packageName: String = "test",
    vararg members: Element
) = object : TypeElement {
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

        override fun toString(): String = "$packageName.$name"
    }

    override fun getKind(): ElementKind {
        WONTDO()
    }

    override fun asType(): TypeMirror = object : TypeMirror {
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

        override fun toString(): String = "$packageName.$name"
    }

    override fun getSuperclass(): TypeMirror {
        WONTDO()
    }

    override fun getTypeParameters(): MutableList<out TypeParameterElement> {
        WONTDO()
    }

    override fun getQualifiedName(): Name {
        WONTDO()
    }

    override fun getEnclosingElement(): Element {
        WONTDO()
    }

    override fun getInterfaces(): MutableList<out TypeMirror> {
        WONTDO()
    }

    override fun <R : Any?, P : Any?> accept(p0: ElementVisitor<R, P>?, p1: P): R {
        WONTDO()
    }

    override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
        WONTDO()
    }

    override fun <A : Annotation?> getAnnotation(p0: Class<A>?): A {
        WONTDO()
    }

    override fun getNestingKind(): NestingKind {
        WONTDO()
    }

    override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
        WONTDO()
    }

    override fun getEnclosedElements(): MutableList<out Element> = members.toMutableList()
}

private fun methodElement(name: String = "test", vararg modifiers: Modifier) =
    object : ExecutableElement {
        override fun getDefaultValue(): AnnotationValue {
            WONTDO()
        }

        override fun getModifiers(): MutableSet<Modifier> = modifiers.toMutableSet()

        override fun getSimpleName(): Name = object : Name {
            override fun get(index: Int): Char = name[index]

            override fun contentEquals(p0: CharSequence): Boolean = name.contentEquals(p0)

            override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
                name.subSequence(startIndex, endIndex)

            override val length: Int = name.length

            override fun toString(): String = name
        }

        override fun getKind(): ElementKind = ElementKind.METHOD

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
