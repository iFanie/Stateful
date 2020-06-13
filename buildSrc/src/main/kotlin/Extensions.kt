import groovy.util.Node
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec
import type.ApiDependency
import type.ImplementationDependency
import type.Plugin
import type.TestImplementationDependency

fun PluginDependenciesSpec.apply(plugin: Plugin): PluginDependencySpec = this.id(plugin.id)

fun DependencyHandler.implementation(dependency: ImplementationDependency): Dependency? =
    add("implementation", dependency.asMap())

fun DependencyHandler.testImplementation(dependency: TestImplementationDependency): Dependency? =
    add("testImplementation", dependency.asMap())

fun DependencyHandler.api(dependency: ApiDependency): Dependency? = add("api", dependency.asMap())

fun Node.addDependency(dep: Dependency, scope: String) {
    if (dep.group != null && dep.version != null && dep.name != "unspecified") {
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
