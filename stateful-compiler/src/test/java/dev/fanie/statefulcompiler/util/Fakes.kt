package dev.fanie.statefulcompiler.util

import dev.fanie.statefulcompiler.StatefulCompiler
import java.io.Writer
import java.util.*
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Name
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

internal fun statefulCompiler() = StatefulCompiler()

internal fun processingEnvironment() = object : ProcessingEnvironment {
    override fun getElementUtils(): Elements = object : Elements {
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

        override fun getAllMembers(p0: TypeElement?): MutableList<out Element> {
            wontDo()
        }

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

        override fun getPackageOf(p0: Element?): PackageElement {
            wontDo()
        }

        override fun getAllAnnotationMirrors(p0: Element?): MutableList<out AnnotationMirror> {
            wontDo()
        }
    }

    override fun getTypeUtils(): Types {
        wontDo()
    }

    override fun getMessager(): Messager = object : Messager {
        override fun printMessage(p0: Diagnostic.Kind?, p1: CharSequence?) {
            wontDo()
        }

        override fun printMessage(p0: Diagnostic.Kind?, p1: CharSequence?, p2: Element?) {
            wontDo()
        }

        override fun printMessage(
            p0: Diagnostic.Kind?,
            p1: CharSequence?,
            p2: Element?,
            p3: AnnotationMirror?
        ) {
            wontDo()
        }

        override fun printMessage(
            p0: Diagnostic.Kind?,
            p1: CharSequence?,
            p2: Element?,
            p3: AnnotationMirror?,
            p4: AnnotationValue?
        ) {
            wontDo()
        }
    }

    override fun getLocale(): Locale {
        wontDo()
    }

    override fun getSourceVersion(): SourceVersion {
        wontDo()
    }

    override fun getOptions(): MutableMap<String, String> {
        return mutableMapOf()
    }

    override fun getFiler(): Filer {
        wontDo()
    }
}