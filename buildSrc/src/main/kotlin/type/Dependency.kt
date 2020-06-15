package type

interface DependencyScope {
    val value: String
}

sealed class Scope : DependencyScope {
    object CLASSPATH : Scope() {
        override val value: String = "classpath"
    }

    object IMPLEMENTATION : Scope() {
        override val value: String = "implementation"
    }

    @Suppress("ClassName")
    object TEST_IMPLEMENTATION : Scope() {
        override val value: String = "testImplementation"
    }

    object API : Scope() {
        override val value: String = "api"
    }

    object COMPILER : Scope() {
        override val value: String = "kapt"
    }
}

inline class Artifact(val value: Map<String, Any>)

inline class Group(val value: String)

inline class Name(val value: String)

inline class Version(val value: String)

inline class Path(val value: String)

interface Dependency {
    val artifact: Artifact
}

class RemoteDependency(group: Group, name: Name, version: Version) : Dependency {
    override val artifact: Artifact = Artifact(
        mapOf(
            "group" to group.value,
            "name" to name.value,
            "version" to version.value
        )
    )
}

fun remoteDependency(group: String, name: String, version: String) =
    RemoteDependency(Group(group), Name(name), Version(version))

class ModuleDependency(val path: Path) : Dependency {
    override val artifact: Artifact = Artifact(
        mapOf(
            "path" to path.value
        )
    )
}

fun moduleDependency(path: String) = ModuleDependency(Path(path))
