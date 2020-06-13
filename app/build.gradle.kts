plugins {
    apply(Plugins.APP)
    apply(Plugins.KOTLIN_ANDROID)
    apply(Plugins.KOTLIN_ANDROID_EXTENSIONS)
    apply(Plugins.KOTLIN_KAPT)
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "dev.fanie.statefulapp"
        minSdkVersion(19)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    include(implementation = Dependencies.Kotlin.STDLIB)
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    include(testImplementation = Dependencies.Testing.JUNIT)
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    if (Props.Local.DEV_MODE) {
        implementation(project(Modules.STATEFUL))
        kapt(project(Modules.STATEFUL_COMPILER))
    } else {
        implementation("dev.fanie:stateful:${Project.VERSION}")
        kapt("dev.fanie:stateful-compiler:${Project.VERSION}")
    }
}
