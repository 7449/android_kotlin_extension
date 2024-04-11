plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
    kotlin("kapt")
}
apply("$rootDir/build.maven.gradle")
android {
    namespace = "androidx.core.extension.recyclerview"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig { minSdk = 21 }
    buildFeatures.dataBinding = true
    buildTypes { release { isMinifyEnabled = false } }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}
dependencies {
    compileOnly(project(":extension"))
    implementation(libs.bundles.recyclerview)
}