import type.Plugin

object Plugins {
    val APP = Plugin("com.android.application")
    val LIBRARY = Plugin("java-library")
    val KOTLIN = Plugin("kotlin")
    val KOTLIN_ANDROID = Plugin("kotlin-android")
    val KOTLIN_ANDROID_EXTENSIONS = Plugin("kotlin-android-extensions")
    val KOTLIN_KAPT = Plugin("kotlin-kapt")
    val MAVEN_PUBLISH = Plugin("maven-publish")
}
