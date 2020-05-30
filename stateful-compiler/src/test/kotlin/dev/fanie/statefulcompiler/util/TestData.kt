package dev.fanie.statefulcompiler.util

import dev.fanie.statefulcompiler.ClassBuilder
import java.io.Writer
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ElementVisitor
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.element.Name
import javax.lang.model.element.NestingKind
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVisitor
import javax.lang.model.util.Elements

internal fun getter(name: String = "test", type: String = "test") = object : ExecutableElement {
    override fun getDefaultValue(): AnnotationValue {
        wontDo()
    }

    override fun getModifiers(): MutableSet<Modifier> {
        wontDo()
    }

    override fun getSimpleName(): Name = object : Name {
        override fun get(index: Int): Char {
            wontDo()
        }

        override fun contentEquals(p0: CharSequence?): Boolean {
            wontDo()
        }

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            wontDo()
        }

        override val length: Int
            get() = wontDo()

        override fun toString(): String = name
    }

    override fun getKind(): ElementKind {
        wontDo()
    }

    override fun asType(): TypeMirror {
        wontDo()
    }

    override fun getReturnType(): TypeMirror = object : DeclaredType {
        override fun getKind(): TypeKind = TypeKind.DECLARED

        override fun <R : Any?, P : Any?> accept(p0: TypeVisitor<R, P>?, p1: P): R {
            wontDo()
        }

        override fun getTypeArguments(): List<TypeMirror> = listOf()

        override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
            wontDo()
        }

        override fun <A : Annotation?> getAnnotation(p0: Class<A>?): A {
            wontDo()
        }

        override fun asElement(): Element {
            wontDo()
        }

        override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
            wontDo()
        }

        override fun getEnclosingType(): TypeMirror {
            wontDo()
        }

        override fun toString(): String = type
    }

    override fun getReceiverType(): TypeMirror {
        wontDo()
    }

    override fun getThrownTypes(): MutableList<out TypeMirror> {
        wontDo()
    }

    override fun getTypeParameters(): MutableList<out TypeParameterElement> {
        wontDo()
    }

    override fun getEnclosingElement(): Element {
        wontDo()
    }

    override fun <R : Any?, P : Any?> accept(p0: ElementVisitor<R, P>?, p1: P): R {
        wontDo()
    }

    override fun getParameters(): MutableList<out VariableElement> {
        wontDo()
    }

    override fun isVarArgs(): Boolean {
        wontDo()
    }

    override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
        wontDo()
    }

    override fun isDefault(): Boolean {
        wontDo()
    }

    override fun <A : Annotation> getAnnotation(p0: Class<A>): A? = annotation(p0.kotlin)

    override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
        wontDo()
    }

    override fun getEnclosedElements(): MutableList<out Element> {
        wontDo()
    }
}

internal fun classBuilder(classPackage: String = "test", className: String = "test", classSource: String = "test") =
    object : ClassBuilder {
        override val classPackage: String = classPackage
        override val className: String = className
        override val classSource: String = classSource
    }

