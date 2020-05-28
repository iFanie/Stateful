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
                val space = if (index > 0) "    " else ""

                val name = getter.name.capitalize()
                val type = getter.type
                append(
                    """ |${space}${newValue(name, type)}
                        |    ${bothValues(name, type)}
                        |    ${newStateful(name)}
                        |    ${bothStatefuls(name)}
                    """.trimMargin()
                )

                if (index < statefulGetters.lastIndex) {
                    append("\n\n")
                }
            }
        }

    private fun newValue(name: String, type: String) = "fun on${name}Updated(new$name: $type) {}"

    private fun bothValues(name: String, type: String) =
        "fun on${name}Updated(current$name: $type?, new$name: $type) {}".replace("??", "?")

    private fun newStateful(name: String) =
        "fun on${name}Updated(new$statefulName: $statefulName) {}"

    private fun bothStatefuls(name: String) =
        "fun on${name}Updated(current$statefulName: $statefulName?, new$statefulName: $statefulName) {}"
}