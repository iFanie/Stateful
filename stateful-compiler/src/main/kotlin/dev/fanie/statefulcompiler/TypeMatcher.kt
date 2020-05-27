package dev.fanie.statefulcompiler

import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement

object TypeMatcher {
    fun toKotlinType(element: ExecutableElement) =
        match(cleanup(element.returnType.toString()), element)

    private fun match(elementType: String?, element: Element) = elementType?.let { type ->
        when (type) {

            "boolean" -> Boolean::class.qualifiedName
            "byte" -> Byte::class.qualifiedName
            "char" -> Char::class.qualifiedName
            "short" -> Short::class.qualifiedName
            "int" -> Int::class.qualifiedName
            "long" -> Long::class.qualifiedName
            "float" -> Float::class.qualifiedName
            "double" -> Double::class.qualifiedName

            "java.lang.Boolean" -> parseNullable(Boolean::class.qualifiedName, element)
            "java.lang.Byte" -> parseNullable(Byte::class.qualifiedName, element)
            "java.lang.Char" -> parseNullable(Char::class.qualifiedName, element)
            "java.lang.Short" -> parseNullable(Short::class.qualifiedName, element)
            "java.lang.Integer" -> parseNullable(Int::class.qualifiedName, element)
            "java.lang.Long" -> parseNullable(Long::class.qualifiedName, element)
            "java.lang.Float" -> parseNullable(Float::class.qualifiedName, element)
            "java.lang.Double" -> parseNullable(Double::class.qualifiedName, element)

            "java.lang.String" -> parseNullable(String::class.qualifiedName, element)
            "java.lang.CharSequence" -> parseNullable(CharSequence::class.qualifiedName, element)

            else -> parseNullable(type, element)

        }
    } ?: "kotlin.Any"

    private fun cleanup(javaType: String) = javaType
        .replace("\\s*\\([^)]*\\)\\s*".toRegex(), "")
        .replace("?", "")

    private fun parseNullable(type: String?, element: Element) = type?.let { strongType ->
        strongType + if (element.getAnnotation(org.jetbrains.annotations.NotNull::class.java) == null) "?"
        else ""
    } ?: "kotlin.Any"
}