internal fun executableElement(name: String = "test", returnType: String = "test") =
    object : ExecutableElement {
        override fun getDefaultValue(): AnnotationValue {
            wontDo()
        }

        override fun getModifiers(): MutableSet<Modifier> {
            wontDo()
        }

        override fun getSimpleName(): Name = object : Name {
            override fun get(index: Int): Char {
                wontDo()
            }

            override fun contentEquals(p0: CharSequence?): Boolean {
                wontDo()
            }

            override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
                wontDo()
            }

            override val length: Int
                get() {
                    wontDo()
                }

            override fun toString(): String = name
        }

        override fun getKind(): ElementKind {
            wontDo()
        }

        override fun asType(): TypeMirror {
            wontDo()
        }

        override fun getReturnType(): TypeMirror = object : DeclaredType {
            override fun getKind(): TypeKind = TypeKind.DECLARED

            override fun <R : Any?, P : Any?> accept(p0: TypeVisitor<R, P>?, p1: P): R {
                wontDo()
            }

            override fun getTypeArguments(): List<TypeMirror> = listOf()

            override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
                wontDo()
            }

            override fun <A : Annotation?> getAnnotation(p0: Class<A>?): A {
                wontDo()
            }

            override fun asElement(): Element {
                wontDo()
            }

            override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
                wontDo()
            }

            override fun getEnclosingType(): TypeMirror {
                wontDo()
            }

            override fun toString(): String = returnType
        }

        override fun getReceiverType(): TypeMirror {
            wontDo()
        }

        override fun getThrownTypes(): MutableList<out TypeMirror> {
            wontDo()
        }

        override fun getTypeParameters(): MutableList<out TypeParameterElement> {
            wontDo()
        }

        override fun getEnclosingElement(): Element {
            wontDo()
        }

        override fun <R : Any?, P : Any?> accept(p0: ElementVisitor<R, P>?, p1: P): R {
            wontDo()
        }

        override fun getParameters(): MutableList<out VariableElement> {
            wontDo()
        }

        override fun isVarArgs(): Boolean {
            wontDo()
        }

        override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
            wontDo()
        }

        override fun isDefault(): Boolean {
            wontDo()
        }

        override fun <A : Annotation> getAnnotation(p0: Class<A>): A? = annotation(p0.kotlin)

        override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
            wontDo()
        }

        override fun getEnclosedElements(): MutableList<out Element> {
            wontDo()
        }

    }

internal fun elements() = object : Elements {
    override fun hides(p0: Element?, p1: Element?): Boolean {
        wontDo()
    }

    override fun overrides(
        p0: ExecutableElement?,
        p1: ExecutableElement?,
        p2: TypeElement?
    ): Boolean {
        wontDo()
    }

    override fun getName(p0: CharSequence?): Name {
        wontDo()
    }

    override fun isFunctionalInterface(p0: TypeElement?): Boolean {
        wontDo()
    }

    override fun getElementValuesWithDefaults(p0: AnnotationMirror?): MutableMap<out ExecutableElement, out AnnotationValue> {
        wontDo()
    }

    override fun getBinaryName(p0: TypeElement?): Name {
        wontDo()
    }

    override fun getDocComment(p0: Element?): String {
        wontDo()
    }

    override fun isDeprecated(p0: Element?): Boolean {
        wontDo()
    }

    override fun getAllMembers(p0: TypeElement): MutableList<out Element> = p0.enclosedElements

    override fun printElements(p0: Writer?, vararg p1: Element?) {
        wontDo()
    }

    override fun getPackageElement(p0: CharSequence?): PackageElement {
        wontDo()
    }

    override fun getTypeElement(p0: CharSequence?): TypeElement {
        wontDo()
    }

    override fun getConstantExpression(p0: Any?): String {
        wontDo()
    }

    override fun getPackageOf(p0: Element): PackageElement = object : PackageElement {
        override fun getModifiers(): MutableSet<Modifier> {
            wontDo()
        }

        override fun getSimpleName(): Name {
            wontDo()
        }

        override fun getKind(): ElementKind {
            wontDo()
        }

        override fun asType(): TypeMirror {
            wontDo()
        }

        override fun isUnnamed(): Boolean {
            wontDo()
        }

        override fun getQualifiedName(): Name {
            wontDo()
        }

        override fun getEnclosingElement(): Element {
            wontDo()
        }

        override fun <R : Any?, P : Any?> accept(p0: ElementVisitor<R, P>?, p1: P): R {
            wontDo()
        }

        override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
            wontDo()
        }

        override fun <A : Annotation?> getAnnotation(p0: Class<A>?): A {
            wontDo()
        }

        override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
            wontDo()
        }

        override fun getEnclosedElements(): MutableList<out Element> {
            wontDo()
        }

        override fun toString(): String {
            val simpleName = p0.simpleName.toString()
            val packageAndName = simpleName.split(".")
            return simpleName.replaceFirst(".${packageAndName[packageAndName.size - 1]}", "")
        }
    }

    override fun getAllAnnotationMirrors(p0: Element?): MutableList<out AnnotationMirror> {
        wontDo()
    }
}

