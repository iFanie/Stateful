package type

object FileTreeDependency {
    fun asMap() = mapOf(
        "dir" to "libs",
        "include" to listOf("*.jar")
    )
}

interface Dependency {
    val group: String
    val name: String
    val version: String

    fun asMap() = mapOf(
        "group" to group,
        "name" to name,
        "version" to version
    )
}

data class ClasspathDependency(
    override val group: String,
    override val name: String,
    override val version: String
) : Dependency

data class ImplementationDependency(
    override val group: String,
    override val name: String,
    override val version: String
) : Dependency

data class ApiDependency(
    override val group: String,
    override val name: String,
    override val version: String
) : Dependency

data class TestImplementationDependency(
    override val group: String,
    override val name: String,
    override val version: String
) : Dependency
