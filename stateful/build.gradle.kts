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
    implementation(Dependencies.Kotlin.STDLIB)
    api(Dependencies.Compilation.JAVAX_ANNOTATIONS)
    testImplementation(Dependencies.Testing.JUNIT)
}

tasks.register("updatePackage") {
    dependsOn(setOf(":stateful:build", ":stateful:publish"))
    tasks.findByPath(":stateful:publish")?.mustRunAfter(":stateful:build")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = Project.GROUP_ID
            artifactId = "stateful"
            version = Project.VERSION
            artifact("$buildDir/libs/stateful.jar")

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