internal fun typeElement(name: String = "test", packageName: String = "test", vararg members: Element) =
    object : TypeElement {
        override fun getModifiers(): MutableSet<Modifier> {
            wontDo()
        }

        override fun getSimpleName(): Name = object : Name {
            override fun get(index: Int): Char {
                wontDo()
            }

            override fun contentEquals(p0: CharSequence?): Boolean {
                wontDo()
            }

            override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
                wontDo()
            }

            override val length: Int
                get() = wontDo()

            override fun toString(): String = "$packageName.$name"
        }

        override fun getKind(): ElementKind {
            wontDo()
        }

        override fun asType(): TypeMirror = object : TypeMirror {
            override fun getKind(): TypeKind {
                wontDo()
            }

            override fun <R : Any?, P : Any?> accept(p0: TypeVisitor<R, P>?, p1: P): R {
                wontDo()
            }

            override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
                wontDo()
            }

            override fun <A : Annotation?> getAnnotation(p0: Class<A>?): A {
                wontDo()
            }

            override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
                wontDo()
            }

            override fun toString(): String = "$packageName.$name"
        }

        override fun getSuperclass(): TypeMirror {
            wontDo()
        }

        override fun getTypeParameters(): MutableList<out TypeParameterElement> {
            wontDo()
        }

        override fun getQualifiedName(): Name {
            wontDo()
        }

        override fun getEnclosingElement(): Element {
            wontDo()
        }

        override fun getInterfaces(): MutableList<out TypeMirror> {
            wontDo()
        }

        override fun <R : Any?, P : Any?> accept(p0: ElementVisitor<R, P>?, p1: P): R {
            wontDo()
        }

        override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
            wontDo()
        }

        override fun <A : Annotation?> getAnnotation(p0: Class<A>?): A {
            wontDo()
        }

        override fun getNestingKind(): NestingKind {
            wontDo()
        }

        override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
            wontDo()
        }

        override fun getEnclosedElements(): MutableList<out Element> = members.toMutableList()
    }

internal fun methodElement(name: String = "test", vararg modifiers: Modifier) = object : ExecutableElement {
    override fun getDefaultValue(): AnnotationValue {
        wontDo()
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
        wontDo()
    }

    override fun getReturnType(): TypeMirror {
        wontDo()
    }

    override fun getReceiverType(): TypeMirror {
        wontDo()
    }

    override fun getThrownTypes(): MutableList<out TypeMirror> {
        wontDo()
    }

    override fun getTypeParameters(): MutableList<out TypeParameterElement> {
        wontDo()
    }

    override fun getEnclosingElement(): Element {
        wontDo()
    }

    override fun <R : Any?, P : Any?> accept(p0: ElementVisitor<R, P>?, p1: P): R {
        wontDo()
    }

    override fun getParameters(): MutableList<out VariableElement> {
        wontDo()
    }

    override fun isVarArgs(): Boolean {
        wontDo()
    }

    override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
        wontDo()
    }

    override fun isDefault(): Boolean {
        wontDo()
    }

    override fun <A : Annotation?> getAnnotation(p0: Class<A>?): A {
        wontDo()
    }

    override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
        wontDo()
    }

    override fun getEnclosedElements(): MutableList<out Element> {
        wontDo()
    }
}

internal fun boolean() = declaredElement("boolean", false)
internal fun byte() = declaredElement("byte", false)
internal fun char() = declaredElement("char", false)
internal fun short() = declaredElement("short", false)
internal fun int() = declaredElement("int", false)
internal fun long() = declaredElement("long", false)
internal fun float() = declaredElement("float", false)
internal fun double() = declaredElement("double", false)

internal fun jBoolean(nullable: Boolean = false) = declaredElement("java.lang.Boolean", nullable)
internal fun jByte(nullable: Boolean = false) = declaredElement("java.lang.Byte", nullable)
internal fun jChar(nullable: Boolean = false) = declaredElement("java.lang.Char", nullable)
internal fun jShort(nullable: Boolean = false) = declaredElement("java.lang.Short", nullable)
internal fun jInt(nullable: Boolean = false) = declaredElement("java.lang.Integer", nullable)
internal fun jLong(nullable: Boolean = false) = declaredElement("java.lang.Long", nullable)
internal fun jFloat(nullable: Boolean = false) = declaredElement("java.lang.Float", nullable)
internal fun jDouble(nullable: Boolean = false) = declaredElement("java.lang.Double", nullable)
internal fun jString(nullable: Boolean = false) = declaredElement("java.lang.String", nullable)
internal fun jCharSequence(nullable: Boolean = false) = declaredElement("java.lang.CharSequence", nullable)

