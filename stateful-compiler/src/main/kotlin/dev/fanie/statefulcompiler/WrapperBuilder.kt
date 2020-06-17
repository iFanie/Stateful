package dev.fanie.statefulcompiler

import dev.fanie.ktap.element.isOptional
import dev.fanie.stateful.AbstractStatefulInstance
import dev.fanie.stateful.AbstractStatefulLinkedList
import dev.fanie.stateful.AbstractStatefulStack
import dev.fanie.stateful.StatefulInstance
import dev.fanie.stateful.StatefulLinkedList
import dev.fanie.stateful.StatefulStack
import dev.fanie.stateful.StatefulType
import javax.lang.model.element.ExecutableElement

@Suppress("DefaultLocale")
class WrapperBuilder(
    statefulPackage: String,
    private val statefulClass: String,
    private val statefulGetters: List<ExecutableElement>,
    statefulType: StatefulType,
    private val noLazyInit: Boolean,
    private val noDiffing: Boolean,
    private val withListener: Boolean,
    private val allowMissingRenderers: Boolean
) : ClassBuilder {
    private val statefulName = statefulClass.replace("$statefulPackage.", "").capitalize()

    private val interfaceClass = when (statefulType) {
        StatefulType.INSTANCE -> StatefulInstance::class
        StatefulType.STACK -> StatefulStack::class
        StatefulType.LINKED_LIST -> StatefulLinkedList::class
    }
    private val abstractClass = when (statefulType) {
        StatefulType.INSTANCE -> AbstractStatefulInstance::class
        StatefulType.STACK -> AbstractStatefulStack::class
        StatefulType.LINKED_LIST -> AbstractStatefulLinkedList::class
    }

    override val classPackage = "$statefulPackage.stateful.${statefulName.toLowerCase()}"
    override val className = "Stateful$statefulName"
    override val classSource
        get() = """     |package $classPackage
                        |
                        |$classImports
                        |
                        |$initializers
                        |
                        |/**
                        | * Implementation of the [${abstractClass.simpleName}] for the [$statefulName] type.
                        | */
                        |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
                        |class $className(
                        |    private val listener: $listenerType,
                        |    initial$statefulName: $statefulName? = null
                        |) : ${abstractClass.simpleName}<$statefulName>(initial$statefulName) {
                        |    $body
                        |}
                        |
        """.trimMargin()

    private val classImports = buildString {
        val imports = mutableListOf(
            requireNotNull(interfaceClass.qualifiedName),
            requireNotNull(abstractClass.qualifiedName),
            statefulClass,
            "java.util.Objects.equals",
            "javax.annotation.Generated"
        ).apply {
            if (!withListener) {
                add("kotlin.reflect.KClass")
                add("dev.fanie.stateful.StatefulProperty")
                add("dev.fanie.stateful.buildPropertyRenderers")
                add("dev.fanie.stateful.StatefulRendererCache")
                add("dev.fanie.stateful.statefulRendererCache")
            }
            statefulGetters.forEach { addAll(it.type.imports) }
        }.toSet().sorted()

        imports.forEachIndexed { index, import ->
            append("import $import")

            if (index < imports.lastIndex) {
                append("\n")
            }
        }
    }

    private val listenerType = if (withListener) "Stateful${statefulName}Listener" else "Any"

    private val initializers = buildString {
        append(
            """
                        |/**
                        | * Creates a new instance of the [$className] type.
                        | * @param listener The [$listenerType] instance to be invoked upon updates.
                        | * @param initial$statefulName The initial ${statefulName.decapitalize()} to be provided. Default value is null.
                        | * @return A new instance of the [$className] type.
                        | */
                        |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
                        |fun ${className.decapitalize()}(
                        |    listener: $listenerType,
                        |    initial$statefulName: $statefulName? = null
                        |) : ${interfaceClass.simpleName}<$statefulName> = $className(listener, initial$statefulName)
            """.trimMargin()
        )

        if (!noLazyInit) {
            append(
                """
                        |
                        |
                        |/**
                        | * Provides a lazy initializer for the [Stateful$statefulName] type.
                        | * @param listener The [$listenerType] instance to be invoked upon updates.
                        | * @param initial$statefulName The initial ${statefulName.decapitalize()} to be provided. Default value is null.
                        | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
                        | * @return A lazy initializer for the [Stateful$statefulName] type.
                        | */
                        |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
                        |fun stateful(
                        |    listener: $listenerType,
                        |    initial$statefulName: ${statefulName}? = null,
                        |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
                        |) = lazy(lazyMode) { ${className.decapitalize()}(listener, initial$statefulName) }
                        |
                        |/**
                        | * Provides a lazy initializer for the [Stateful$statefulName] type, invoking the receiving [Stateful${statefulName}Listener] instance.
                        | * @param initial$statefulName The initial  ${statefulName.decapitalize()}  to be provided. Default value is null.
                        | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is [LazyThreadSafetyMode.SYNCHRONIZED].
                        | * @return A lazy initializer for the [Stateful$statefulName] type.
                        | */
                        |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
                        |@JvmName("extensionStateful")
                        |fun $listenerType.stateful(
                        |    initial$statefulName: ${statefulName}? = null,
                        |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
                        |) = stateful(this, initial$statefulName, lazyMode)
                """.trimMargin()
            )
        }
    }

    private val body
        get() = if (withListener) {
            """override fun announce(currentInstance: $statefulName?, newInstance: $statefulName) {
                        |        $callbackInvocations
                        |    }
            """.trimMargin()
        } else {
            """private val rendererCache by statefulRendererCache<$statefulName, Property<*>>(listener, allowMissingRenderers = $allowMissingRenderers)
                        |
                        |    override fun announce(currentInstance: $statefulName?, newInstance: $statefulName) {
                        |        $rendererInvocations
                        |    }
                        |
                        |    /**
                        |     * Enumerates the available [StatefulProperty] object implementations for the [$statefulName] type.
                        |     * @param Type The type of each respective property of the [$statefulName] type.
                        |     */
                        |    sealed class Property<Type : Any> : StatefulProperty<Type, $statefulName> {
                        |        $properties
                        |    }
            """.trimIndent()
        }

    private val callbackInvocations
        get() = buildString {
            statefulGetters.forEachIndexed { index, getter ->
                val name = getter.name
                val space = if (index > 0) "        " else ""

                if (!noDiffing) {
                    append(
                        """ |${space}if (!equals(currentInstance?.$name, newInstance.$name)) {
                            |            ${newValue(name)}
                            |            ${bothValues(name)}
                            |            ${newStateful(name)}
                            |            ${bothStatefuls(name)}
                            |        }
                        """.trimMargin()
                    )
                } else {
                    append(
                        """
                        |$space${singleValue(name)}
                        """.trimMargin()
                    )
                }

                if (index < statefulGetters.lastIndex) {
                    append('\n')
                    if (!noDiffing) {
                        append('\n')
                    }
                }
            }
        }

    private fun newValue(name: String) =
        "listener.on${name.capitalize()}Updated(newInstance.$name)"

    private fun singleValue(name: String) =
        "listener.on${name.capitalize()}(newInstance.$name)"

    private fun bothValues(name: String) =
        "listener.on${name.capitalize()}Updated(currentInstance?.$name, newInstance.$name)"

    private fun newStateful(name: String) =
        "listener.on${name.capitalize()}Updated(newInstance)"

    private fun bothStatefuls(name: String) =
        "listener.on${name.capitalize()}Updated(currentInstance, newInstance)"

    private val rendererInvocations
        get() = buildString {
            statefulGetters.forEachIndexed { index, getter ->
                val name = getter.name
                val space = if (index > 0) "        " else ""

                if (!noDiffing) {
                    append(
                        """ |${space}if (!equals(currentInstance?.$name, newInstance.$name)) {
                        |            rendererCache.getRenderers(Property.${name.toSnakeCase()})?.forEach { it(currentInstance, newInstance) }
                        |        }
                        """.trimMargin()
                    )
                } else {
                    append(
                        """
                        |rendererCache.getRenderers(Property.${name.toSnakeCase()})?.forEach { it(currentInstance, newInstance) }
                        """.trimMargin()
                    )
                }

                if (index < statefulGetters.lastIndex) {
                    append('\n')
                    if (!noDiffing) {
                        append('\n')
                    }
                }
            }
        }

    private val properties
        get() = buildString {
            statefulGetters.forEachIndexed { index, getter ->
                val name = getter.name.toSnakeCase()
                val type = getter.type.value.replace("?", "")
                val trueType = if (!type.startsWith("Array<")) type.split('<').first() else type
                val space = if (index > 0) "        " else ""
                val suppress = when {
                    type != trueType && name.contains('_') -> "@Suppress(\"UNCHECKED_CAST\", \"ClassName\")\n        "
                    type != trueType -> "@Suppress(\"UNCHECKED_CAST\")\n        "
                    name.contains('_') -> "@Suppress(\"ClassName\")\n        "
                    else -> ""
                }
                val cast = if (type != trueType) " as KClass<$type>" else ""

                append(
                    """
                    |${space}${suppress}object $name : Property<$type>() {
                    |            override val getter: Function1<$statefulName, ${getter.type.value}> = { model -> model.${getter.name} }
                    |            override val type: KClass<$type> = $trueType::class$cast
                    |            override val isOptional: Boolean = ${getter.isOptional()}
                    |            override val modelType: KClass<$statefulName> = $statefulName::class
                    |        }
                    """.trimMargin()
                )

                if (index < statefulGetters.lastIndex) {
                    append("\n\n")
                }
            }
        }

    private fun String.toSnakeCase() = buildString {
        this@toSnakeCase.forEachIndexed { index, char ->
            if (char.isUpperCase()) {
                if (index > 0) {
                    append('_')
                }
                append(char)
            } else {
                append(char.toUpperCase())
            }
        }
    }
}
