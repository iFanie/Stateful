package dev.fanie.statefulcompiler

import dev.fanie.stateful.StatefulType
import javax.lang.model.element.ExecutableElement

@Suppress("DefaultLocale")
class WrapperBuilder(
    statefulPackage: String,
    private val statefulClass: String,
    private val statefulGetters: List<ExecutableElement>,
    statefulType: StatefulType
) : ClassBuilder {
    private val statefulName = statefulClass.replace("$statefulPackage.", "").capitalize()
    private val abstractName =
        if (statefulType == StatefulType.INSTANCE) "AbstractStatefulInstance" else "AbstractStatefulStack"

    override val classPackage = "$statefulPackage.stateful"
    override val className = "Stateful$statefulName"
    override val classSource
        get() = """     |package $classPackage
                        |
                        |$classImports
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