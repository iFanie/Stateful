package dev.fanie.statefulcompiler

import dev.fanie.stateful.StatefulType
import javax.lang.model.element.ExecutableElement

@Suppress("DefaultLocale")
class WrapperBuilder(
    statefulPackage: String,
    private val statefulClass: String,
    private val statefulGetters: List<ExecutableElement>,
    statefulType: StatefulType,
    private val noLazyInit: Boolean
) : ClassBuilder {
    private val statefulName = statefulClass.replace("$statefulPackage.", "").capitalize()
    private val interfaceName =
        if (statefulType == StatefulType.INSTANCE) "StatefulInstance" else "StatefulStack"
    private val abstractName =
        if (statefulType == StatefulType.INSTANCE) "AbstractStatefulInstance" else "AbstractStatefulStack"

    override val classPackage = "$statefulPackage.stateful"
    override val className = "Stateful$statefulName"
    override val classSource
        get() = """     |package $classPackage
                        |
                        |$classImports
                        |
                        |$initializers
                        |
                        |/**
                        | * Implementation of the [$abstractName] for the [$statefulName] type.
                        | */
                        |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
                        |class $className(
                        |    private val listener: Stateful${statefulName}Listener,
                        |    initial$statefulName: $statefulName? = null
                        |) : $abstractName<$statefulName>(initial$statefulName) {
                        |    override fun announce(currentInstance: $statefulName?, newInstance: $statefulName) {
                        |        $invocations
                        |    }
                        |}
                        |
        """.trimMargin()

    private val classImports = buildString {
        val imports = listOf(
            "dev.fanie.stateful.$interfaceName",
            "dev.fanie.stateful.$abstractName",
            statefulClass,
            "java.util.Objects.equals",
            "javax.annotation.Generated"
        ).sorted()

        imports.forEachIndexed { index, import ->
            append("import $import")

            if (index < imports.lastIndex) {
                append("\n")
            }
        }
    }

    private val initializers = buildString {
        append(
            """
                        |/**
                        | * Creates a new instance of the [$className] type.
                        | * @param listener The [Stateful${statefulName}Listener] instance to be invoked upon updates.
                        | * @param initial$statefulName The initial ${statefulName.decapitalize()} to be provided. Default value is {@code null}.
                        | * @return A new instance of the [$className] type.
                        | */
                        |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
                        |fun ${className.decapitalize()}(
                        |    listener: Stateful${statefulName}Listener,
                        |    initial$statefulName: $statefulName? = null
                        |) : $interfaceName<$statefulName> = $className(listener, initial$statefulName)
            """.trimMargin()
        )

        if (!noLazyInit) {
            append(
                """
                        |
                        |
                        |/**
                        | * Provides a lazy initializer for the [Stateful$statefulName] type.
                        | * @param listener The [Stateful${statefulName}Listener] instance to be invoked upon updates.
                        | * @param initial$statefulName The initial ${statefulName.decapitalize()} to be provided. Default value is {@code null}.
                        | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is {@code LazyThreadSafetyMode.SYNCHRONIZED}.
                        | * @return A lazy initializer for the [Stateful$statefulName] type.
                        | */
                        |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
                        |fun stateful(
                        |    listener: Stateful${statefulName}Listener,
                        |    initial$statefulName: ${statefulName}? = null,
                        |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
                        |) = lazy(lazyMode) { ${className.decapitalize()}(listener, initial$statefulName) }
                        |
                        |/**
                        | * Provides a lazy initializer for the [Stateful$statefulName] type, invoking the receiving [Stateful${statefulName}Listener] instance.
                        | * @param initial$statefulName The initial  ${statefulName.decapitalize()}  to be provided. Default value is {@code null}.
                        | * @param lazyMode The [LazyThreadSafetyMode] for the instance creation. Default value is {@code LazyThreadSafetyMode.SYNCHRONIZED}.
                        | * @return A lazy initializer for the [Stateful$statefulName] type.
                        | */
                        |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
                        |@JvmName("extensionStateful")
                        |fun Stateful${statefulName}Listener.stateful(
                        |    initial$statefulName: ${statefulName}? = null,
                        |    lazyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED
                        |) = stateful(this, initial$statefulName, lazyMode)
                """.trimMargin()
            )
        }
    }

    private val invocations
        get() = buildString {
            statefulGetters.forEachIndexed { index, getter ->
                val space = if (index > 0) "        " else ""

                val name = getter.name
                append(
                    """ |${space}if (!equals(currentInstance?.$name, newInstance.$name)) {
                        |            ${newValue(name)}
                        |            ${bothValues(name)}
                        |            ${newStateful(name)}
                        |            ${bothStatefuls(name)}
                        |        }
                    """.trimMargin()
                )

                if (index < statefulGetters.lastIndex) {
                    append("\n\n")
                }
            }
        }

    private fun newValue(name: String) =
        "listener.on${name.capitalize()}Updated(newInstance.$name)"

    private fun bothValues(name: String) =
        "listener.on${name.capitalize()}Updated(currentInstance?.$name, newInstance.$name)"

    private fun newStateful(name: String) =
        "listener.on${name.capitalize()}Updated(newInstance)"

    private fun bothStatefuls(name: String) =
        "listener.on${name.capitalize()}Updated(currentInstance, newInstance)"
}