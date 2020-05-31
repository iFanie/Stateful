package dev.fanie.statefulcompiler

import dev.fanie.ktap.element.isOptional
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.ArrayType
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror

object TypeMatcher {
    private val standardTypeMap = mapOf(
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
        "java.util.Map" to Map::class,

        "java.lang.Throwable" to Throwable::class
    )

    private val primitiveArrayTypeMap = mapOf(
        "boolean" to BooleanArray::class,
        "byte" to ByteArray::class,
        "char" to CharArray::class,
        "short" to ShortArray::class,
        "int" to IntArray::class,
        "long" to LongArray::class,
        "float" to FloatArray::class,
        "double" to DoubleArray::class,

        "java.lang.Boolean" to BooleanArray::class,
        "java.lang.Byte" to ByteArray::class,
        "java.lang.Char" to CharArray::class,
        "java.lang.Short" to ShortArray::class,
        "java.lang.Integer" to IntArray::class,
        "java.lang.Long" to LongArray::class,
        "java.lang.Float" to FloatArray::class,
        "java.lang.Double" to DoubleArray::class
    )

    fun match(element: ExecutableElement): String = matchType(element.returnType) + element.isOptional().delimiter

    private fun matchType(type: TypeMirror) = when (type.kind) {
        TypeKind.DECLARED -> matchDeclaredType(type as DeclaredType)
        TypeKind.ARRAY -> matchArrayType(type as ArrayType)
        else -> matchOrSelf(type.toString())
    }

    private fun matchDeclaredType(type: DeclaredType): String {
        val generics = type.typeArguments
        if (generics.isEmpty()) {
            return matchOrSelf(type.toString())
        }

        val actualType = type.toString().split('<').first()
        val paramTypes = generics.map { matchType(it) }

        return "${matchOrSelf(actualType)}<${paramTypes.concat()}>"
    }

    private fun matchArrayType(type: ArrayType): String {
        val componentType = type.componentType

        if (componentType.kind.isPrimitive) {
            return primitiveArrayTypeMap[componentType.toString()]?.qualifiedName
                ?: throw NoSuchElementException("Unknown primitive array: ${componentType.kind}")
        }

        if (componentType.kind == TypeKind.DECLARED) {
            val matchedType = matchDeclaredType(componentType as DeclaredType)
            return primitiveArrayTypeMap[matchedType]?.qualifiedName ?: "kotlin.Array<$matchedType>"
        }

        if (componentType.kind == TypeKind.ARRAY) {
            val matchedType = matchArrayType(componentType as ArrayType)
            return "kotlin.Array<$matchedType>"
        }

        return matchOrSelf(componentType.toString())
    }

    private fun matchOrSelf(self: String) = standardTypeMap[self]?.qualifiedName ?: self

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
