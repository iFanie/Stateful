package dev.fanie.statefulcompiler

import javax.lang.model.element.ExecutableElement

class StatefulBuilder(
    statefulPackage: String,
    private val statefulClass: String,
    private val statefulGetters: List<ExecutableElement>
) {
    private val statefulName = statefulClass.replace("$statefulPackage.", "").capitalize()
    private val updateListenerName = "${statefulName.decapitalize()}UpdateListener"
    val packageName = "$statefulPackage.stateful"
    val className = "Stateful$statefulName"
    val sourceCode
        get() = """     |package $packageName
                        |
                        |class $className(
                        |    private val $updateListenerName: ${statefulName}UpdateListener,
                        |    initial$statefulName: $statefulClass? = null
                        |) {
                        |    private var current$statefulName = initial$statefulName
                        |
                        |    fun accept(new$statefulName: $statefulClass) {
                        |        $invocations
                        |        current$statefulName = new$statefulName
                        |    }
                        |}
                        |
        """.trimMargin()

    private val invocations
        get() = buildString {
            statefulGetters.forEach { getter ->
                val name = getter.name
                append(
                    """ |
                        |        if (!java.util.Objects.equals(current$statefulName?.$name, new$statefulName.$name)) {
                        |            ${newValue(name)}
                        |            ${bothValues(name)}
                        |            ${newStateful(name)}
                        |            ${bothStatefuls(name)}
                        |        }
                        |
                    """.trimMargin()
                )
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