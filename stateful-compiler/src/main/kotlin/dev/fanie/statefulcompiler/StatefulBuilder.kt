package dev.fanie.statefulcompiler

import javax.lang.model.element.ExecutableElement

class StatefulBuilder(
    statefulPackage: String,
    private val statefulClass: String,
    private val statefulGetters: List<ExecutableElement>
) : ClassBuilder {
    private val statefulName = statefulClass.replace("$statefulPackage.", "").capitalize()
    private val updateListenerName = "${statefulName.decapitalize()}UpdateListener"

    override val classPackage = "$statefulPackage.stateful"
    override val className = "Stateful$statefulName"
    override val classSource
        get() = """     |package $classPackage
                        |
                        |import dev.fanie.stateful.AbstractStatefulInstance
                        |import java.util.Objects.equals
                        |import $statefulClass
                        |
                        |class $className(
                        |    private val $updateListenerName: Stateful${statefulName}Listener,
                        |    initial$statefulName: $statefulName? = null
                        |) : AbstractStatefulInstance<$statefulName>(initial$statefulName) {
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