fun nonStandard(
    returnType: String = "test",
    nullable: Boolean = false,
    generics: List<String> = listOf()
) = declaredElement(returnType, nullable, generics)

internal fun list(generic: String, nullable: Boolean = false) =
    declaredElement("java.util.List", nullable, listOf(generic))

internal fun set(generic: String, nullable: Boolean = false) = declaredElement("java.util.Set", nullable, listOf(generic))

internal fun map(keyGeneric: String, valGeneric: String, nullable: Boolean = false) =
    declaredElement("java.util.Map", nullable, listOf(keyGeneric, valGeneric))

private fun declaredElement(
    returnType: String = "test",
    nullable: Boolean = false,
    generics: List<String> = listOf()
) = object : ExecutableElement {
    override fun getDefaultValue(): AnnotationValue {
        wontDo()
    }

    override fun getModifiers(): MutableSet<Modifier> {
        wontDo()
    }

    override fun getSimpleName(): Name {
        wontDo()
    }

    override fun getKind(): ElementKind {
        wontDo()
    }

    override fun asType(): TypeMirror {
        wontDo()
    }

    override fun getReturnType(): TypeMirror = object : DeclaredType {
        override fun getKind(): TypeKind = TypeKind.DECLARED

        override fun <R : Any?, P : Any?> accept(p0: TypeVisitor<R, P>?, p1: P): R {
            wontDo()
        }

        override fun getTypeArguments(): List<TypeMirror> = generics.map {
            object : DeclaredType {
                override fun getKind(): TypeKind = TypeKind.DECLARED

                override fun <R : Any?, P : Any?> accept(p0: TypeVisitor<R, P>?, p1: P): R {
                    wontDo()
                }

                override fun getTypeArguments(): List<TypeMirror> = listOf()

                override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
                    wontDo()
                }

                override fun <A : Annotation?> getAnnotation(p0: Class<A>?): A {
                    wontDo()
                }

                override fun asElement(): Element {
                    wontDo()
                }

                override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
                    wontDo()
                }

                override fun getEnclosingType(): TypeMirror {
                    wontDo()
                }

                override fun toString(): String = it
            }
        }

        override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
            wontDo()
        }

        override fun <A : Annotation?> getAnnotation(p0: Class<A>?): A {
            wontDo()
        }

        override fun asElement(): Element {
            wontDo()
        }

        override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
            wontDo()
        }

        override fun getEnclosingType(): TypeMirror {
            wontDo()
        }

        override fun toString(): String = returnType
    }

    override fun getReceiverType(): TypeMirror {
        wontDo()
    }

    override fun getThrownTypes(): MutableList<out TypeMirror> {
        wontDo()
    }

    override fun getTypeParameters(): MutableList<out TypeParameterElement> {
        wontDo()
    }

    override fun getEnclosingElement(): Element {
        wontDo()
    }

    override fun <R : Any?, P : Any?> accept(p0: ElementVisitor<R, P>?, p1: P): R {
        wontDo()
    }

    override fun getParameters(): MutableList<out VariableElement> {
        wontDo()
    }

    override fun isVarArgs(): Boolean {
        wontDo()
    }

    override fun <A : Annotation?> getAnnotationsByType(p0: Class<A>?): Array<A> {
        wontDo()
    }

    override fun isDefault(): Boolean {
        wontDo()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <A : Annotation> getAnnotation(p0: Class<A>): A? =
        if (p0 == org.jetbrains.annotations.NotNull::class.java && !nullable) {
            annotation(p0.kotlin) as A
        } else {
            null
        }

    override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
        wontDo()
    }

    override fun getEnclosedElements(): MutableList<out Element> {
        wontDo()
    }
}
