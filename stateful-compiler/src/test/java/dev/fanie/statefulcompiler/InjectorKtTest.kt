package dev.fanie.statefulcompiler

import org.junit.Test
import java.io.Writer
import java.util.*
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

class InjectorKtTest {
    @Test
    fun `when injecting to a StatefulCompiler, then the expected class generator is present`() {
        val compiler = statefulCompiler()
        val environment = processingEnvironment()
        inject(compiler, environment)

        compiler.classGenerator.toString()
    }

    @Test
    fun `when injecting to a StatefulCompiler, then the expected messager is present`() {
        val compiler = statefulCompiler()
        val environment = processingEnvironment()
        inject(compiler, environment)

        compiler.messager.toString()
    }

    @Test
    fun `when injecting to a StatefulCompiler, then the processing utils are present`() {
        val compiler = statefulCompiler()
        val environment = processingEnvironment()
        inject(compiler, environment)

        compiler.processingUtils.toString()
    }
}

private fun statefulCompiler() = StatefulCompiler()

private fun processingEnvironment() = object : ProcessingEnvironment {
    override fun getElementUtils(): Elements = object : Elements {
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

        override fun getAllMembers(p0: TypeElement?): MutableList<out Element> {
            WONTDO()
        }

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

        override fun getPackageOf(p0: Element?): PackageElement {
            WONTDO()
        }

        override fun getAllAnnotationMirrors(p0: Element?): MutableList<out AnnotationMirror> {
            WONTDO()
        }
    }

    override fun getTypeUtils(): Types {
        WONTDO()
    }

    override fun getMessager(): Messager = object : Messager {
        override fun printMessage(p0: Diagnostic.Kind?, p1: CharSequence?) {
            WONTDO()
        }

        override fun printMessage(p0: Diagnostic.Kind?, p1: CharSequence?, p2: Element?) {
            WONTDO()
        }

        override fun printMessage(
            p0: Diagnostic.Kind?,
            p1: CharSequence?,
            p2: Element?,
            p3: AnnotationMirror?
        ) {
            WONTDO()
        }

        override fun printMessage(
            p0: Diagnostic.Kind?,
            p1: CharSequence?,
            p2: Element?,
            p3: AnnotationMirror?,
            p4: AnnotationValue?
        ) {
            WONTDO()
        }
    }

    override fun getLocale(): Locale {
        WONTDO()
    }

    override fun getSourceVersion(): SourceVersion {
        WONTDO()
    }

    override fun getOptions(): MutableMap<String, String> {
        return mutableMapOf()
    }

    override fun getFiler(): Filer {
        WONTDO()
    }
}