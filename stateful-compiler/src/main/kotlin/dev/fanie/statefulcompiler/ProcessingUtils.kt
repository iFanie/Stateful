package dev.fanie.statefulcompiler

import javax.lang.model.element.Modifier
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement
import javax.lang.model.util.ElementFilter
import javax.lang.model.util.Elements

class ProcessingUtils(private val elementUtils: Elements) {
    fun getPackageOf(typeElement: TypeElement): PackageElement =
        elementUtils.getPackageOf(typeElement)

    fun getClassOf(typeElement: TypeElement) = typeElement.asType().toString()

    fun getGettersOf(typeElement: TypeElement) =
        ElementFilter.methodsIn(elementUtils.getAllMembers(typeElement)).filter { method ->
            !method.simpleName.startsWith(CLASS_GETTER_PREFIX)
                    && method.modifiers.contains(Modifier.PUBLIC)
                    && (method.simpleName.startsWith(GETTER_PREFIX)
                    || method.simpleName.startsWith(BOOLEAN_GETTER_PREFIX))
        }
}