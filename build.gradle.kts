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
                username = Properties.GitHub.ID
                password = Properties.GitHub.KEY
            }
        }
    }
}

subprojects {
    afterEvaluate {
        dependencies {
            add("implementation", (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))))
        }
    }
}

task("clean") {
    Modules.getPaths().map { project(it) }.plus(rootProject).forEach { delete(it.buildDir) }
    doLast { println("Cleaned all buildDirs.") }
}

tasks.register("updatePackages") {
    dependsOn(setOf(":stateful:updatePackage", ":stateful-compiler:updatePackage"))
    doLast { println("Published ${Project.VERSION}(${Project.BUILD}) packages.") }
}

tasks.register("update") {
    dependsOn(setOf("clean", ":app:assembleDebug", "updatePackages"))
    tasks.findByPath(":app:assembleDebug")?.mustRunAfter("clean")
    tasks.findByName("updatePackages")?.mustRunAfter(":app:assembleDebug")
    doLast { println("Update done.") }
}
