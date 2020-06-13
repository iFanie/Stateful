object Modules {
    val STATEFUL = mapOf("path" to ":stateful")
    val STATEFUL_COMPILER = mapOf("path" to ":stateful-compiler")
    val APP = mapOf("path" to ":app")

    fun getPaths() = listOf(
        requireNotNull(STATEFUL["path"]),
        requireNotNull(STATEFUL_COMPILER["path"]),
        requireNotNull(APP["path"])
    )
}
