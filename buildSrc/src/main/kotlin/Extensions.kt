import groovy.util.Node
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec
import type.Artifact
import type.Plugin
import type.RemoteDependency
import type.Scope
import type.ModuleDependency as LocalDependency

fun PluginDependenciesSpec.apply(plugin: Plugin): PluginDependencySpec = id(plugin.id)

private fun DependencyHandler.from(scope: Scope, artifact: Artifact): Dependency? =
    add(scope.value, artifact.value)

fun DependencyHandler.include(
    classpath: RemoteDependency? = null,
    implementation: RemoteDependency? = null,
    testImplementation: RemoteDependency? = null,
    api: RemoteDependency? = null,
    compiler: RemoteDependency? = null
): Dependency? = when {
    classpath != null -> from(Scope.CLASSPATH, classpath.artifact)
    implementation != null -> from(Scope.IMPLEMENTATION, implementation.artifact)
    testImplementation != null -> from(Scope.TEST_IMPLEMENTATION, testImplementation.artifact)
    api != null -> from(Scope.API, api.artifact)
    compiler != null -> from(Scope.COMPILER, compiler.artifact)
    else -> null
}

private fun DependencyHandler.fromProject(scope: Scope, artifact: Artifact): Dependency? =
    add(scope.value, project(artifact.value))

fun DependencyHandler.include(
    implementation: LocalDependency? = null,
    testImplementation: LocalDependency? = null,
    api: LocalDependency? = null,
    compiler: LocalDependency? = null
): Dependency? = when {
    implementation != null -> fromProject(Scope.IMPLEMENTATION, implementation.artifact)
    testImplementation != null -> fromProject(Scope.TEST_IMPLEMENTATION, testImplementation.artifact)
    api != null -> fromProject(Scope.API, api.artifact)
    compiler != null -> fromProject(Scope.COMPILER, compiler.artifact)
    else -> null
}

fun Node.addDependency(dep: Dependency, scope: String) {
    if (dep.group != null && dep.version != null && dep.version != "unspecified" && dep.name != "unspecified") {
        val dependencyNode = appendNode("dependency").apply {
            appendNode("groupId", dep.group)
            appendNode("artifactId", dep.name)
            appendNode("version", dep.version)
            appendNode("scope", scope)
        }

        if (dep is ModuleDependency) {
            dependencyNode.appendNode("exclusions").appendNode("exclusion").apply {
                if (!dep.isTransitive) {
                    appendNode("groupId", "*")
                    appendNode("artifactId", "*")
                } else if (dep.excludeRules.isNotEmpty()) {
                    dep.excludeRules.forEach {
                        appendNode("groupId", it.group)
                        appendNode("artifactId", it.module)
                    }
                }
            }
        }
    }
}
