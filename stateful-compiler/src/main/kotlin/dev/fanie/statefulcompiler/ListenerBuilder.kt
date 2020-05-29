package dev.fanie.statefulcompiler

import javax.lang.model.element.ExecutableElement

@Suppress("DefaultLocale")
class ListenerBuilder(
    statefulPackage: String,
    private val statefulClass: String,
    private val statefulGetters: List<ExecutableElement>
) : ClassBuilder {
    private val statefulName = statefulClass.replace("$statefulPackage.", "").capitalize()

    override val classPackage = "$statefulPackage.stateful"
    override val className = "Stateful${statefulName}Listener"
    override val classSource
        get() = """     |package $classPackage
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

    private val classImports = buildString {
        val imports = listOf(
            "javax.annotation.Generated",
            statefulClass
        ).sorted()

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
                val type = getter.type
                val initialSpace = if (index > 0) "    " else ""

                append(initialSpace)
                append(newValue(name, type))
                append("\n\n")
                append(bothValues(name, type))
                append("\n\n")
                append(newStateful(name))
                append("\n\n")
                append(bothStatefuls(name))

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