buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        include(classpath = Dependencies.Build.GRADLE)
        include(classpath = Dependencies.Build.KOTLIN_GRADLE_PLUGIN)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url = uri("https://maven.pkg.github.com/iFanie/Stateful")
            credentials {
                username = Props.GitHub.ID
                password = Props.GitHub.KEY
            }
        }
    }
}

subprojects {
    afterEvaluate {
        if (!hasProperty("android")) {
            dependencies {
                add("implementation", (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))))
            }
        }
    }
}

task("clean") {
    Modules.getPaths().map { project(it) }.plus(rootProject).forEach { delete(it.buildDir) }
    doLast { println("Cleaned all buildDirs.") }
}

tasks.register("updatePackages") {
    dependsOn(setOf(":stateful:updatePackage", ":stateful-compiler:updatePackage"))
    doLast { println("Published ${Project.VERSION} packages.") }
}

tasks.register("update") {
    dependsOn(setOf("clean", ":app:assembleDebug", "updatePackages"))
    tasks.findByPath(":app:assembleDebug")?.mustRunAfter("clean")
    tasks.findByName("updatePackages")?.mustRunAfter(":app:assembleDebug")
    doLast { println("Update done.") }
}
