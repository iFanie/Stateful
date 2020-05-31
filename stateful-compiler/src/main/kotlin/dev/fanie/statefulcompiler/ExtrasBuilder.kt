package dev.fanie.statefulcompiler

import dev.fanie.stateful.StatefulExtra

@Suppress("DefaultLocale")
class ExtrasBuilder(
    statefulPackage: String,
    statefulClass: String,
    private val extras: Set<StatefulExtra>
) : ClassBuilder {
    private val statefulName = statefulClass.replace("$statefulPackage.", "").capitalize()

    override val classPackage = "$statefulPackage.stateful"
    override val className = "Stateful${statefulName}Extras"

    override val classSource: String
        get() = """
            |package $classPackage
            |
            |$classImports
            |
            |$extrasSource
            |
            """.trimMargin()

    private val classImports = buildString {
        val imports = listOf(
            statefulClass,
            "javax.annotation.Generated"
        ).sorted()

        imports.forEachIndexed { index, import ->
            append("import $import")

            if (index < imports.lastIndex) {
                append("\n")
            }
        }
    }

    private val extrasSource
        get() = buildString {
            extras.forEachIndexed { index, extra ->
                val extraSource = extra.source
                if (extraSource.isNotEmpty()) {
                    append(extra.source)
                    if (index < extras.size - 1) {
                        append("\n\n")
                    }
                }
            }
        }

    private val StatefulExtra.source
        get() = when (this) {
            StatefulExtra.LAZY_INIT -> lazyInit()
            StatefulExtra.NON_CASCADING_LISTENER -> ""
        }

    private fun lazyInit() = """/**
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
            |) = lazy(lazyMode) { Stateful$statefulName(listener, initial$statefulName) }
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
            """.trimIndent()
}