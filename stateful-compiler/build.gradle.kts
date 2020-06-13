plugins {
    apply(Plugins.LIBRARY)
    apply(Plugins.KOTLIN)
    apply(Plugins.MAVEN_PUBLISH)
}

java {
    sourceCompatibility = Dependencies.JAVA
    targetCompatibility = Dependencies.JAVA
}

dependencies {
    include(implementation = Dependencies.Kotlin.STDLIB)
    include(implementation = Dependencies.Kotlin.REFLECT)
    include(implementation = Dependencies.Compilation.KTAP)
    implementation(project(Modules.STATEFUL))
    include(testImplementation = Dependencies.Testing.JUNIT)
}

tasks.register("updatePackage") {
    dependsOn(setOf(":stateful-compiler:build", ":stateful-compiler:publish"))
    tasks.findByPath(":stateful-compiler:publish")?.mustRunAfter(":stateful-compiler:build")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = Project.GROUP_ID
            artifactId = "stateful-compiler"
            version = Project.VERSION
            artifact("$buildDir/libs/stateful-compiler.jar")

            pom.withXml {
                asNode().appendNode("dependencies").apply {
                    configurations.compile.get().dependencies.forEach { addDependency(it, "compile") }
                    configurations.api.get().dependencies.forEach { addDependency(it, "compile") }
                    configurations.implementation.get().dependencies.forEach { addDependency(it, "runtime") }
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/iFanie/Stateful")
            credentials {
                username = Props.GitHub.ID
                password = Props.GitHub.KEY
            }
        }
    }
}
