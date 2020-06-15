import org.gradle.api.JavaVersion
import type.remoteDependency

private const val KOTLIN_GROUP = "org.jetbrains.kotlin"
private const val KOTLIN_VERSION = "1.3.71"

object Dependencies {
    val JAVA = JavaVersion.VERSION_1_8

    object Build {
        val GRADLE = remoteDependency(
            "com.android.tools.build",
            "gradle",
            "4.0.0"
        )

        val KOTLIN_GRADLE_PLUGIN = remoteDependency(
            KOTLIN_GROUP,
            "kotlin-gradle-plugin",
            KOTLIN_VERSION
        )
    }

    object Kotlin {
        val STDLIB = remoteDependency(
            KOTLIN_GROUP,
            "kotlin-stdlib-jdk8",
            KOTLIN_VERSION
        )

        val REFLECT = remoteDependency(
            KOTLIN_GROUP,
            "kotlin-reflect",
            KOTLIN_VERSION
        )
    }

    object Testing {
        val JUNIT = remoteDependency(
            "junit",
            "junit",
            "4.13"
        )
    }

    object Compilation {
        val JAVAX_ANNOTATIONS = remoteDependency(
            "javax.annotation",
            "javax.annotation-api",
            "1.3.2"
        )

        val KTAP = remoteDependency(
            "dev.fanie",
            "ktap",
            "0.0.5"
        )
    }
}
