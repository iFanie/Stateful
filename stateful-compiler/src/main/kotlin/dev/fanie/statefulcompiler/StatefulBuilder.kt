package dev.fanie.statefulcompiler

import dev.fanie.stateful.StatefulType
import javax.lang.model.element.ExecutableElement

@Suppress("DefaultLocale")
class StatefulBuilder(
    statefulPackage: String,
    private val statefulClass: String,
    private val statefulGetters: List<ExecutableElement>,
    statefulType: StatefulType
) : ClassBuilder {
    private val statefulName = statefulClass.replace("$statefulPackage.", "").capitalize()
    private val updateListenerName = "${statefulName.decapitalize()}UpdateListener"
    private val abstractName =
        if (statefulType == StatefulType.INSTANCE) "AbstractStatefulInstance" else "AbstractStatefulStack"

    override val classPackage = "$statefulPackage.stateful"
    override val className = "Stateful$statefulName"
    override val classSource
        get() = """     |package $classPackage
                        |
                        |import dev.fanie.stateful.$abstractName
                        |import java.util.Objects.equals
                        |import javax.annotation.Generated
                        |import $statefulClass
                        |
                        |/**
                        | * Implementation of the [$abstractName] for the [$statefulName] type.
                        | */
                        |@Generated("dev.fanie.statefulcompiler.StatefulCompiler")
                        |class $className(
                        |    private val $updateListenerName: Stateful${statefulName}Listener,
                        |    initial$statefulName: $statefulName? = null
                        |) : $abstractName<$statefulName>(initial$statefulName) {
                        |    final override fun announce(current$statefulName: $statefulName?, new$statefulName: $statefulName) {
                        |        $invocations
                        |    }
                        |}
                        |
        """.trimMargin()

    private val invocations
        get() = buildString {
            statefulGetters.forEachIndexed { index, getter ->
                val space = if (index > 0) "        " else ""

                val name = getter.name
                append(
                    """ |${space}if (!equals(current$statefulName?.$name, new$statefulName.$name)) {
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
        "$updateListenerName.on${name.capitalize()}Updated(new$statefulName.$name)"

    private fun bothValues(name: String) =
        "$updateListenerName.on${name.capitalize()}Updated(current$statefulName?.$name, new$statefulName.$name)"

    private fun newStateful(name: String) =
        "$updateListenerName.on${name.capitalize()}Updated(new$statefulName)"

    private fun bothStatefuls(name: String) =
        "$updateListenerName.on${name.capitalize()}Updated(current$statefulName, new$statefulName)"
}