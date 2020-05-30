package dev.fanie.statefulcompiler

import dev.fanie.ktap.element.isOptional
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror

object TypeMatcher {
    private val typeMap = mapOf(
        "boolean" to Boolean::class,
        "byte" to Byte::class,
        "char" to Char::class,
        "short" to Short::class,
        "int" to Int::class,
        "long" to Long::class,
        "float" to Float::class,
        "double" to Double::class,

        "java.lang.Boolean" to Boolean::class,
        "java.lang.Byte" to Byte::class,
        "java.lang.Char" to Char::class,
        "java.lang.Short" to Short::class,
        "java.lang.Integer" to Int::class,
        "java.lang.Long" to Long::class,
        "java.lang.Float" to Float::class,
        "java.lang.Double" to Double::class,

        "java.lang.String" to String::class,
        "java.lang.CharSequence" to CharSequence::class,

        "java.util.List" to List::class,
        "java.util.Set" to Set::class,
        "java.util.Map" to Map::class
    )

    fun match(element: ExecutableElement): String = matchType(element.returnType) + element.isOptional().delimiter

    private fun matchType(type: TypeMirror): String {
        if (type.kind != TypeKind.DECLARED) {
            return matchOrSelf(type.toString())
        }

        val declared = type as DeclaredType
        val generics = declared.typeArguments
        if (generics.isEmpty()) {
            return matchOrSelf(type.toString())
        }

        val actualType = declared.toString().split('<').first()
        val paramTypes = generics.map { matchType(it) }

        return "${matchOrSelf(actualType)}<${paramTypes.concat()}>"
    }

    private fun matchOrSelf(self: String) = typeMap[self]?.qualifiedName ?: self

    private fun List<String>.concat() = buildString {
        this@concat.forEachIndexed { index, string ->
            append(string)
            if (index < this@concat.lastIndex) {
                append(", ")
            }
        }
    }

    private val Boolean.delimiter get() = if (this) "?" else ""
}