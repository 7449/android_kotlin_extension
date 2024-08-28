plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
}
if (file("$rootDir/build.maven.gradle").exists()) {
    apply("$rootDir/build.maven.gradle")
}
android {
    namespace = "androidx.core.extension.http"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        consumerProguardFile(file("proguard-rules.pro"))
    }
    buildTypes { release { isMinifyEnabled = false } }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}