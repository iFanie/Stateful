package dev.fanie.statefulcompiler

import javax.lang.model.element.ExecutableElement

@Suppress("DefaultLocale")
class ListenerBuilder(
    statefulPackage: String,
    private val statefulClass: String,
    private val statefulGetters: List<ExecutableElement>,
    private val nonCascading: Boolean,
    private val noDiffing: Boolean
) : ClassBuilder {
    private val statefulName = statefulClass.replace("$statefulPackage.", "").capitalize()

    override val classPackage = "$statefulPackage.stateful.${statefulName.toLowerCase()}"
    override val className = "Stateful${statefulName}Listener"
    override val classSource
        get() = if (nonCascading) nonCascadingSource() else cascadingSource()

    private fun nonCascadingSource() =
        """ |package $classPackage
            |
            |$classImports
            |
            |/**
            | * Contains callbacks to be invoked on updates of each individual public property
            | * of an instance of the [$statefulName] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |interface $className {
            |    $functions
            |}
            |
        """.trimMargin()

    private fun cascadingSource() =
        """ |package $classPackage
            |
            |$classImports
            |
            |/**
            | * Contains callbacks to be invoked on updates of each individual public property
            | * of an instance of the [$statefulName] type.
            | */
            |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
            |interface $className : $subInterfaceNames
            |    
            |$subInterfaces
            |
        """.trimMargin()

    private val classImports = buildString {
        val imports = mutableListOf<String>().apply {
            add("javax.annotation.Generated")
            add(statefulClass)
            statefulGetters.forEach { addAll(it.type.imports) }
        }.toSet().sorted()

        imports.forEachIndexed { index, import ->
            append("import $import")

            if (index < imports.lastIndex) {
                append("\n")
            }
        }
    }

    private val functions
        get() = buildString {
            statefulGetters.forEachIndexed { index, getter ->
                val name = getter.name
                val type = getter.type.value
                val initialSpace = if (index > 0) "    " else ""

                append(initialSpace)

                if (!noDiffing) {
                    append(newValue(name, type))
                    append("\n\n")
                    append(bothValues(name, type))
                    append("\n\n")
                    append(newStateful(name))
                    append("\n\n")
                    append(bothStatefuls(name))
                } else {
                    append(singleValue(name, type))
                }

                if (index < statefulGetters.lastIndex) {
                    append("\n\n")
                }
            }
        }

    private val ExecutableElement.interfaceName get() = "Stateful$statefulName${this.name.capitalize()}Listener"

    private val subInterfaceNames
        get() = buildString {
            statefulGetters.forEachIndexed { index, getter ->
                if (index > 0) {
                    append("\n    ")
                }
                append(getter.interfaceName)
                if (index < statefulGetters.lastIndex) {
                    append(", ")
                }
            }
        }

    private val subInterfaces
        get() = buildString {
            statefulGetters.forEachIndexed { index, getter ->
                val name = getter.name
                val type = getter.type.value

                append(
                    """
                        |/**
                        | * Contains callbacks to be invoked on updates of the [$statefulName.$name] property.
                        | */
                        |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
                        |interface ${getter.interfaceName} {
                        |
                    """.trimMargin()
                )

                append("    ")
                if (!noDiffing) {
                    append(newValue(name, type))
                    append("\n\n")
                    append(bothValues(name, type))
                    append("\n\n")
                    append(newStateful(name))
                    append("\n\n")
                    append(bothStatefuls(name))
                } else {
                    append(singleValue(name, type))
                }
                append('\n')

                append('}')

                if (index < statefulGetters.lastIndex) {
                    append("\n\n")
                }
            }
        }

    private fun newValue(name: String, type: String) = """/**
            |     * Invoked on updates of the [$statefulName.$name] property.
            |     * @param new${name.capitalize()} The new $name to be rendered.
            |     */
            |    fun on${name.capitalize()}Updated(new${name.capitalize()}: $type) {}
            """.trimMargin()

    private fun singleValue(name: String, type: String) = """/**
            |     * Invoked on updates of the [$statefulName.$name] property.
            |     * @param $name The $name to be rendered.
            |     */
            |    fun on${name.capitalize()}($name: $type) {}
            """.trimMargin()

    private fun bothValues(name: String, type: String) = """
            |    /**
            |     * Invoked on updates of the [$statefulName.$name] property.
            |     * @param current${name.capitalize()} The currently rendered $name, if any.
            |     * @param new${name.capitalize()} The new $name to be rendered.
            |     */
            |     fun on${name.capitalize()}Updated(current${name.capitalize()}: $type?, new${name.capitalize()}: $type) {}
            """.trimMargin().replace("??", "?")

    private fun newStateful(name: String) = """
            |    /**
            |     * Invoked on updates of the [$statefulName.$name] property.
            |     * @param new$statefulName The new ${statefulName.decapitalize()} to be rendered.
            |     */
            |     fun on${name.capitalize()}Updated(new$statefulName: $statefulName) {}
            """.trimMargin()

    private fun bothStatefuls(name: String) = """
            |    /**
            |     * Invoked on updates of the [$statefulName.$name] property.
            |     * @param current$statefulName The currently rendered ${statefulName.decapitalize()}, if any.
            |     * @param new$statefulName The new ${statefulName.decapitalize()} to be rendered.
            |     */
            |     fun on${name.capitalize()}Updated(current$statefulName: $statefulName?, new$statefulName: $statefulName) {}
            """.trimMargin()
}
