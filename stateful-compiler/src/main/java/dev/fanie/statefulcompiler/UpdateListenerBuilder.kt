package dev.fanie.statefulcompiler

import javax.lang.model.element.ExecutableElement

class UpdateListenerBuilder(
    statefulPackage: String,
    private val statefulClass: String,
    private val statefulGetters: List<ExecutableElement>
) {
    private val statefulName = statefulClass.replace("$statefulPackage.", "").capitalize()
    val packageName = "$statefulPackage.stateful"
    val className = "${statefulName}UpdateListener"
    val sourceCode
        get() = """     |package $packageName
                        |
                        |interface $className {
                        |    $functions
                        |}
                        |
        """.trimMargin()

    private val functions
        get() = buildString {
            statefulGetters.forEach { getter ->
                val name = getter.name.capitalize()
                val type = getter.type
                append(
                    """ |
                        |    ${newValue(name, type)}
                        |    ${bothValues(name, type)}
                        |    ${newStateful(name)}
                        |    ${bothStatefuls(name)}
                        |
                    """.trimMargin()
                )
            }
        }

    private fun newValue(name: String, type: String) = "fun on${name}Updated(new$name: $type) {}"

    private fun bothValues(name: String, type: String) =
        "fun on${name}Updated(old$name: $type?, new$name: $type) {}".replace("??", "?")

    private fun newStateful(name: String) =
        "fun on${name}Updated(new$statefulName: $statefulClass) {}"

    private fun bothStatefuls(name: String) =
        "fun on${name}Updated(old$statefulName: $statefulClass?, new$statefulName: $statefulClass) {}"
}