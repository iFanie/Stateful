import type.moduleDependency
import type.ModuleDependency


object Modules {
    val STATEFUL: ModuleDependency = moduleDependency(":stateful")
    val STATEFUL_COMPILER = moduleDependency(":stateful-compiler")
    val APP = moduleDependency(":app")

    fun getPaths() = listOf(
        STATEFUL.path.value,
        STATEFUL_COMPILER.path.value,
        APP.path.value
    )
}
