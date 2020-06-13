import org.gradle.api.JavaVersion
import type.ApiDependency
import type.ClasspathDependency
import type.FileTreeDependency
import type.ImplementationDependency
import type.TestImplementationDependency

private const val KOTLIN_GROUP = "org.jetbrains.kotlin"
private const val KOTLIN_VERSION = "1.3.71"

object Dependencies {
    val FILE_TREE = FileTreeDependency
    val JAVA = JavaVersion.VERSION_1_8

    object Build {
        val GRADLE = ClasspathDependency(
            "com.android.tools.build",
            "gradle",
            "4.0.0"
        )

        val KOTLIN_GRADLE_PLUGIN = ClasspathDependency(
            KOTLIN_GROUP,
            "kotlin-gradle-plugin",
            KOTLIN_VERSION
        )
    }

    object Kotlin {
        val STDLIB = ImplementationDependency(
            KOTLIN_GROUP,
            "kotlin-stdlib-jdk8",
            KOTLIN_VERSION
        )

        val REFLECT = ImplementationDependency(
            KOTLIN_GROUP,
            "kotlin-reflect",
            KOTLIN_VERSION
        )
    }

    object Testing {
        val JUNIT = TestImplementationDependency(
            "junit",
            "junit",
            "4.13"
        )
    }

    object Compilation {
        val JAVAX_ANNOTATIONS = ApiDependency(
            "javax.annotation",
            "javax.annotation-api",
            "1.3.2"
        )

        val KTAP = ImplementationDependency(
            "dev.fanie",
            "ktap",
            "0.0.5"
        )
    }
}
